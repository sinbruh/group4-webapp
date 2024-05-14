package no.ntnu.project.group4.webapp.dto;

/**
 * The CarDto class represents a data transfer object (DTO) for car data.
 * 
 * @author Group 4
 * @version v1.0 (2024.05.14)
 */
public class CarDto {
  private String make;
  private String model;
  private int year;

  /**
   * Contructs an instance of the CarDto class.
   * 
   * @param make The specified make
   * @param model The specified model
   * @param year The specified year
   */
  public CarDto(String make, String model, int year) {
    this.make = make;
    this.model = model;
    this.year = year;
  }

  /**
   * Getter for make.
   * 
   * @return Make
   */
  public String getMake() {
    return make;
  }
  
  /**
   * Getter for model.
   * 
   * @return Model
   */
  public String getModel() {
    return model;
  }
  
  /**
   * Getter for year.
   * 
   * @return Year
   */
  public int getYear() {
    return year;
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
   * Setter for model.
   * 
   * @param model The specified model
   */
  public void setModel(String model) {
    this.model = model;
  }
  
  /**
   * Setter for year.
   * 
   * @param year The specified year
   */
  public void setYear(int year) {
    this.year = year;
  }
}
