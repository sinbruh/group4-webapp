package no.ntnu.project.group4.webapp.models;

import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity(name = "provider")
@Schema(name = "Provider", description = "A provider entity, representing a provider of a car configuration.")
public class Provider {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private int price;
  private String location;
  private boolean available = true;
  private boolean visible = true;
  @JsonIgnore
  @ManyToOne
  private Configuration configuration;
  @OneToMany(mappedBy = "provider")
  private Set<Rental> rentals = new LinkedHashSet<>();
  @JsonIgnore
  @ManyToMany(mappedBy = "favorites")
  private Set<User> favoritedUsers = new LinkedHashSet<>();

  /**
   * Empty constructor needed for JPA.
   */
  public Provider() {
  }

  public Provider(String name, int price, String location, boolean available, boolean visible) {
    this.name = name;
    this.price = price;
    this.location = location;
    this.available = available;
    this.visible = visible;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPrice() {
    return this.price;
  }

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

  public Configuration getConfiguration() {
    return this.configuration;
  }

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
   * Toggles the availability for the provider.
   */
  public void toggleAvailable() {
    if (!this.available) {
      this.available = true;
    } else {
      this.available = false;
    }
  }

  /**
   * Toggles the visibility for the provider.
   */
  public void toggleVisible() {
    if (!this.visible) {
      this.visible = true;
    } else {
      this.visible = false;
    }
  }

  public boolean isValid() {
    return !this.name.isBlank() && this.price > 0 && !this.location.isBlank();
  }
}
