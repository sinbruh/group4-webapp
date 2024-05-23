package no.ntnu.project.group4.webapp.services;

import java.util.Optional;
import no.ntnu.project.group4.webapp.models.Car;
import no.ntnu.project.group4.webapp.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The CarService class represents the service class for the car entity.
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@Service
public class CarService {
  @Autowired
  private CarRepository carRepository;

  /**
   * Returns all cars in the database.
   *
   * @return All cars in the database
   */
  public Iterable<Car> getAll() {
    return this.carRepository.findAll();
  }
  
  /**
   * Returns the car with the specified ID regardless of if it exists or not.
   *
   * @param id The specified ID
   * @return The car with the specified ID regardless of if it exists or not
   */
  public Optional<Car> getOne(Long id) {
    return this.carRepository.findById(id);
  }

  /**
   * Returns the generated ID of the specified car if it is added to the database.
   *
   * @param car The specified car
   * @return The generated ID of the specified car if it is added to the database
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
   * Returns true if the car with the specified ID is found and updated with the specified car or
   * false otherwise.
   *
   * @param id  The specified ID
   * @param car The specified car
   * @return True if the car with the specified ID is found and updated with the specified car or
   *         false otherwise
   * @throws IllegalArgumentException If the specified car is invalid
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
  
  /**
   * Returns true if the car with the specified ID is found and deleted or false otherwise.
   *
   * @param id The specified ID
   * @return True if the car with the specified ID is found and deleted or false otherwise
   */
  public boolean delete(Long id) {
    Optional<Car> car = this.carRepository.findById(id);
    if (car.isPresent()) {
      this.carRepository.deleteById(id);
    }
    return car.isPresent();
  }
}
