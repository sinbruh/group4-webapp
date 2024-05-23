package no.ntnu.project.group4.webapp.dto;

import java.sql.Date;

/**
 * The UserUpdateDto represents the data transfer object (DTO) for sending user data. The class
 * contains data for submitting changes to user data.
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
public class UserUpdateDto {
  private String firstName;
  private String lastName;
  private String email;
  private int phoneNumber;
  private Date dateOfBirth;

  /**
   * Constructs an instance of the UserUpdateDto class.
   *
   * @param firstName   The specified first name
   * @param lastName    The specified last name
   * @param email       The specified email
   * @param phoneNumber The specified phone number
   * @param dateLong    The specified long value for the date of birth
   */
  public UserUpdateDto(String firstName, String lastName, String email, int phoneNumber,
                       long dateLong) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.dateOfBirth = new Date(dateLong);
  }

  /**
   * Getter for first name.
   *
   * @return First name
   */
  public String getFirstName() {
    return this.firstName;
  }

  /**
   * Setter for first name.
   *
   * @param firstName The specified first name
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Getter for last name.
   *
   * @return Last name
   */
  public String getLastName() {
    return this.lastName;
  }

  /**
   * Setter for last name.
   *
   * @param lastName The specified last name
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
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
   * Getter for phone number.
   *
   * @return Phone number
   */
  public int getPhoneNumber() {
    return this.phoneNumber;
  }

  /**
   * Setter for phone number.
   *
   * @param phoneNumber The specified phone number
   */
  public void setPhoneNumber(int phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * Getter for date of birth.
   *
   * @return Date of birth
   */
  public Date getDateOfBirth() {
    return this.dateOfBirth;
  }

  /**
   * Setter for date of birth.
   *
   * @param dateLong The specified long value for the date of birth
   */
  public void setDateOfBirth(long dateLong) {
    this.dateOfBirth = new Date(dateLong);
  }
}
