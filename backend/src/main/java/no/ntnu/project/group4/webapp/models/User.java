package no.ntnu.project.group4.webapp.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.sql.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The User class represents the entity class for the user entity.
 *
 * <p>The class uses JPA with annotations for ORM operations.</p>
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@Entity(name = "user")
@Schema(description = "A user entity, representing a specific user")
public class User {
  @Schema(description = "Unique ID")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Schema(description = "First name of user")
  private String firstName;
  @Schema(description = "Last name of user")
  private String lastName;
  @Schema(description = "User email, primarily used when identifying user")
  private String email;
  @Schema(description = "Phone number of user")
  private int phoneNumber;
  @Schema(description = "User password")
  private String password;
  @Schema(description = "Birth date of user")
  private Date dateOfBirth;
  @Schema(description = "Active status of user")
  private boolean active = true;
  @Schema(description = "Roles the user has")
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<Role> roles = new LinkedHashSet<>();
  @Schema(description = "Rentals the users has")
  @OneToMany(mappedBy = "user")
  private Set<Rental> rentals = new LinkedHashSet<>();
  @Schema(description = "Receipts the user has")
  @OneToMany(mappedBy = "user")
  private Set<Receipt> receipts = new LinkedHashSet<>();
  @Schema(description = "Providers the user has favorited")
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "favorite",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "provider_id")
  )
  private Set<Provider> favorites = new LinkedHashSet<>();

  /**
   * Constructs an instance of the User class.
   *
   * <p>Empty constructor needed for JPA.</p>
   */
  public User() {
    // Intentionally left blank
  }

  /**
   * Constructs an instance of the User class.
   *
   * @param firstName   The specified first name
   * @param lastName    The specfifed last name
   * @param email       The specified email
   * @param phoneNumber The specified phone number
   * @param password    The specified password
   * @param dateOfBirth The specified date of birth
   */
  public User(String firstName, String lastName, String email, int phoneNumber, String password,
              Date dateOfBirth) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.password = password;
    this.dateOfBirth = dateOfBirth;
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
   * @param dateOfBirth The specified date of birth
   */
  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
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
   * Setter for favorties.
   *
   * @param favorites The specified favorites
   */
  public void setFavorites(Set<Provider> favorites) {
    this.favorites = favorites;
  }

  /**
   * Adds the specified role to the user.
   *
   * @param role The specified role
   */
  public void addRole(Role role) {
    this.roles.add(role);
  }

  /**
   * Adds the specified provider to the user favorites.
   *
   * @param provider The specified provider
   */
  public void addFavorite(Provider provider) {
    this.favorites.add(provider);
  }

  /**
   * Removes the specified provider from the user favorites.
   *
   * @param provider The specified provider
   */
  public void removeFavorite(Provider provider) {
    this.favorites.remove(provider);
  }

  /**
   * Checks if the user has the admin role.
   *
   * @return True if the user has the admin role or false otherwise
   */
  public boolean isAdmin() {
    return this.hasRole("ROLE_ADMIN");
  }

  /**
   * Checks if the user has the specified role.
   *
   * @param roleName The specified role
   * @return True if the user has the specified role or false otherwise
   */
  private boolean hasRole(String roleName) {
    boolean found = false;
    Iterator<Role> it = this.roles.iterator();
    while (!found && it.hasNext()) {
      Role role = it.next();
      if (role.getName().equals(roleName)) {
        found = true;
      }
    }
    return found;
  }

  /**
   * Returns true if the user is valid or false otherwise.
   *
   * @return True if the user is valid or false otherwise
   */
  public boolean isValid() {
    return this.firstName != null && !this.firstName.isBlank() && this.lastName != null
           && !this.lastName.isBlank() && this.email != null && !this.email.isBlank()
           && this.phoneNumber > 0 && this.password != null && !this.password.isBlank()
           && this.dateOfBirth != null;
  }
}
