package no.ntnu.project.group4.webapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import no.ntnu.project.group4.webapp.dto.ConfigurationDto;
import no.ntnu.project.group4.webapp.models.Car;
import no.ntnu.project.group4.webapp.models.Configuration;
import no.ntnu.project.group4.webapp.models.User;
import no.ntnu.project.group4.webapp.services.AccessUserService;
import no.ntnu.project.group4.webapp.services.CarService;
import no.ntnu.project.group4.webapp.services.ConfigurationService;
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
 * The ConfigurationController class represents the REST API controller class for configurations.
 *
 * <p>All HTTP requests affiliated with configurations are handled in this class.</p>
 *
 * @author Group 4
 * @version v2.0 (2024.05.14)
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

  /**
   * Returns a response to the request of getting all configuration data.
   * 
   * <p>The response body contains configuration data.</p>
   *
   * @return 200 OK + configuration data
   */
  @Operation(summary = "Get all configuration data")
  @GetMapping
  public ResponseEntity<Set<ConfigurationDto>> getAll() {
    Set<ConfigurationDto> configurationData = new LinkedHashSet<>();
    for (Configuration configuration : this.configurationService.getAll()) {
      ConfigurationDto configurationDataObj =
        new ConfigurationDto(configuration.getName(), configuration.getFuelType(),
                             configuration.getTranmissionType(), configuration.getNumberOfSeats(),
                             configuration.getLocation(), configuration.getCar().getId());
      configurationData.add(configurationDataObj);
    }
    return new ResponseEntity<>(configurationData, HttpStatus.OK);
  }

  /**
   * Returns a HTTP response to the request requesting to get the configuration data of the
   * configuration with the specified ID.
   *
   * <p>The response body contains configuration data on success or a string that contains an error
   * message otherwise.</p>
   *
   * @param id The specified ID
   * @return <p>200 OK on success + configuration data</p>
   *         <p>404 NOT FOUND if configuration is not found</p>
   */
  @Operation(
    summary = "Get configuration data of configuration by ID",
    description = "Returns the configuration data of the configuration with the specified ID")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Configuration data"),
    @ApiResponse(responseCode = "404", description = "Configuration with specified ID not found")
  })
  @GetMapping("/{id}")
  public ResponseEntity<?> get(@PathVariable Long id) {
    ResponseEntity<?> response;
    Optional<Configuration> configuration = this.configurationService.getOne(id);
    if (configuration.isPresent()) {
      Configuration foundConfiguration = configuration.get();
      ConfigurationDto configurationData =
        new ConfigurationDto(foundConfiguration.getName(), foundConfiguration.getFuelType(),
                             foundConfiguration.getTranmissionType(),
                             foundConfiguration.getNumberOfSeats(),
                             foundConfiguration.getLocation(),
                             foundConfiguration.getCar().getId());
      response = new ResponseEntity<>(configurationData, HttpStatus.OK);
    } else {
      response = new ResponseEntity<>("Configuration with specified ID not found",
                                      HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request requesting to add the specified configuration data to
   * the car with the specified car ID.
   *
   * <p>The response body contains a string that is empty on success or contains an error message
   * otherwise.</p>
   *
   * @param configuration The specified configuration
   * @param carId The specified car ID
   * @return <p>201 CREATED on success</p>
   *         <p>400 BAD REQUEST on error</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user is not admin</p>
   *         <p>404 NOT FOUND if car is not found</p>
   */
  @Operation(
    summary = "Add configuration data",
    description = "Adds the specified configuration data to the car with the specified car ID")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Configuration added"),
    @ApiResponse(responseCode = "400", description = "Error adding configuration"),
    @ApiResponse(responseCode = "401", description = "Only authenticated users have access to add configurations"),
    @ApiResponse(responseCode = "403", description = "Only admin users have access to add configurations"),
    @ApiResponse(responseCode = "404", description = "Car with specified ID not found")
  })
  @PostMapping("/{carId}")
  public ResponseEntity<String> add(@RequestBody ConfigurationDto configurationData,
                                    @PathVariable Long carId) {
    ResponseEntity<String> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      Optional<Car> car = this.carService.getOne(carId);
      if (car.isPresent()) {
        try {
          Configuration configuration = new Configuration(configurationData.getName(),
                                                          configurationData.getFuelType(),
                                                          configurationData.getTransmissionType(),
                                                          configurationData.getNumberOfSeats(),
                                                          configurationData.getLocation());
          configuration.setCar(car.get());
          this.configurationService.add(configuration);
          response = new ResponseEntity<>("", HttpStatus.CREATED);
        } catch (Exception e) {
          response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
      } else {
        response = new ResponseEntity<>("Car with specified ID not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else if (sessionUser == null) {
      response = new ResponseEntity<>("Only authenticated users have access to add configurations",
                                      HttpStatus.UNAUTHORIZED);
    } else {
      response = new ResponseEntity<>("Only admin users have access to add configurations",
                                      HttpStatus.FORBIDDEN);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request requesting to delete the configuration with the
   * specified ID.
   *
   * <p>The response body contains a string that is empty on success or contains an error message
   * otherwise.</p>
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
    @ApiResponse(responseCode = "200", description = "Configuration deleted"),
    @ApiResponse(responseCode = "401", description = "Only authenticated users have access to delete configurations"),
    @ApiResponse(responseCode = "403", description = "Only admin users have access to delete configurations"),
    @ApiResponse(responseCode = "404", description = "Configuration with specified ID not found")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id) {
    ResponseEntity<String> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      Optional<Configuration> configuration = this.configurationService.getOne(id);
      if (configuration.isPresent()) {
        this.configurationService.delete(id);
        response = new ResponseEntity<>("", HttpStatus.OK);
      } else {
        response = new ResponseEntity<>("Configuration with specified ID not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else if (sessionUser == null) {
      response = new ResponseEntity<>("Only authenticated users have access to delete " +
                                      "configurations", HttpStatus.UNAUTHORIZED);
    } else {
      response = new ResponseEntity<>("Only admin users have access to delete configurations",
                                      HttpStatus.FORBIDDEN);
    }
    return response;
  }
}
