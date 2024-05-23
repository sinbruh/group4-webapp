package no.ntnu.project.group4.webapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Iterator;
import java.util.Optional;
import no.ntnu.project.group4.webapp.models.Car;
import no.ntnu.project.group4.webapp.models.Configuration;
import no.ntnu.project.group4.webapp.models.Provider;
import no.ntnu.project.group4.webapp.models.User;
import no.ntnu.project.group4.webapp.services.AccessUserService;
import no.ntnu.project.group4.webapp.services.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * The CarController class represents the REST API controller class for cars.
 *
 * <p>All HTTP requests affiliated with cars are handled in this class.</p>
 *
 * @author Group 4
 * @version v1.6 (2024.05.22)
 */
@CrossOrigin
@RestController
@RequestMapping("/api/cars")
public class CarController {
  @Autowired
  private CarService carService;
  @Autowired
  private AccessUserService userService;

  private final Logger logger = LoggerFactory.getLogger(CarController.class);

  /**
   * Returns an iterable containing all cars. When this endpoint is requested, a HTTP 200 OK
   * response will automatically be sent back.
   * 
   * <p>If user is not authenticated or user is authenticated but not admin, only providers who are
   * visible are included.</p>
   *
   * @return 200 OK + all car data
   */
  @Operation(
      summary = "Get all cars",
      description = "Gets all cars"
  )
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200",
        description = "All car data"
      )
  })
  @GetMapping
  public Iterable<Car> getAll() {
    Iterable<Car> cars = this.carService.getAll();
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser == null || (sessionUser != null && !sessionUser.isAdmin())) {
      for (Car car : cars) {
        for (Configuration config : car.getConfigurations()) {
          Iterator<Provider> it = config.getProviders().iterator();
          while (it.hasNext()) {
            if (!it.next().isVisible()) {
              it.remove();
            }
          }
        }
      }
    }
    logger.info("Sending all car data...");
    return cars;
  }

  /**
   * Returns HTTP a response to the request requesting to get the car with the specified ID.
   *
   * <p>The response body contains car data on success or a string with an error message on
   * error.</p>
   * 
   * <p>If user is not authenticated or user is authenticated but not admin, only providers who are
   * visible are included.</p>
   *
   * @param id The specified ID
   * @return <p>200 OK on success + car data</p>
   *         <p>404 NOT FOUND if car is not found</p>
   */
  @Operation(
      summary = "Get car by ID",
      description = "Gets the car with the specified ID"
  )
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200",
        description = "Car data"
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Car with specified ID not found"
      )
  })
  @GetMapping("/{id}")
  public ResponseEntity<?> get(
      @Parameter(description = "The ID of the car to get")
      @PathVariable Long id
  ) {
    ResponseEntity<?> response;
    User sessionUser = this.userService.getSessionUser();
    Optional<Car> car = this.carService.getOne(id);
    if (car.isPresent()) {
      Car existingCar = car.get();
      if (sessionUser == null || (sessionUser != null && !sessionUser.isAdmin())) {
        for (Configuration config : existingCar.getConfigurations()) {
          Iterator<Provider> it = config.getProviders().iterator();
          if (it.hasNext()) {
            if (!it.next().isVisible()) {
              it.remove();
            }
          }
        }
      }
      logger.info("Car found, sending car data...");
      response = new ResponseEntity<>(existingCar, HttpStatus.OK);
    } else {
      logger.error("Car not found, sending error message...");
      response = new ResponseEntity<>("Car with specified ID not found", HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request requesting to add the specified car.
   *
   * <p>The response body contains the generated ID of the specified car on success or a string
   * with an error message on error.</p>
   *
   * @param car The specified car
   * @return <p>201 CREATED on success + ID</p>
   *         <p>400 BAD REQUEST on error</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user is not admin</p>
   */
  @Operation(
      summary = "Add car",
      description = "Adds the specified car"
  )
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "201",
        description = "Car added + ID of car"
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Error adding car + error message"
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Only authenticated users have access to add cars"
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Only admin users have access to add cars"
      )
  })
  @PostMapping
  public ResponseEntity<?> add(
      @Parameter(description = "The car to add")
      @RequestBody Car car
  ) {
    ResponseEntity<?> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      try {
        this.carService.add(car);
        logger.info("Valid car data, sending generated ID of new car...");
        response = new ResponseEntity<>(car.getId(), HttpStatus.CREATED);
      } catch (IllegalArgumentException e) {
        logger.error("Invalid car data, sending error message...");
        response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
      }
    } else if (sessionUser == null) {
      logger.error("User not authenticated, sending error message...");
      response = new ResponseEntity<>("Only authenticated users have access to add cars",
          HttpStatus.UNAUTHORIZED);
    } else {
      logger.error("User not admin, sending error message...");
      response = new ResponseEntity<>("Only admin users have access to add cars",
          HttpStatus.FORBIDDEN);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request requesting to update the car with the specified ID with
   * the specified car.
   *
   * <p>The response body contains an empty string on success or a string with an error message on
   * error.</p>
   *
   * @param id  The specified ID
   * @param car The specified car
   * @return <p>200 OK on success</p>
   *         <p>400 BAD REQUEST on error</p>
   *         <p>401 UNAUTHORIZED if user is not authorized</p>
   *         <p>403 FORBIDDEN if user is not admin</p>
   *         <p>404 NOT FOUND if car is not found</p>
   */
  @Operation(
      summary = "Update car",
      description = "Updates the car with the specified ID with the specified car"
  )
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200",
        description = "Car updated"
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Error updating car + error message"
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Only authenticated users have access to update cars"
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Only admin users have access to update cars"
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Car with specified ID not found"
      )
  })
  @PutMapping("/{id}")
  public ResponseEntity<String> update(
      @Parameter(description = "The ID of the car to update")
      @PathVariable Long id,
      @Parameter(description = "The car to update the existing car with")
      @RequestBody Car car
  ) {
    ResponseEntity<String> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      try {
        if (this.carService.update(id, car)) {
          logger.info("Car found and valid car data, updating car...");
          response = new ResponseEntity<>("", HttpStatus.OK);
        } else {
          logger.error("Car not found, sending error message...");
          response = new ResponseEntity<>("Car with specified ID not found", HttpStatus.NOT_FOUND);
        }
      } catch (IllegalArgumentException e) {
        logger.error("Invalid car data, sending error message...");
        response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
      }
    } else if (sessionUser == null) {
      logger.error("User not authenticated, sending error message...");
      response = new ResponseEntity<>("Only authenticated users have access to update cars",
                                      HttpStatus.UNAUTHORIZED);
    } else {
      logger.error("User not admin, sending error message...");
      response = new ResponseEntity<>("Only admin users have access to update cars",
                                      HttpStatus.FORBIDDEN);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request requesting to delete the car with the specified ID.
   *
   * <p>The response body contains an empty string on success or a string with an error message on
   * error.</p>
   *
   * @param id The specified ID
   * @return <p>200 OK on success</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user is not admin</p>
   *         <p>404 NOT FOUND if car is not found</p>
   */
  @Operation(
      summary = "Delete car",
      description = "Deletes the car with the specified ID"
  )
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200",
        description = "Car deleted"
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Only authenticated users have access to delete cars"
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Only admin users have access to delete cars"
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Car with specified ID not found"
      )
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(
      @Parameter(description = "The ID of the car to delete")
      @PathVariable Long id
  ) {
    ResponseEntity<String> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      if (this.carService.delete(id)) {
        logger.info("Car found, deleting car...");
        response = new ResponseEntity<>("", HttpStatus.OK);
      } else {
        logger.error("Car not found, sending error message...");
        response = new ResponseEntity<>("Car with specified ID not found", HttpStatus.NOT_FOUND);
      }
    } else if (sessionUser == null) {
      logger.error("User not authenticated, sending error message...");
      response = new ResponseEntity<>("Only authenticated users have access to delete cars",
                                      HttpStatus.UNAUTHORIZED);
    } else {
      logger.error("User not admin, sending error message...");
      response = new ResponseEntity<>("Only admin users have access to delete cars",
                                      HttpStatus.FORBIDDEN);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request causing the specified
   * MethodArgumentTypeMismatchException.
   *
   * @param e The specified MethodArgumentTypeMismatchException
   * @return 400 BAD REQUEST with an error message
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<String> handlePathVarException(MethodArgumentTypeMismatchException e) {
    logger.error("Received HTTP request could not be read, sending error message...");
    return new ResponseEntity<>("HTTP request contains a value on an invalid format",
                                HttpStatus.BAD_REQUEST);
  }

  /**
   * Returns a HTTP response to the request causing the specified HttpMessageNotReadableException.
   *
   * @param e The specified HttpMessageNotReadableException
   * @return 400 BAD REQUEST with an error message
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<String> handleRequestBodyException(HttpMessageNotReadableException e) {
    logger.error("Received car data could not be read, sending error message...");
    return new ResponseEntity<>("Car data not supplied or contains a parameter on an invalid "
                              + "format", HttpStatus.BAD_REQUEST);
  }
}
