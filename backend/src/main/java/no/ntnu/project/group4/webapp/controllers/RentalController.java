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

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/rentals")
public class RentalController {
  @Autowired
  private RentalService rentalService;
  @Autowired
  private ConfigurationService configurationService;
  @Autowired
  private AccessUserService userService;

  @GetMapping
  public Iterable<Rental> getAll() {
    return rentalService.getAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> get(@PathVariable Long id) {
    ResponseEntity<?> response;
    User sessionUser = userService.getSessionUser();
    if (sessionUser != null) {
      Optional<Rental> rental = rentalService.getOne(id);
      if (rental.isPresent()) {
        Rental foundRental = rental.get();
        if (sessionUser.getEmail().equals(foundRental.getUser().getEmail())) {
          response = new ResponseEntity<>(foundRental, HttpStatus.OK);
        } else {
          response = new ResponseEntity<>("Users do not have access to rental data of other users",
                                          HttpStatus.FORBIDDEN);
        }
      } else {
        response = new ResponseEntity<>("", HttpStatus.NOT_FOUND);
      }
    } else {
      response = new ResponseEntity<>("Only authenticated users have access to rental data",
                                      HttpStatus.UNAUTHORIZED);
    }
    return response;
  }

  /**
   * Adds the specified rental to the session user and the configuration with the specified ID in
   * the database.
   * 
   * @param id The specified ID
   * @param rental The specified rental
   * @return <p>201 CREATED on success</p>
   *         <p>400 BAD REQUEST on error</p>
   *         <p>404 NOT FOUND if configuration was not found</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   */
  @PostMapping("/configurations/{id}")
  public ResponseEntity<String> addRental(@PathVariable Long id, @RequestBody Rental rental) {
    ResponseEntity<String> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null) {
      Optional<Configuration> configuration = this.configurationService.getOne(id);
      if (configuration.isPresent()) {
        rental.setUser(this.userService.getSessionUser());
        rental.setConfiguration(configuration.get());
        try {
          this.rentalService.add(rental);
          response = new ResponseEntity<>("", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
          response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
      } else {
        response = new ResponseEntity<>("Configuration with specified ID was not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else {
      response = new ResponseEntity<>("Only authenticated users have access to add rentals",
                                      HttpStatus.UNAUTHORIZED);
    }
    return response;
  }

  /**
   * Deletes the rental with the specified ID from the database.
   * 
   * @param id The specified ID
   * @return <p>200 OK on success</p>
   *         <p>403 FORBIDDEN if user is not owner of rental</p>
   *         <p>404 NOT FOUND on error</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteRental(@PathVariable Long id) {
    ResponseEntity<String> response;
    User sessionUser = userService.getSessionUser();
    if (sessionUser != null) {
      Optional<Rental> rental = rentalService.getOne(id);
      if (rental.isPresent()) {
        if (sessionUser.getEmail().equals(rental.get().getUser().getEmail())) {
          rentalService.delete(id);
          response = new ResponseEntity<>("", HttpStatus.OK);
        } else {
          response = new ResponseEntity<>("Users do not have access to delete rentals of other " +
                                          "users", HttpStatus.FORBIDDEN);
        }
      } else {
        response = new ResponseEntity<>("", HttpStatus.NOT_FOUND);
      }
    } else {
      response = new ResponseEntity<>("Only authenticated users have access to delete rentals",
                                      HttpStatus.UNAUTHORIZED);
    }
    return response;
  }
}
