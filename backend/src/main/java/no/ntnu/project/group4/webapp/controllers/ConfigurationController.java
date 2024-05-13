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
import no.ntnu.project.group4.webapp.models.Configuration;
import no.ntnu.project.group4.webapp.models.User;
import no.ntnu.project.group4.webapp.services.AccessUserService;
import no.ntnu.project.group4.webapp.services.CarService;
import no.ntnu.project.group4.webapp.services.ConfigurationService;

/**
 * The ConfigurationController class represents the REST API controller class for configurations.
 * 
 * <p>All HTTP requests affiliated with configurations are handeled in this class.</p>
 * 
 * @author Group 4
 * @version v1.0 (2024.05.09)
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
   * Returns an iterable containing all configurations. When this endpoint is requested, a HTTP 200
   * OK response will automatically be sent back.
   * 
   * @return 200 OK + configuration data
   */
  @GetMapping
  public Iterable<Configuration> getAll() {
    return this.configurationService.getAll();
  }

  /**
   * Returns a response to the request of getting the configuration with the specified ID.
   * 
   * <p>The response body contains (1) configuration data or (2) a string that contains an error
   * message.</p>
   * 
   * @param id The specified ID
   * @return <p>200 OK on success + configuration data</p>
   *         <p>404 NOT FOUND if configuration is not found</p>
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> get(@PathVariable Long id) {
    ResponseEntity<?> response;
    Optional<Configuration> configuration = this.configurationService.getOne(id);
    if (configuration.isPresent()) {
      response = new ResponseEntity<>(configuration.get(), HttpStatus.OK);
    } else {
      response = new ResponseEntity<>("Configuration with specified ID not found",
                                      HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Returns a response to the request of adding the specified configuration to the car with the
   * specified ID.
   * 
   * <p>The response body contains a string that is empty or contains an error message.</p>
   * 
   * @param id The specified ID
   * @param configuration The specified configuration
   * @return <p>201 CREATED on success</p>
   *         <p>400 BAD REQUEST on error</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user is not admin</p>
   *         <p>404 NOT FOUND if car is not found</p>
   */
  @PostMapping("/cars/{id}")
  public ResponseEntity<String> add(@PathVariable Long id,
                                    @RequestBody Configuration configuration) {
    ResponseEntity<String> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      Optional<Car> car = this.carService.getOne(id);
      if (car.isPresent()) {
        configuration.setCar(car.get());
        try {
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
   * Returns a response to the request of deleting the configuration with the specified ID.
   * 
   * <p>The response body contains a string that is empty or contains an error message.</p>
   * 
   * @param id The specified ID
   * @return <p>200 OK on success</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user is not admin</p>
   *         <p>404 NOT FOUND if configuration is not found</p>
   */
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
