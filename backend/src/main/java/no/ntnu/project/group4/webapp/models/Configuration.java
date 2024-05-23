package no.ntnu.project.group4.webapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The Configuration class represents the entity class for the configuration entity.
 * 
 * <p>The class uses JPA with annotations for ORM operations.</p>
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@Entity(name = "configuration")
@Schema(
    description = "A configuration entity, representing a specific car configuration that can be "
                + "added to a car"
)
public class Configuration {
  @Schema(description = "Unique ID")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Schema(description = "Configuration name")
  private String name;
  @Schema(description = "Configuration fuel type")
  private String fuelType;
  @Schema(description = "Configuration transmission type")
  private String transmissionType;
  @Schema(description = "Number of seats in the configuration")
  private int numberOfSeats;
  @Schema(description = "Car the configuration belongs to")
  @JsonIgnore
  @ManyToOne
  private Car car;
  @Schema(description = "Extra features in the configuration")
  @OneToMany(mappedBy = "configuration")
  private Set<ExtraFeature> extraFeatures = new LinkedHashSet<>();
  @Schema(description = "Providers of the configuration")
  @OneToMany(mappedBy = "configuration")
  private Set<Provider> providers = new LinkedHashSet<>();

  /**
   * Constructs an instance of the Configuration class.
   *
   * <p>Empty constructor needed for JPA.</p>
   */
  public Configuration() {
    // Intentionally left blank
  }

  /**
   * Constructs an instance of the Configuration class.
   *
   * @param name             The specified name
   * @param fuelType         The specified fuel type
   * @param transmissionType The specified transmission type
   * @param numberOfSeats    The specified number of seats
   */
  public Configuration(String name, String fuelType, String transmissionType, int numberOfSeats) {
    this.name = name;
    this.fuelType = fuelType;
    this.transmissionType = transmissionType;
    this.numberOfSeats = numberOfSeats;
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
   * Getter for fuel type.
   *
   * @return Fuel type
   */
  public String getFuelType() {
    return this.fuelType;
  }

  /**
   * Setter for fuel type.
   *
   * @param fuelType The specified fuel type
   */
  public void setFuelType(String fuelType) {
    this.fuelType = fuelType;
  }

  /**
   * Getter for transmission type.
   *
   * @return Transmission type
   */
  public String getTransmissionType() {
    return this.transmissionType;
  }

  /**
   * Setter for transmission type.
   *
   * @param transmissionType The specified transmission type
   */
  public void setTransmissionType(String transmissionType) {
    this.transmissionType = transmissionType;
  }

  /**
   * Getter for number of seats.
   *
   * @return Number of seats
   */
  public int getNumberOfSeats() {
    return this.numberOfSeats;
  }

  /**
   * Setter for number of seats.
   *
   * @param numberOfSeats The specified number of seats
   */
  public void setNumberOfSeats(int numberOfSeats) {
    this.numberOfSeats = numberOfSeats;
  }

  /**
   * Getter for car.
   *
   * @return Car
   */
  public Car getCar() {
    return this.car;
  }

  /**
   * Setter for car.
   *
   * @param car The specified car
   */
  public void setCar(Car car) {
    this.car = car;
  }

  /**
   * Getter for extra features.
   *
   * @return Extra features
   */
  public Set<ExtraFeature> getExtraFeatures() {
    return this.extraFeatures;
  }

  /**
   * Setter for extra features.
   *
   * @param extraFeatures The specified extra features
   */
  public void setExtraFeatures(Set<ExtraFeature> extraFeatures) {
    this.extraFeatures = extraFeatures;
  }

  /**
   * Getter for providers.
   *
   * @return Providers
   */
  public Set<Provider> getProviders() {
    return this.providers;
  }

  /**
   * Setter for providers.
   *
   * @param providers The specified providers
   */
  public void setProviders(Set<Provider> providers) {
    this.providers = providers;
  }

  /**
   * Returns true if the configuration is valid or false otherwise.
   *
   * @return True if the configuration is valid or false otherwise
   */
  public boolean isValid() {
    return !this.name.isBlank() && !this.fuelType.isBlank() && !this.transmissionType.isBlank()
           && this.numberOfSeats > 0;
  }
}
