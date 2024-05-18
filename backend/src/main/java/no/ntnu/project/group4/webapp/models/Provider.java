package no.ntnu.project.group4.webapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

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
  @JsonIgnore
  @ManyToOne
  private Configuration configuration;

  /**
   * Empty constructor needed for JPA.
   */
  public Provider() {
  }

  public Provider(String name, int price, String location) {
    this.name = name;
    this.price = price;
    this.location = location;
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

  public Configuration getConfiguration() {
    return this.configuration;
  }

  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }

  public boolean isValid() {
    return !this.name.isBlank() && this.price > 0 && !this.location.isBlank();
  }
}
