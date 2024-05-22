package no.ntnu.project.group4.webapp.dto;

/**
 * Data transfer object (DTO) for submitting changes to user password.
 */
public class UserUpdatePasswordDto {
  private String password;

  /**
   * Contructs an instance of the UserUpdatePasswordDto.
   * 
   * <p>Empty constructor needed for issue described at
   * https://stackoverflow.com/questions/53191468/no-creators-like-default-construct-exist-cannot-deserialize-from-object-valu
   * .</p>
   */
  public UserUpdatePasswordDto() {
  }

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
