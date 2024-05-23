package no.ntnu.project.group4.webapp.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The Car class represents the entity class for the car entity.
 *
 * <p>The class uses JPA with annotations for ORM operations.</p>
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@Entity(name = "car")
@Schema(description = "A car entity, representing a specific car")
public class Car {
  @Schema(description = "Unique ID")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Schema(description = "Car make")
  private String make;
  @Schema(description = "Car model")
  private String model;
  @Schema(description = "Car model release year")
  private int year;
  @Schema(description = "Car configurations")
  @OneToMany(mappedBy = "car")
  private Set<Configuration> configurations = new LinkedHashSet<>();

  /**
   * Constructs an instance of the Car class.
   *
   * <p>Empty constructor needed for JPA.</p>
   */
  public Car() {
    // Intentionally left blank
  }

  /**
   * Constructs an instance of the Car class.
   *
   * @param make  The specified make
   * @param model The specified model
   * @param year  The specified year
   */
  public Car(String make, String model, int year) {
    this.make = make;
    this.model = model;
    this.year = year;
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
   * Getter for make.
   *
   * @return Make
   */
  public String getMake() {
    return this.make;
  }

  /**
   * Setter for make.
   *
   * @param make The specified make
   */
  public void setMake(String make) {
    this.make = make;
  }

  /**
   * Getter for model.
   *
   * @return Model
   */
  public String getModel() {
    return this.model;
  }

  /**
   * Setter for model.
   *
   * @param model The specified model
   */
  public void setModel(String model) {
    this.model = model;
  }

  /**
   * Getter for year.
   *
   * @return Year
   */
  public int getYear() {
    return this.year;
  }

  /**
   * Setter for year.
   *
   * @param year The specified year
   */
  public void setYear(int year) {
    this.year = year;
  }

  /**
   * Getter for configurations.
   *
   * @return Configurations
   */
  public Set<Configuration> getConfigurations() {
    return this.configurations;
  }

  /**
   * Setter for configurations.
   *
   * @param configurations The specified configurations
   */
  public void setConfigurations(Set<Configuration> configurations) {
    this.configurations = configurations;
  }

  /**
   * Returns true if the car is valid or false otherwise.
   *
   * @return True if the car is valid or false otherwise
   */
  public boolean isValid() {
    return !this.make.isBlank() && !this.model.isBlank() && this.year >= 0;
  }
}
