package no.ntnu.project.group4.webapp.dto;

import java.sql.Date;

/**
 * Data transfer object (DTO) for submitting changes to user data.
 */
public class UserDto {
  private String firstName;
  private String lastName;
  private String email;
  private int phoneNumber;
  private Date dateOfBirth;

  public UserDto(String firstName, String lastName, String email, int phoneNumber, long dateLong) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    Date dateOfBirth = new Date(dateLong);
    this.dateOfBirth = dateOfBirth;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getPhoneNumber() {
    return this.phoneNumber;
  }

  public void setPhoneNumber(int phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public Date getDateOfBirth() {
    return this.dateOfBirth;
  }

  public void setDateOfBirth(long dateLong) {
    Date dateOfBirth = new Date(dateLong);
    this.dateOfBirth = dateOfBirth;
  }
}
