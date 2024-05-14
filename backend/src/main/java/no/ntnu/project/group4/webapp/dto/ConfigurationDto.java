package no.ntnu.project.group4.webapp.dto;

/**
 * The ConfigurationDto class represents the data transfer object (DTO) for configurations.
 * 
 * @author Group 4
 * @version v1.0 (2024.05.14)
 */
public class ConfigurationDto {
  private String name;
  private String fuelType;
  private String transmissionType;
  private int numberOfSeats;
  private String location;
  private boolean available = true;
  private Long carId;

  /**
   * Constructs an instance of the ConfigurationDto class.
   * 
   * @param name The specified name
   * @param fuelType The specified fuel type
   * @param transmissionType The specified transmission type
   * @param numberOfSeats The specified number of seats
   * @param location The specified location
   * @param carId The specified car ID
   */
  public ConfigurationDto(String name, String fuelType, String transmissionType, int numberOfSeats,
                          String location, Long carId) {
    this.name = name;
    this.fuelType = fuelType;
    this.transmissionType = transmissionType;
    this.numberOfSeats = numberOfSeats;
    this.location = location;
    this.carId = carId;
  }

  /**
   * Getter for name.
   * 
   * @return Name
   */
  public String getName() {
    return name;
  }

  /**
   * Getter for fuel type.
   * 
   * @return Fuel type
   */
  public String getFuelType() {
    return fuelType;
  }

  /**
   * Getter for transmission type.
   * 
   * @return Transmission type
   */
  public String getTransmissionType() {
    return transmissionType;
  }

  /**
   * Getter for number of seats.
   * 
   * @return Number of seats
   */
  public int getNumberOfSeats() {
    return numberOfSeats;
  }
  
  /**
   * Getter for location.
   * 
   * @return Location
   */
  public String getLocation() {
    return location;
  }
  
  /**
   * Checks if configuration is available.
   * 
   * @return True if configuration is available or false otherwise
   */
  public boolean isAvailable() {
    return available;
  }
  
  /**
   * Getter for car ID.
   * 
   * @return Car ID
   */
  public Long getCarId() {
    return carId;
  }

  /**
   * Setter for name.
   * 
   * @param name Specified name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Setter for fuel type.
   * 
   * @param fuelType Specified fuel type
   */
  public void setFuelType(String fuelType) {
    this.fuelType = fuelType;
  }

  /**
   * Setter for transmission type.
   * 
   * @param transmissionType Specified transmission type
   */
  public void setTransmissionType(String transmissionType) {
    this.transmissionType = transmissionType;
  }

  /**
   * Setter for number of seats.
   * 
   * @param numberOfSeats Specified number of seats
   */
  public void setNumberOfSeats(int numberOfSeats) {
    this.numberOfSeats = numberOfSeats;
  }

  /**
   * Setter for location.
   * 
   * @param location Specified location
   */
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * Setter for available.
   * 
   * @param available Specified available
   */
  public void setAvailable(boolean available) {
    this.available = available;
  }

  /**
   * Setter for car ID.
   * 
   * @param carId Specified car ID
   */
  public void setCarId(Long carId) {
    this.carId = carId;
  }
}
