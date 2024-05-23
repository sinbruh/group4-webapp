package no.ntnu.project.group4.webapp.dto;

/**
 * The UserUpdatePasswordDto represents the data transfer object (DTO) for sending user password
 * data. The class contains data for submitting changes to user password data.
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
public class UserUpdatePasswordDto {
  private String password;

  /**
   * Constructs an instance of the UserUpdatePasswordDto class.
   *
   * <p>Empty constructor needed for issue described at
   * https://stackoverflow.com/questions/53191468/no-creators-like-default-construct-exist-cannot-deserialize-from-object-valu
   * .</p>
   */
  public UserUpdatePasswordDto() {
    // Intentionally left blank
  }

  /**
   * Constructs an instance of the UserUpdatePasswordDto class.
   *
   * @param password The specified password
   */
  public UserUpdatePasswordDto(String password) {
    this.password = password;
  }

  /**
   * Getter for password.
   *
   * @return Password
   */
  public String getPassword() {
    return this.password;
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
