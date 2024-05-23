package no.ntnu.project.group4.webapp.dto;

/**
 * The AuthenticationRequest class represents an authentication request data transfer object (DTO).
 * The class contains data the user will send when requesting to authenticate (log in).
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
public class AuthenticationRequest {
  private String email;
  private String password;

  /**
   * Constructs an instance of the AuthenticationRequest class.
   *
   * @param email    The specified email
   * @param password The specified password
   */
  public AuthenticationRequest(String email, String password) {
    this.email = email;
    this.password = password;
  }

  /**
   * Getter for email.
   *
   * @return Email
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * Setter for email.
   *
   * @param email The specified email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Getter for password.
   *
   * @return Password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Setter for password.
   *
   * @param password The specified password
   */
  public void setPassword(String password) {
    this.password = password;
  }
}
