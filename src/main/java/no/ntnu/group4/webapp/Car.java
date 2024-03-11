package no.ntnu.group4.webapp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;

@Entity
public class Car {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String make;
  private String model;
  private int year;
  private int price;
  @OneToMany(mappedBy = "car")
  private Set<Rental> rentals;

  public Car() {
  }

  public Car(int id, String make, String model, int year, int price) {
    this.id = id;
    this.make = make;
    this.model = model;
    this.year = year;
    this.price = price;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getMake() {
    return make;
  }

  public void setMake(String make) {
    this.make = make;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public boolean isValid() {
    return make != null && model != null && year > 0 && price > 0;
  }
}
