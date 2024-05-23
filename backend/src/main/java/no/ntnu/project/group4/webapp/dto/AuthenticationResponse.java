package no.ntnu.project.group4.webapp.dto;

/**
 * The AuthenticationResponse class represents an authentication response data transfer object
 * (DTO). The class contains data the user will receive when sending a successful authentication
 * request.
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
public class AuthenticationResponse {
  private final String jwt;

  /**
   * Constructs an instance of the AuthenticationResponse class.
   *
   * @param jwt The specified JWT token
   */
  public AuthenticationResponse(String jwt) {
    this.jwt = jwt;
  }

  /**
   * Getter for JWT token.
   *
   * @return JWT token
   */
  public String getJwt() {
    return jwt;
  }
}
