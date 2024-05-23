package no.ntnu.project.group4.webapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The Role class represents the entity class for the role entity.
 *
 * <p>The class uses JPA with annotations for ORM operations.</p>
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@Entity(name = "role")
@Schema(description = "A role entity, representing a specific role that can be added to a user")
public class Role {
  @Schema(description = "Unique ID")
  @Id
  @GeneratedValue
  private Long id;
  @Schema(description = "Role name")
  private String name;
  @Schema(description = "Users that have this role")
  @JsonIgnore
  @ManyToMany(mappedBy = "roles")
  private Set<User> users = new LinkedHashSet<>();

  /**
   * Constructs an instance of the Role class.
   *
   * <p>Empty constructor needed for JPA.</p>
   */
  public Role() {
    // Intentionally left blank
  }

  /**
   * Constructs an instane of the Role class.
   *
   * @param name The specified name
   */
  public Role(String name) {
    this.name = name;
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
   * Getter for name.
   *
   * @return Name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Setter for name.
   *
   * @param name The specified name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Getter for users.
   *
   * @return Users
   */
  public Set<User> getUsers() {
    return this.users;
  }

  /**
   * Setter for users.
   *
   * @param users The specified users
   */
  public void setUsers(Set<User> users) {
    this.users = users;
  }

  /**
   * Returns true if the role is valid or false otherwise.
   *
   * @return True if the role is valid or false otherwise
   */
  public boolean isValid() {
    return !this.name.isBlank();
  }
}
