package no.ntnu.project.group4.webapp.models;

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

@Entity(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private int phoneNumber;
  private String password;
  private Date dateOfBirth;
  @OneToMany(mappedBy = "user")
  private Set<Rental> rentals;
  private boolean active = true;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<Role> roles = new LinkedHashSet<>();

  /**
   * Empty constructor needed for JPA.
   */
  public User() {
  }

  public User(String firstName, String lastName, String email, int phoneNumber, String password,
              Date dateOfBirth) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.password = password;
    this.dateOfBirth = dateOfBirth;
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

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Date getDateOfBirth() {
    return this.dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
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

  /**
   * Adds the specified role to the user.
   * 
   * @param role The specified role
   */
  public void addRole(Role role) {
    this.roles.add(role);
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
   * Checks if the user has valid custom fields.
   * 
   * <p>Custom fields are in this context fields that are set by the user when the user is
   * created.</p>
   * 
   * @return True if the user has valid custom fields or false otherwise
   */
  public boolean isValid() {
    return this.firstName != null && !this.firstName.isBlank() && this.lastName != null &&
           !this.lastName.isBlank() && this.email != null && !this.email.isBlank() &&
           this.phoneNumber > 0 && this.password != null && !this.password.isBlank() &&
           this.dateOfBirth != null;
  }
}
