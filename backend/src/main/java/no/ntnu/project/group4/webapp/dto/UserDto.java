package no.ntnu.project.group4.webapp.dto;

import java.sql.Date;
import java.util.Set;

import no.ntnu.project.group4.webapp.models.Provider;
import no.ntnu.project.group4.webapp.models.Receipt;
import no.ntnu.project.group4.webapp.models.Rental;
import no.ntnu.project.group4.webapp.models.Role;

/**
 * Data transfer object (DTO) for getting user data.
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

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
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
    this.dateOfBirth = new Date(dateLong);
  }

  public boolean isActive() {
    return this.active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public Set<Role> getRoles() {
    return this.roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public Set<Rental> getRentals() {
    return this.rentals;
  }

  public void setRentals(Set<Rental> rentals) {
    this.rentals = rentals;
  }

  public Set<Receipt> getReceipts() {
    return this.receipts;
  }

  public void setReceipts(Set<Receipt> receipts) {
    this.receipts = receipts;
  }

  public Set<Provider> getFavorites() {
    return this.favorites;
  }

  public void setFavorites(Set<Provider> favorites) {
    this.favorites = favorites;
  }
}
