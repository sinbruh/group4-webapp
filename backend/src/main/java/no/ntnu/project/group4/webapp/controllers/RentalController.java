package no.ntnu.project.group4.webapp.controllers;

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

import no.ntnu.project.group4.webapp.models.Configuration;
import no.ntnu.project.group4.webapp.models.Rental;
import no.ntnu.project.group4.webapp.models.User;
import no.ntnu.project.group4.webapp.services.AccessUserService;
import no.ntnu.project.group4.webapp.services.ConfigurationService;
import no.ntnu.project.group4.webapp.services.RentalService;
import no.ntnu.project.group4.webapp.services.UserService;

import java.util.Optional;

/**
 * The RentalController class represents the REST API controller class for rentals.
 * 
 * <p>All HTTP requests affiliated with rentals are handeled in this class.</p>
 * 
 * @author Group 4
 * @version v1.1 (2024.05.10)
 */
@CrossOrigin
@RestController
@RequestMapping("/api/rentals")
public class RentalController {
  @Autowired
  private RentalService rentalService;
  @Autowired
  private ConfigurationService configurationService;
  @Autowired
  private AccessUserService accessUserService;
  @Autowired
  private UserService userService;

  /**
   * Returns a response to the request of getting all rentals.
   * 
   * <p>The response body contains (1) rental data or (2) a string that contains an error
   * message.</p>
   * 
   * @return <p>200 OK on success + rental data</p>
   *         <p>401 UNAUTHORIZED if user is not authorized</p>
   *         <p>403 FORBIDDEN if user is not admin</p>
   */
  @GetMapping
  public ResponseEntity<?> getAll() {
    ResponseEntity<?> response;
    User sessionUser = this.accessUserService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      response = new ResponseEntity<>(this.rentalService.getAll(), HttpStatus.OK);
    } else if (sessionUser == null) {
      response = new ResponseEntity<>("Only authenticated users have access to rental data",
                                      HttpStatus.UNAUTHORIZED);
    } else {
      response = new ResponseEntity<>("Only admin users have access to rental data",
                                      HttpStatus.FORBIDDEN);
    }
    return response;
  }

  /**
   * Returns a response to the request of getting the rental with the specified ID.
   * 
   * <p>The response body contains (1) rental data or (2) a string that contains an error
   * message.</p>
   * 
   * @param id The specified ID
   * @return <p>200 OK on success + rental data</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user email does not match email of rental user</p>
   *         <p>404 NOT FOUND if rental is not found</p>
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> get(@PathVariable Long id) {
    ResponseEntity<?> response;
    User sessionUser = this.accessUserService.getSessionUser();
    if (sessionUser != null) {
      Optional<Rental> rental = this.rentalService.getOne(id);
      if (rental.isPresent()) {
        Rental foundRental = rental.get();
        if (sessionUser.getEmail().equals(foundRental.getUser().getEmail()) ||
            sessionUser.isAdmin()) {
          response = new ResponseEntity<>(foundRental, HttpStatus.OK);
        } else {
          response = new ResponseEntity<>("Users do not have access to rental data of other users",
                                          HttpStatus.FORBIDDEN);
        }
      } else {
        response = new ResponseEntity<>("Rental with specified ID not found", HttpStatus.NOT_FOUND);
      }
    } else {
      response = new ResponseEntity<>("Only authenticated users have access to rental data",
                                      HttpStatus.UNAUTHORIZED);
    }
    return response;
  }

  /**
   * Returns a response to the request of adding the specified rental to the user with the
   * specified email and the configuration with the specified ID.
   * 
   * <p>The response body contains a string that is empty or contains an error message.</p>
   * 
   * @param id The specified ID
   * @param rental The specified rental
   * @return <p>201 CREATED on success</p>
   *         <p>400 BAD REQUEST on error</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user email does not match email of rental user</p>
   *         <p>404 NOT FOUND if user or configuration is not found</p>
   */
  @PostMapping("/users/{email}/configurations/{id}")
  public ResponseEntity<String> add(@PathVariable String email, @PathVariable Long id,
                                    @RequestBody Rental rental) {
    ResponseEntity<String> response;
    User sessionUser = this.accessUserService.getSessionUser();
    if (sessionUser != null) {
      Optional<User> user = this.userService.getOneByEmail(email);
      Optional<Configuration> configuration = this.configurationService.getOne(id);
      if (user.isPresent() && configuration.isPresent()) {
        if (sessionUser.getEmail().equals(email) || sessionUser.isAdmin()) {
          rental.setUser(user.get());
          rental.setConfiguration(configuration.get());
          try {
            this.rentalService.add(rental);
            response = new ResponseEntity<>("", HttpStatus.CREATED);
          } catch (IllegalArgumentException e) {
            response = new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
          }
        } else {
          response = new ResponseEntity<>("Users do not have access to add rental data of other " +
                                          "users", HttpStatus.FORBIDDEN);
        }
      } else if (!user.isPresent()) {
        response = new ResponseEntity<>("User with specified email not found",
                                        HttpStatus.NOT_FOUND);
      } else {
        response = new ResponseEntity<>("Configuration with specified ID not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else {
      response = new ResponseEntity<>("Only authenticated users have access to add rentals",
                                      HttpStatus.UNAUTHORIZED);
    }
    return response;
  }

  /**
   * Returns a response to the request of deleting the rental with the specified ID.
   * 
   * <p>The response body contains a string that is empty or contains an error message.</p>
   * 
   * @param id The specified ID
   * @return <p>200 OK on success</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user email does not match email of rental user</p>
   *         <p>404 NOT FOUND if rental is not found</p>
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id) {
    ResponseEntity<String> response;
    User sessionUser = this.accessUserService.getSessionUser();
    if (sessionUser != null) {
      Optional<Rental> rental = this.rentalService.getOne(id);
      if (rental.isPresent()) {
        if (sessionUser.getEmail().equals(rental.get().getUser().getEmail()) ||
            sessionUser.isAdmin()) {
          this.rentalService.delete(id);
          response = new ResponseEntity<>("", HttpStatus.OK);
        } else {
          response = new ResponseEntity<>("Users do not have access to delete rentals of other " +
                                          "users", HttpStatus.FORBIDDEN);
        }
      } else {
        response = new ResponseEntity<>("Rental with specified ID is not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else {
      response = new ResponseEntity<>("Only authenticated users have access to delete rentals",
                                      HttpStatus.UNAUTHORIZED);
    }
    return response;
  }
}
