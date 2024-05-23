package no.ntnu.project.group4.webapp.dto;

import java.sql.Date;

/**
 * The RegisterDto class represents the data transfer object (DTO) the user will send when
 * registering. The class contains data from the register form.
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
public class RegisterDto {
  private final String firstName;
  private final String lastName;
  private final String email;
  private final int phoneNumber;
  private final String password;
  private final Date dateOfBirth;

  /**
   * Constructs an instance of the RegisterDto class.
   *
   * @param firstName   The specified first name
   * @param lastName    The specified last name
   * @param email       The specified email
   * @param phoneNumber The specified phone number
   * @param password    The specified password
   * @param dateLong    The specified long value for the date of birth
   */
  public RegisterDto(String firstName, String lastName, String email, int phoneNumber,
                     String password, long dateLong) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.password = password;
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
   * Getter for last name.
   *
   * @return Last name
   */
  public String getLastName() {
    return this.lastName;
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
   * Getter for phone number.
   *
   * @return Phone number
   */
  public int getPhoneNumber() {
    return this.phoneNumber;
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
   * Getter for date of birth.
   *
   * @return Date of birth
   */
  public Date getDateOfBirth() {
    return this.dateOfBirth;
  }
}
