package no.ntnu.project.group4.webapp.dto;

/**
 * Data transfer object (DTO) for submitting changes to user password.
 */
public class UserUpdatePasswordDto {
  private String password;

  public UserUpdatePasswordDto(String password) {
    this.password = password;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
