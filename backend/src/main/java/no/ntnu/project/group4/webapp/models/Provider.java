package no.ntnu.project.group4.webapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The Provider class represents the entity class for the provider entity.
 *
 * <p>The class uses JPA with annotations for ORM operations.</p>
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@Entity(name = "provider")
@Schema(
    description = "A provider entity, representing a specific configuration provider that can be "
                + "added to a car configuration"
)
public class Provider {
  @Schema(description = "Unique ID")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Schema(description = "Provider name")
  private String name;
  @Schema(description = "Price for the configuration")
  private int price;
  @Schema(description = "Location of the configuration")
  private String location;
  @Schema(description = "Availability for the configuration")
  private boolean available = true;
  @Schema(description = "Visibility for the configuration")
  private boolean visible = true;
  @Schema(description = "Configuration the provider belongs to")
  @JsonIgnore
  @ManyToOne
  private Configuration configuration;
  @Schema(description = "Rentals of configuration through this provider")
  @OneToMany(mappedBy = "provider")
  private Set<Rental> rentals = new LinkedHashSet<>();
  @Schema(description = "Users who have favorited this provider")
  @JsonIgnore
  @ManyToMany(mappedBy = "favorites")
  private Set<User> favoritedUsers = new LinkedHashSet<>();

  /**
   * Constructs an instance of the Provider class.
   *
   * <p>Empty constructor needed for JPA.</p>
   */
  public Provider() {
    // Intentionally left blank
  }

  /**
   * Constructs an instance of the Provider class.
   *
   * @param name      The specified name
   * @param price     The specified price
   * @param location  The specified location
   * @param available The specified availability
   * @param visible   The specified visibility
   */
  public Provider(String name, int price, String location, boolean available, boolean visible) {
    this.name = name;
    this.price = price;
    this.location = location;
    this.available = available;
    this.visible = visible;
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
   * Getter for price.
   *
   * @return Price
   */
  public int getPrice() {
    return this.price;
  }

  /**
   * Setter for price.
   *
   * @param price The specified price
   */
  public void setPrice(int price) {
    this.price = price;
  }

  /**
   * Getter for location.
   *
   * @return Location
   */
  public String getLocation() {
    return this.location;
  }

  /**
   * Setter for location.
   *
   * @param location The specified location
   */
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * Checks if provider is available.
   *
   * @return True if provider is available or false otherwise
   */
  public boolean isAvailable() {
    return this.available;
  }

  /**
   * Setter for availability.
   *
   * @param available The specified availability
   */
  public void setAvailable(boolean available) {
    this.available = available;
  }

  /**
   * Checks if provider is visible.
   *
   * @return True if provider is visible or false otherwise
   */
  public boolean isVisible() {
    return this.visible;
  }

  /**
   * Setter for visibility.
   *
   * @param visible The specified visibility
   */
  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  /**
   * Getter for configuration.
   *
   * @return Configuration
   */
  public Configuration getConfiguration() {
    return this.configuration;
  }

  /**
   * Setter for configuration.
   *
   * @param configuration The specified configuraiton
   */
  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
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
   * Getter for favorited users.
   *
   * @return Favorited users
   */
  public Set<User> getFavoritedUsers() {
    return this.favoritedUsers;
  }

  /**
   * Setter for favorites users.
   *
   * @param favoritedUsers The specified favorited users
   */
  public void setFavoritedUsers(Set<User> favoritedUsers) {
    this.favoritedUsers = favoritedUsers;
  }

  /**
   * Returns true if the provider is valid or false otherwise.
   *
   * @return True if the provider is valid or false otherwise
   */
  public boolean isValid() {
    return !this.name.isBlank() && this.price > 0 && !this.location.isBlank();
  }
}
