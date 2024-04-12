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

  public Long addCar(Car car) {
    if (!car.isValid()) {
      throw new IllegalArgumentException("Car is invalid");
    }
    carRepository.save(car);
    return car.getId();
  }
  /**
   * Get all cars from the database.
   * @return All cars in the database.
   */
  public Iterable<Car> getAll() {
    return carRepository.findAll();
  }

  /**
   * Try to find a car with the given ID.
   * @param id ID of the car to find
   * @return True if the car was found and False otherwise.
   */
  public Optional<Car> findById(Long id) {
    return carRepository.findById(id);
  }

  /**
   * Try to delete a car with given ID.
   * @param id ID of the car
   * @return True if the car was found and got deleted. False otherwise.
   */
  public boolean delete(Long id) {
    Optional<Car> car = carRepository.findById(id);
    if (car.isPresent()) {
      carRepository.deleteById(id);
    }
    return car.isPresent();
  }

  /**
   * Try to update a car with given ID. The car.id must match the id.
   * @param id ID of the car
   * @param car The updated car data
   * @throws IllegalArgumentException If something goes wrong.
   *                                  Error message can be used in HTTP response.
   */
  public void update(Long id, Car car) throws IllegalArgumentException {
    Optional<Car> existingCar = carRepository.findById(id);
    if (existingCar.isEmpty()) {
      throw new IllegalArgumentException("Car not found");
    }
    if (car.getId() != id) {
      throw new IllegalArgumentException("ID mismatch");
    }
    if (!car.isValid()) {
      throw new IllegalArgumentException("Car is invalid");
    }
    carRepository.save(car);
  }
}
