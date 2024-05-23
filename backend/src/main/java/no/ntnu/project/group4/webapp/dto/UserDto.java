package no.ntnu.project.group4.webapp.dto;

import java.sql.Date;
import java.util.Set;
import no.ntnu.project.group4.webapp.models.Provider;
import no.ntnu.project.group4.webapp.models.Receipt;
import no.ntnu.project.group4.webapp.models.Rental;
import no.ntnu.project.group4.webapp.models.Role;

/**
 * The UserDto class represents the data transfer object (DTO) for receiving user data.
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
public class UserDto {
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private int phoneNumber;
  private Date dateOfBirth;
  private boolean active;
  private Set<Role> roles;
  private Set<Rental> rentals;
  private Set<Receipt> receipts;
  private Set<Provider> favorites;

  /**
   * Constructs an instance of the UserDto class.
   *
   * @param id          The specified ID
   * @param firstName   The specified first name
   * @param lastName    The specified last name
   * @param email       The specified email
   * @param phoneNumber The specified phone number
   * @param dateLong    The specified long value for the date of birth
   * @param active      The specified active status
   * @param roles       The specified roles
   * @param rentals     The specified rentals
   * @param receipts    The specified receipts
   * @param favorites   The specified favorites
   */
  public UserDto(Long id, String firstName, String lastName, String email, int phoneNumber,
                 long dateLong, boolean active, Set<Role> roles, Set<Rental> rentals,
                 Set<Receipt> receipts, Set<Provider> favorites) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.dateOfBirth = new Date(dateLong);
    this.active = active;
    this.roles = roles;
    this.rentals = rentals;
    this.receipts = receipts;
    this.favorites = favorites;
  }

  /**
   * Getter for ID.
   *
   * @return ID
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Setter for ID.
   *
   * @param id The specified ID
   */
  public void setId(Long id) {
    this.id = id;
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
   * Setter fordate of birth.
   *
   * @param dateLong The specified long value for the date of birth
   */
  public void setDateOfBirth(long dateLong) {
    this.dateOfBirth = new Date(dateLong);
  }

  /**
   * Checks if the user is active.
   *
   * @return True if the user is active or false otherwise
   */
  public boolean isActive() {
    return this.active;
  }

  /**
   * Setter for active status.
   *
   * @param active The specified active status
   */
  public void setActive(boolean active) {
    this.active = active;
  }

  /**
   * Getter for roles.
   *
   * @return Roles
   */
  public Set<Role> getRoles() {
    return this.roles;
  }

  /**
   * Setter for roles.
   *
   * @param roles The specified roles
   */
  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  /**
   * Getter for rentals.
   *
   * @return Rentals
   */
  public Set<Rental> getRentals() {
    return this.rentals;
  }

  /**
   * Setter for rentals.
   *
   * @param rentals The specified rentals
   */
  public void setRentals(Set<Rental> rentals) {
    this.rentals = rentals;
  }

  /**
   * Getter for receipts.
   *
   * @return Receipts
   */
  public Set<Receipt> getReceipts() {
    return this.receipts;
  }

  /**
   * Setter for receipts.
   *
   * @param receipts The specified receipts
   */
  public void setReceipts(Set<Receipt> receipts) {
    this.receipts = receipts;
  }

  /**
   * Getter for favorites.
   *
   * @return Favorites
   */
  public Set<Provider> getFavorites() {
    return this.favorites;
  }

  /**
   * Setter for favorites.
   *
   * @param favorites The specified favorites
   */
  public void setFavorites(Set<Provider> favorites) {
    this.favorites = favorites;
  }
}
