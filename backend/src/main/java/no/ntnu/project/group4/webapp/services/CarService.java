package no.ntnu.project.group4.webapp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.ntnu.project.group4.webapp.models.Car;
import no.ntnu.project.group4.webapp.repositories.CarRepository;

@Service
public class CarService {
  @Autowired
  private CarRepository carRepository;

  /**
   * Gets all cars in the database.
   * 
   * @return All cars in the database
   */
  public Iterable<Car> getAll() {
    return this.carRepository.findAll();
  }
  
  /**
   * Gets the car with the specified ID regardless of if it exists or not.
   * 
   * @param id The specified ID
   * @return The car with the specified ID regardless of if it exists or not
   */
  public Optional<Car> getOne(Long id) {
    return this.carRepository.findById(id);
  }

  /**
   * Tries to add the specified car to the database.
   * 
   * @param car The specified car
   * @return The ID of the specified car added to the database
   * @throws IllegalArgumentException If the specified car is invalid
   */
  public Long add(Car car) {
    if (!car.isValid()) {
      throw new IllegalArgumentException("Car is invalid");
    }
    this.carRepository.save(car);
    return car.getId();
  }
  
  /**
   * Tries to delete the car with the specified ID.
   * 
   * @param id The specified ID
   * @return True if the car with the specified ID was found and thus deleted or false otherwise
   */
  public boolean delete(Long id) {
    Optional<Car> car = this.carRepository.findById(id);
    if (car.isPresent()) {
      this.carRepository.deleteById(id);
    }
    return car.isPresent();
  }
  
  /**
   * Tries to update the car with the specified ID to the specified car. The ID of the car must
   * match the specified ID.
   * 
   * @param id The specified ID
   * @param car The specified car
   * @throws IllegalArgumentException If the existing car was not found or any of the specified
   *                                  data is wrong (error message can be used in HTTP response)
   */
  public boolean update(Long id, Car car) {
    Optional<Car> existingCar = this.carRepository.findById(id);
    if (!car.isValid()) {
      throw new IllegalArgumentException("Car is invalid");
    }
    if (existingCar.isPresent()) {
      Car existingCarObj = existingCar.get();
      existingCarObj.setMake(car.getMake());
      existingCarObj.setModel(car.getModel());
      existingCarObj.setYear(car.getYear());
      this.carRepository.save(existingCarObj);
    }
    return existingCar.isPresent();
  }
}
