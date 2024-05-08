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

  @GetMapping
  public Iterable<Configuration> getAll() {
    return this.configurationService.getAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Configuration> get(@PathVariable Long id) {
    ResponseEntity<Configuration> response;
    Optional<Configuration> configuration = this.configurationService.getOne(id);
    if (configuration.isPresent()) {
      response = ResponseEntity.ok(configuration.get());
    } else {
      response = ResponseEntity.notFound().build();
    }
    return response;
  }

  /**
   * Adds the specified configuration to the car with the specified ID in the database.
   * 
   * @param id The specified ID
   * @param configuration The specified configuration
   * @return <p>201 CREATED on success</p>
   *         <p>400 BAD REQUEST on error</p>
   *         <p>404 NOT FOUND if car was not found</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user is authenticated but not admin</p>
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
        response = new ResponseEntity<>("The car with the specified ID was not found",
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
   * Deletes the configuration with the specified ID from the database.
   * 
   * @param id The specified ID
   * @return <p>200 OK on success</p>
   *         <p>404 NOT FOUND on error</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user is authenticated but not admin</p>
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
        response = new ResponseEntity<>("", HttpStatus.NOT_FOUND);
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
