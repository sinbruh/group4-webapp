package no.ntnu.project.group4.webapp.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.ntnu.project.group4.webapp.models.Car;
import no.ntnu.project.group4.webapp.services.CarService;

@CrossOrigin
@RestController
@RequestMapping("/api/cars")
public class CarController {
  @Autowired
  private CarService carService;

  @GetMapping
  public Iterable<Car> getAll() {
    return this.carService.getAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Car> get(@PathVariable Long id) {
    ResponseEntity<Car> response;
    Optional<Car> car = this.carService.getOne(id);
    if (car.isPresent()) {
      response = ResponseEntity.ok(car.get());
    } else {
      response = ResponseEntity.notFound().build();
    }
    return response;
  }

  /**
   * Adds the specified car to the database.
   * 
   * @param car The specified car
   * @return 201 CREATED on success or 400 BAD REQUEST on error
   */
  @PostMapping
  public ResponseEntity<String> add(@RequestBody Car car) {
    ResponseEntity<String> response;
    try {
      this.carService.add(car);
      response = new ResponseEntity<>("", HttpStatus.CREATED);
    } catch (Exception e) {
      response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return response;
  }

  /**
   * Deletes the car with the specified ID from the database.
   * 
   * @param id The specified ID
   * @return 200 OK on success or 404 NOT FOUND on error
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id) {
    ResponseEntity<String> response;
    Optional<Car> car = this.carService.getOne(id);
    if (car.isPresent()) {
      this.carService.delete(id);
      response = new ResponseEntity<>("", HttpStatus.OK);
    } else {
      response = new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }
    return response;
  }
}
