package no.ntnu.project.group4.webapp.dto;

/**
 * Data the user will send in the login request.
 */
public class AuthenticationRequest {
  private String email;
  private String password;

  public AuthenticationRequest(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
