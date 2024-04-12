package no.ntnu.project.group4.webapp.dto;

/**
 * Data the API will send as a response to the user when the authentication is successful.
 */
public class AuthenticationResponse {
  private final String jwt;

  public AuthenticationResponse(String jwt) {
    this.jwt = jwt;
  }

  public String getJwt() {
    return jwt;
  }
}
