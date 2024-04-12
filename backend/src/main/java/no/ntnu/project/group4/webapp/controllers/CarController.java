package no.ntnu.project.group4.webapp.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @GetMapping("/get")
  public Iterable<Car> getAll() {
    return carService.getAll();
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<Car> getOne(@PathVariable Long id) {
    ResponseEntity<Car> response;
    Optional<Car> car = carService.findById(id);
    if (car.isPresent()) {
      response = ResponseEntity.ok(car.get());
    } else {
      response = ResponseEntity.notFound().build();
    }
    return response;
  }

  @DeleteMapping("/delete/{id}")
  public HttpStatus deleteOne(@PathVariable Long id) {
    HttpStatus response;
    Optional<Car> car = carService.findById(id);
    if (car.isPresent()) {
      carService.delete(id);
      response = HttpStatus.ACCEPTED;
    } else {
      response = HttpStatus.NOT_FOUND;
    }
    return response;
  }
}