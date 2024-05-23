package no.ntnu.project.group4.webapp.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * The JwtRequestFilter class represents the request filter for JWT tokens. The class is a filter
 * that is applied to all HTTP requests and checks for a valid JWT token in the `Authorization:
 * Bearer ...` header.
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private JwtUtil jwtUtil;

  private static final Logger logger = LoggerFactory.getLogger(
      JwtRequestFilter.class.getSimpleName()
  );

  /**
   * Enables internal filter.
   *
   * @param request     The specified request
   * @param response    The specified response
   * @param filterChain The specified filter chain
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    String jwtToken = this.getJwtToken(request);
    String username = jwtToken != null ? this.getUsernameFrom(jwtToken) : null;
    if (username != null && notAuthenticatedYet()) {
      UserDetails userDetails = this.getUserDetailsFromDatabase(username);
      if (jwtUtil.validateToken(jwtToken, userDetails)) {
        registerUserAsAuthenticated(request, userDetails);
      }
    }
    filterChain.doFilter(request, response);
  }

  /**
   * Returns the user details of the user with the specified username from the database.
   *
   * @param username The specified username
   * @return The user details of the user with the specified username from the database
   */
  private UserDetails getUserDetailsFromDatabase(String username) {
    UserDetails userDetails = null;
    try {
      userDetails = userDetailsService.loadUserByUsername(username);
    } catch (UsernameNotFoundException e) {
      logger.warn("User " + username + " not found in the database");
    }
    return userDetails;
  }

  /**
   * Returns the JWT token gathered from the Header of the specified request.
   *
   * @param request The specified request
   * @return The JWT token gathered from the Header of the specified request
   */
  private String getJwtToken(HttpServletRequest request) {
    final String authorizationHeader = request.getHeader("Authorization");
    String jwt = null;
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwt = stripBearerPrefixFrom(authorizationHeader);
    }
    return jwt;
  }

  /**
   * Returns the JWT token following the "Bearer " prefix in the specified Header value.
   *
   * @param authorizationHeaderValue The specified Header value
   * @return The JWT token following the "Bearer " prefix in the specified Header value
   */
  private static String stripBearerPrefixFrom(String authorizationHeaderValue) {
    final int numberOfCharsToStrip = "Bearer ".length();
    return authorizationHeaderValue.substring(numberOfCharsToStrip);
  }

  /**
   * Returns the username of the user extracted from the specified JWT token.
   *
   * @param jwtToken The specified JWT token
   * @return The username of the user extracted from the specified JWT token
   */
  private String getUsernameFrom(String jwtToken) {
    String username = null;
    try {
      username = jwtUtil.extractUsername(jwtToken);
    } catch (MalformedJwtException e) {
      logger.warn("Malformed JWT: " + e.getMessage());
    } catch (JwtException e) {
      logger.warn("Error in the JWT token: " + e.getMessage());
    }
    return username;
  }

  /**
   * Checks if the user is not authenticated yet.
   *
   * @return True if the user is not authenticated yet or false otherwise
   */
  private static boolean notAuthenticatedYet() {
    return SecurityContextHolder.getContext().getAuthentication() == null;
  }

  /**
   * Registers the user with the specified user details as authenticated with the specified
   * request.
   *
   * @param request     The specified request
   * @param userDetails The specified user details
   */
  private static void registerUserAsAuthenticated(HttpServletRequest request,
                                                  UserDetails userDetails) {
    final UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities()
    );
    upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(upat);
  }
}
