package no.ntnu.project.group4.webapp.models;

public class Model {
  private Long id;
  private String make;
  private String name;
  private int year;
  private String fuel;
  private String transmission;
  private int numberOfSeats;

  public Model() {
  }

  public Model(String make, String name, int year, String fuel, String transmission, int numberOfSeats) {
    this.make = make;
    this.name = name;
    this.year = year;
    this.fuel = fuel;
    this.transmission = transmission;
    this.numberOfSeats = numberOfSeats;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMake() {
    return this.make;
  }

  public void setMake(String make) {
    this.make = make;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getYear() {
    return this.year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public String getFuel() {
    return this.fuel;
  }

  public void setFuel(String fuel) {
    this.fuel = fuel;
  }

  public String getTransmission() {
    return this.transmission;
  }

  public void setTransmission(String transmission) {
    this.transmission = transmission;
  }

  public int getNumberOfSeats() {
    return this.numberOfSeats;
  }

  public void setNumberOfSeats(int numberOfSeats) {
    this.numberOfSeats = numberOfSeats;
  }
}
