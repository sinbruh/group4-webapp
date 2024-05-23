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
import no.ntnu.project.group4.webapp.services.ConfigurationService;
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
 * The ConfigurationController class represents the REST API controller class for configurations.
 *
 * <p>All HTTP requests affiliated with configurations are handled in this class.</p>
 *
 * @author Group 4
 * @version v1.8 (2024.05.22)
 */
@CrossOrigin
@RestController
@RequestMapping("/api/configurations")
public class ConfigurationController {
  @Autowired
  private ConfigurationService configurationService;
  @Autowired
  private CarService carService;
  @Autowired
  private AccessUserService userService;

  private final Logger logger = LoggerFactory.getLogger(ConfigurationController.class);

  /**
   * Returns an iterable containing all configurations. When this endpoint is requested, a HTTP 200
   * OK response will automatically be sent back.
   *
   * <p>If user is not authenticated or user is authenticated but not admin, only providers who are
   * visible are included.</p>
   *
   * @return 200 OK + all configuration data
   */
  @Operation(
      summary = "Get all configurations",
      description = "Gets all configurations"
  )
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200",
        description = "All configuration data"
      )
  })
  @GetMapping
  public Iterable<Configuration> getAll() {
    Iterable<Configuration> configurations = this.configurationService.getAll();
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser == null || (sessionUser != null && !sessionUser.isAdmin())) {
      for (Configuration config : configurations) {
        Iterator<Provider> it = config.getProviders().iterator();
        if (it.hasNext()) {
          if (!it.next().isVisible()) {
            it.remove();
          }
        }
      }
    }
    logger.info("Sending all configuration data...");
    return configurations;
  }

  /**
   * Returns a HTTP response to the request requesting to get the configuration with the specified
   * ID.
   *
   * <p>The response body contains configuration data on success or a string with an error message
   * on error.</p>
   * 
   * <p>If user is not authenticated or user is authenticated but not admin, only providers who are
   * visible are included.</p>
   *
   * @param id The specified ID
   * @return <p>200 OK on success + configuration data</p>
   *         <p>404 NOT FOUND if configuration is not found</p>
   */
  @Operation(
      summary = "Get configuration by ID",
      description = "Gets the configuration with the specified ID"
  )
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200",
        description = "Configuration data"
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Configuration with specified ID not found"
      )
  })
  @GetMapping("/{id}")
  public ResponseEntity<?> get(
      @Parameter(description = "The ID of the configuration to get")
      @PathVariable Long id
  ) {
    ResponseEntity<?> response;
    User sessionUser = this.userService.getSessionUser();
    Optional<Configuration> configuration = this.configurationService.getOne(id);
    if (configuration.isPresent()) {
      Configuration existingConfiguration = configuration.get();
      if (sessionUser == null || (sessionUser != null & !sessionUser.isAdmin())) {
        Iterator<Provider> it = existingConfiguration.getProviders().iterator();
        if (it.hasNext()) {
          if (!it.next().isVisible()) {
            it.remove();
          }
        }
      }
      logger.info("Configuration found, sending configuration data...");
      response = new ResponseEntity<>(existingConfiguration, HttpStatus.OK);
    } else {
      logger.error("Configuration not found, sending error message...");
      response = new ResponseEntity<>("Configuration with specified ID not found",
                                      HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request requesting to add the specified configuration to the
   * car with the specified car ID.
   *
   * <p>The response body contains the generated ID of the specified configuration on success or a
   * string with an error message on error.</p>
   *
   * @param carId         The specified car ID
   * @param configuration The specified configuration
   * @return <p>201 CREATED on success + ID</p>
   *         <p>400 BAD REQUEST on error</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user is not admin</p>
   *         <p>404 NOT FOUND if car is not found</p>
   */
  @Operation(
      summary = "Add configuration",
      description = "Adds the specified configuration to the car with the specified car ID"
  )
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "201",
        description = "Configuration added + ID of configuration"
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Error adding configuration + error message"
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Only authenticated users have access to add configurations"
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Only admin users have access to add configurations"
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Car with specified car ID not found"
      )
  })
  @PostMapping("/{carId}")
  public ResponseEntity<?> add(
      @Parameter(description = "The ID of the car to add to")
      @PathVariable Long carId,
      @Parameter(description = "The configuration to add")
      @RequestBody Configuration configuration
  ) {
    ResponseEntity<?> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      Optional<Car> car = this.carService.getOne(carId);
      if (car.isPresent()) {
        configuration.setCar(car.get());
        try {
          this.configurationService.add(configuration);
          logger.info("Car found and valid configuration data, sending generated ID of new "
                    + "configuration...");
          response = new ResponseEntity<>(configuration.getId(), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
          logger.error("Invaid configuration data, sending error message...");
          response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
      } else {
        logger.error("Car not found, sending error message...");
        response = new ResponseEntity<>("Car with specified ID not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else if (sessionUser == null) {
      logger.error("User not authenticated, sending error message...");
      response = new ResponseEntity<>("Only authenticated users have access to add configurations",
                                      HttpStatus.UNAUTHORIZED);
    } else {
      logger.error("User not admin, sending error message...");
      response = new ResponseEntity<>("Only admin users have access to add configurations",
                                      HttpStatus.FORBIDDEN);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request requesting to update the configuration with the
   * specified ID with the specified configuration.
   *
   * <p>The response body contains an empty string on success or a string with an error message on
   * error.</p>
   *
   * <p><b>NB!</b> This method does not allow updating which car the configuration belongs to.</p>
   *
   * @param id            The specified ID
   * @param configuration The specified configuration
   * @return <p>200 OK on success</p>
   *         <p>400 BAD REQUEST on error</p>
   *         <p>401 UNAUTHORIZED if user is not authorized</p>
   *         <p>403 FORBIDDEN if user is not admin</p>
   *         <p>404 NOT FOUND if configuration is not found</p>
   */
  @Operation(
      summary = "Update configuration",
      description = "Updates the configuration with the specified ID with the specified "
                  + "configuration"
  )
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200",
        description = "Configuration updated"
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Error updating configuration + error message"
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Only authenticated users have access to update configurations"
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Only admin users have access to update configurations"
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Configuration with specified ID not found"
      )
  })
  @PutMapping("/{id}")
  public ResponseEntity<String> update(
      @Parameter(description = "The ID of the configuration to update")
      @PathVariable Long id,
      @Parameter(description = "The configuration to update the existing configuration with")
      @RequestBody Configuration configuration
  ) {
    ResponseEntity<String> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      try {
        if (this.configurationService.update(id, configuration)) {
          logger.info("Configuration found and valid configuration data, updating "
                    + "configuration...");
          response = new ResponseEntity<>("", HttpStatus.OK);
        } else {
          logger.error("Configuration not found, sending error message...");
          response = new ResponseEntity<>("Configuration with specified ID not found",
                                          HttpStatus.NOT_FOUND);
        }
      } catch (IllegalArgumentException e) {
        logger.error("Invalid configuration data, sending error message...");
        response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
      }
    } else if (sessionUser == null) {
      logger.error("User not authenticated, sending error message...");
      response = new ResponseEntity<>("Only authenticated users have access to update "
                                    + "configurations", HttpStatus.UNAUTHORIZED);
    } else {
      logger.error("User not admin, sending error message...");
      response = new ResponseEntity<>("Only admin users have access to update configurations",
                                      HttpStatus.FORBIDDEN);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request requesting to delete the configuration with the
   * specified ID.
   *
   * <p>The response body contains an empty string on success or a string with an error message on
   * error.</p>
   *
   * @param id The specified ID
   * @return <p>200 OK on success</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user is not admin</p>
   *         <p>404 NOT FOUND if configuration is not found</p>
   */
  @Operation(
      summary = "Delete configuration",
      description = "Deletes the configuration with the specified ID")
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200",
        description = "Configuration deleted"
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Only authenticated users have access to delete configurations"
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Only admin users have access to delete configurations"
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Configuration with specified ID not found"
      )
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(
      @Parameter(description = "The ID of the configuration to delete")
      @PathVariable Long id
  ) {
    ResponseEntity<String> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      if (this.configurationService.delete(id)) {
        logger.info("Configuration found, deleting configuration...");
        response = new ResponseEntity<>("", HttpStatus.OK);
      } else {
        logger.error("Configuration not found, sending error message...");
        response = new ResponseEntity<>("Configuration with specified ID not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else if (sessionUser == null) {
      logger.error("User not authenticated, sending error message...");
      response = new ResponseEntity<>("Only authenticated users have access to delete "
                                    + "configurations", HttpStatus.UNAUTHORIZED);
    } else {
      logger.error("User not admin, sending error message...");
      response = new ResponseEntity<>("Only admin users have access to delete configurations",
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
    logger.error("Received configuration data could not be read, sending error message...");
    return new ResponseEntity<>("Configuration data not supplied or contains a parameter on an "
                              + "invalid format", HttpStatus.BAD_REQUEST);
  }
}
