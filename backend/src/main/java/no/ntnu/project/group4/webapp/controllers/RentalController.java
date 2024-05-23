package no.ntnu.project.group4.webapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Optional;
import no.ntnu.project.group4.webapp.models.Provider;
import no.ntnu.project.group4.webapp.models.Rental;
import no.ntnu.project.group4.webapp.models.User;
import no.ntnu.project.group4.webapp.services.AccessUserService;
import no.ntnu.project.group4.webapp.services.ProviderService;
import no.ntnu.project.group4.webapp.services.RentalService;
import no.ntnu.project.group4.webapp.services.UserService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * The RentalController class represents the REST API controller class for rentals.
 *
 * <p>All HTTP requests affiliated with rentals are handled in this class.</p>
 *
 * @author Group 4
 * @version v1.5 (2024.05.22)
 */
@CrossOrigin
@RestController
@RequestMapping("/api/rentals")
public class RentalController {
  @Autowired
  private RentalService rentalService;
  @Autowired
  private ProviderService providerService;
  @Autowired
  private AccessUserService accessUserService;
  @Autowired
  private UserService userService;

  private final Logger logger = LoggerFactory.getLogger(RentalController.class);

  /**
   * Returns a HTTP response to the request requesting to get all rentals.
   *
   * <p>The response body contains rental data on success or a string with an error message on
   * error.</p>
   *
   * @return <p>200 OK on success + all rental data</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user is not admin</p>
   */
  @Operation(
      summary = "Get all rentals",
      description = "Gets all rentals"
  )
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200",
        description = "All rental data"
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Only authenticated users have access to rental data"
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Only admin users have access to rental data"
      )
  })
  @GetMapping
  public ResponseEntity<?> getAll() {
    ResponseEntity<?> response;
    User sessionUser = this.accessUserService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      logger.info("Sending all rental data...");
      response = new ResponseEntity<>(this.rentalService.getAll(), HttpStatus.OK);
    } else if (sessionUser == null) {
      logger.error("User not authenticated, sending error message...");
      response = new ResponseEntity<>("Only authenticated users have access to rental data",
                                      HttpStatus.UNAUTHORIZED);
    } else {
      logger.error("User not admin, sending error message...");
      response = new ResponseEntity<>("Only admin users have access to rental data",
                                      HttpStatus.FORBIDDEN);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request requesting to get the rental with the specified ID.
   *
   * <p>The response body contains rental data on success or a string with an error message on
   * error.</p>
   *
   * @param id The specified ID
   * @return <p>200 OK on success + rental data</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user email does not match email of rental user</p>
   *         <p>404 NOT FOUND if rental is not found</p>
   */
  @Operation(
      summary = "Get rental by ID",
      description = "Gets the rental with the specified ID"
  )
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200",
        description = "Rental data"
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Only authenticated users have access to rental data"
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Users do not have access to rental data of other users"
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Rental with specified ID not found"
      )
  })
  @GetMapping("/{id}")
  public ResponseEntity<?> get(
      @Parameter(description = "The ID of the rental to get")
      @PathVariable Long id
  ) {
    ResponseEntity<?> response;
    User sessionUser = this.accessUserService.getSessionUser();
    if (sessionUser != null) {
      Optional<Rental> rental = this.rentalService.getOne(id);
      if (rental.isPresent()) {
        Rental foundRental = rental.get();
        if (sessionUser.getEmail().equals(foundRental.getUser().getEmail())
            || sessionUser.isAdmin()) {
          logger.info("Rental found, sending rental data...");
          response = new ResponseEntity<>(foundRental, HttpStatus.OK);
        } else {
          logger.error("Email of rental user does not match email of session user, sending "
                     + "error message...");
          response = new ResponseEntity<>("Users do not have access to rental data of other users",
                                          HttpStatus.FORBIDDEN);
        }
      } else {
        logger.error("Rental not found, sending error message...");
        response = new ResponseEntity<>("Rental with specified ID not found", HttpStatus.NOT_FOUND);
      }
    } else {
      logger.error("User not authenticated, sending error message...");
      response = new ResponseEntity<>("Only authenticated users have access to rental data",
                                      HttpStatus.UNAUTHORIZED);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request requesting to add the specified rental to the user with
   * the specified email and the provider with the specified provider ID.
   *
   * <p>The response body contains the generated ID of the specified rental on success or a string
   * with an error message on error.</p>
   *
   * @param email      The specified email
   * @param providerId The specified provider ID
   * @param rental     The specified rental
   * @return <p>201 CREATED on success + ID</p>
   *         <p>400 BAD REQUEST on error</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user email does not match email of rental user</p>
   *         <p>404 NOT FOUND if user or provider is not found</p>
   */
  @Operation(
      summary = "Add rental",
      description = "Adds the specified rental to the user with the specified email and the "
                  + "provider with the specified provider ID"
  )
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "201",
        description = "Added renal + ID of rental"
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Error adding rental + error message"
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Only authenticated users have access to add rentals"
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Users do not have access to add rental data of other users"
      ),
      @ApiResponse(
        responseCode = "404",
        description = "User with specified email not found or provider with specified provider ID "
                    + "not found"
      )
  })
  @PostMapping("/{email}/{providerId}")
  public ResponseEntity<?> add(
      @Parameter(description = "The email of the user to add to")
      @PathVariable String email,
      @Parameter(description = "The ID of the provider to add to")
      @PathVariable Long providerId,
      @Parameter(description = "The rental to add")
      @RequestBody Rental rental
  ) {
    ResponseEntity<?> response;
    User sessionUser = this.accessUserService.getSessionUser();
    if (sessionUser != null) {
      Optional<User> user = this.userService.getOneByEmail(email);
      Optional<Provider> provider = this.providerService.getOne(providerId);
      if (user.isPresent() && provider.isPresent()) {
        if (sessionUser.getEmail().equals(user.get().getEmail()) || sessionUser.isAdmin()) {
          rental.setUser(user.get());
          rental.setProvider(provider.get());
          try {
            this.rentalService.add(rental);
            logger.info("User and provider found and valid rental data, sending generated ID of "
                      + "new rental...");
            response = new ResponseEntity<>(rental.getId(), HttpStatus.CREATED);
          } catch (IllegalArgumentException e) {
            logger.error("Invalid rental data, sending error message...");
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
          }
        } else {
          logger.error("Email of user does not match email of session user, sending error "
                     + "message...");
          response = new ResponseEntity<>("Users do not have access to add rental data of other "
                                        + "users", HttpStatus.FORBIDDEN);
        }
      } else if (!user.isPresent()) {
        logger.error("User not found, sending error message...");
        response = new ResponseEntity<>("User with specified email not found",
                                        HttpStatus.NOT_FOUND);
      } else {
        logger.error("Provider not found, sending error message...");
        response = new ResponseEntity<>("Provider with specified provider ID not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else {
      logger.error("User not authenticated, sending error message...");
      response = new ResponseEntity<>("Only authenticated users have access to add rentals",
                                      HttpStatus.UNAUTHORIZED);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request requesting to delete the rental with the specified ID.
   *
   * <p>The response body contains an empty string on success or a string with an error message on
   * error.</p>
   *
   * @param id The specified ID
   * @return <p>200 OK on success</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user email does not match email of rental user</p>
   *         <p>404 NOT FOUND if rental is not found</p>
   */
  @Operation(
      summary = "Delete rental by ID",
      description = "Deletes the rental with the specified ID"
  )
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200",
        description = "Rental deleted"
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Only authenticated users have access to delete rentals"
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Users do not have access to delete rentals of other users"
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Rental with specified ID not found"
      )
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id) {
    ResponseEntity<String> response;
    User sessionUser = this.accessUserService.getSessionUser();
    if (sessionUser != null) {
      Optional<Rental> rental = this.rentalService.getOne(id);
      if (rental.isPresent()) {
        if (sessionUser.getEmail().equals(rental.get().getUser().getEmail())
            || sessionUser.isAdmin()) {
          logger.info("Rental found, deleting rental...");
          this.rentalService.delete(id);
          response = new ResponseEntity<>("", HttpStatus.OK);
        } else {
          logger.error("Email of rental users does not match email of session user, sending "
                     + "error message...");
          response = new ResponseEntity<>("Users do not have access to delete rentals of other "
                                        + "users", HttpStatus.FORBIDDEN);
        }
      } else {
        logger.error("Rental not found, sending error message...");
        response = new ResponseEntity<>("Rental with specified ID is not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else {
      logger.error("User not authenticated, sending error message...");
      response = new ResponseEntity<>("Only authenticated users have access to delete rentals",
                                      HttpStatus.UNAUTHORIZED);
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
    logger.error("Received rental data could not be read, sending error message...");
    return new ResponseEntity<>("Rental data not supplied or contains a parameter on an invalid "
                              + "format", HttpStatus.BAD_REQUEST);
  }
}
