package no.ntnu.project.group4.webapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import no.ntnu.project.group4.webapp.dto.CarDto;
import no.ntnu.project.group4.webapp.models.Car;
import no.ntnu.project.group4.webapp.models.User;
import no.ntnu.project.group4.webapp.services.AccessUserService;
import no.ntnu.project.group4.webapp.services.CarService;
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

/**
 * The CarController class represents the REST API controller class for cars.
 *
 * <p>All HTTP requests affiliated with cars are handled in this class.</p>
 *
 * @author Group 4
 * @version v2.0 (2024.05.14)
 */
@CrossOrigin
@RestController
@RequestMapping("/api/cars")
public class CarController {
  @Autowired
  private CarService carService;
  @Autowired
  private AccessUserService userService;

  /**
   * Returns a HTTP response to the request requesting to get all car data.
   * 
   * <p>The response body contains car data.</p>
   *
   * @return 200 OK + car data
   */
  @Operation(summary = "Get all car data")
  @GetMapping
  public ResponseEntity<Set<CarDto>> getAll() {
    Set<CarDto> carData = new LinkedHashSet<>();
    for (Car car : this.carService.getAll()) {
      CarDto carDataObj = new CarDto(car.getMake(), car.getModel(), car.getYear());
      carData.add(carDataObj);
    }
    return new ResponseEntity<>(carData, HttpStatus.OK);
  }

  /**
   * Returns a HTTP response to the request requesting to get the car data of the car with the
   * specified ID.
   *
   * <p>The response body contains car data on success or a string that contains an error message
   * otherwise.</p>
   *
   * @param id The specified ID
   * @return <p>200 OK on success + car data</p>
   *         <p>404 NOT FOUND if car is not found</p>
   */
  @GetMapping("/{id}")
  @Operation(
    summary = "Get car data of car by ID",
    description = "Returns the car data of the car with the specified ID")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Car data"),
    @ApiResponse(responseCode = "404", description = "Car with specified ID not found")
  })
  public ResponseEntity<?> get(@PathVariable Long id) {
    ResponseEntity<?> response;
    Optional<Car> car = this.carService.getOne(id);
    if (car.isPresent()) {
      Car foundCar = car.get();
      CarDto carData = new CarDto(foundCar.getMake(), foundCar.getModel(), foundCar.getYear());
      response = new ResponseEntity<>(carData, HttpStatus.OK);
    } else {
      response = new ResponseEntity<>("Car with specified ID not found", HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request requesting to add the specified car data.
   *
   * <p>The response body contains a string that is empty on success or contains an error message
   * otherwise.</p>
   *
   * @param car The specified car
   * @return <p>201 CREATED on success</p>
   *         <p>400 BAD REQUEST on error</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user is not admin</p>
   */
  @Operation(
    summary = "Add car",
    description = "Adds the specified car")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Car added"),
    @ApiResponse(responseCode = "400", description = "Error adding car"),
    @ApiResponse(responseCode = "401", description = "Only authenticated users have access to add cars"),
    @ApiResponse(responseCode = "403", description = "Only admin users have access to add cars")
  })
  @PostMapping
  public ResponseEntity<String> add(@RequestBody CarDto carData) {
    ResponseEntity<String> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      try {
        Car car = new Car(carData.getMake(), carData.getModel(), carData.getYear());
        this.carService.add(car);
        response = new ResponseEntity<>("", HttpStatus.CREATED);
      } catch (Exception e) {
        response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
      }
    } else if (sessionUser == null) {
      response = new ResponseEntity<>("Only authenticated users have access to add cars",
                                      HttpStatus.UNAUTHORIZED);
    } else {
      response = new ResponseEntity<>("Only admin users have access to add cars",
                                      HttpStatus.FORBIDDEN);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request requesting to delete the car with the specified ID.
   *
   * <p>The response body contains a string that is empty on success or contains an error message
   * otherwise.</p>
   *
   * @param id The specified ID
   * @return <p>200 OK on success</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user is not admin</p>
   *         <p>404 NOT FOUND if car is not found</p>
   */
  @Operation(
      summary = "Delete car",
      description = "Deletes the car with the specified ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Car deleted"),
      @ApiResponse(responseCode = "401", description = "Only authenticated users have access to delete cars"),
      @ApiResponse(responseCode = "403", description = "Only admin users have access to delete cars"),
      @ApiResponse(responseCode = "404", description = "Car with specified ID not found")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id) {
    ResponseEntity<String> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      Optional<Car> car = this.carService.getOne(id);
      if (car.isPresent()) {
        this.carService.delete(id);
        response = new ResponseEntity<>("", HttpStatus.OK);
      } else {
        response = new ResponseEntity<>("Car with specified ID not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else if (sessionUser == null) {
      response = new ResponseEntity<>("Only authenticated users have access to delete cars",
                                      HttpStatus.UNAUTHORIZED);
    } else {
      response = new ResponseEntity<>("Only admin users have access to delete cars",
                                      HttpStatus.FORBIDDEN);
    }
    return response;
  }
}
