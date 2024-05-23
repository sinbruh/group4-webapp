package no.ntnu.project.group4.webapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * The JwtUtil class represents a utility class for handling JWT tokens.
 *
 * <p>Code from https://youtu.be/X80nJ5T7YpE.</p>
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@Component
public class JwtUtil {
  @Value("${jwt_secret_key}")
  private String secretKey;

  // Key inside JWT token where roles are stored.
  private static final String ROLE_KEY = "roles";

  /**
   * Getter for secret key.
   *
   * @return Secret key
   */
  private SecretKey getSigningKey() {
    byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    return new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256");
  }

  /**
   * Returns a JWT token for an authenticated user generated from the specified user details.
   *
   * @param userDetails The specified user details
   * @return A JWT token for an authenticated user
   */
  public String generateToken(UserDetails userDetails) {
    final long timeNow = System.currentTimeMillis();
    final long millisecondsInHour = 60 * 60 * 1000;
    final long timeAfterOneHour = timeNow + millisecondsInHour;

    return Jwts.builder()
        .subject(userDetails.getUsername())
        .claim(ROLE_KEY, userDetails.getAuthorities())
        .issuedAt(new Date(timeNow))
        .expiration(new Date(timeAfterOneHour))
        .signWith(getSigningKey())
        .compact();
  }

  /**
   * Returns a username from the specified JWT token.
   *
   * @param token The specified JWT token
   * @return A username
   */
  public String extractUsername(String token) throws JwtException {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Checks if the specified JWT token is valid for the user specified in the specified user
   * details.
   *
   * @param token       The specified JWT token
   * @param userDetails The specified user details
   * @return True if the token is valid and matches the current user or false otherwise
   */
  public boolean validateToken(String token, UserDetails userDetails) throws JwtException {
    final String username = extractUsername(token);
    return userDetails != null && username.equals(userDetails.getUsername())
           && !isTokenExpired(token);
  }

  /**
   * Returns the expiration date of the specified JWT token.
   *
   * @param token The specified JWT token
   * @return The expiration date of the specified JWT token
   */
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Returns the claims of the specified JWT token with the specified claims resolver.
   *
   * @param token          The specified JWT token
   * @param claimsResolver The specified claims resolver
   * @return The claims of the specified JWT token with the specified claims resolver
   */
  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Returns all the claims of the specified JWT token.
   *
   * @param token The specified JWT token
   * @return All the claims of the specified JWT token
   */
  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
  }

  /**
   * Checks if the specified JWT token is expired.
   *
   * @param token The specified JWT token
   * @return True if the specified JWT token is expired or false otherwise
   */
  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }
}
