package no.ntnu.project.group4.webapp.dto;

import java.sql.Date;

/**
 * Data transfer object (DTO) for data from the register form.
 */
public class RegisterDto {
  private final String firstName;
  private final String lastName;
  private final String email;
  private final int phoneNumber;
  private final String password;
  private final Date dateOfBirth;

  public RegisterDto(String firstName, String lastName, String email, int phoneNumber,
                     String password, long dateLong) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.password = password;
    this.dateOfBirth = new Date(dateLong);
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public String getEmail() {
    return this.email;
  }

  public int getPhoneNumber() {
    return this.phoneNumber;
  }

  public String getPassword() {
    return this.password;
  }

  public Date getDateOfBirth() {
    return this.dateOfBirth;
  }
}
