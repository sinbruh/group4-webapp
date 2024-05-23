package no.ntnu.project.group4.webapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Optional;
import no.ntnu.project.group4.webapp.models.Receipt;
import no.ntnu.project.group4.webapp.models.Rental;
import no.ntnu.project.group4.webapp.models.User;
import no.ntnu.project.group4.webapp.services.AccessUserService;
import no.ntnu.project.group4.webapp.services.ReceiptService;
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
 * The ReceiptController class represents the controller class for the receipt entity.
 *
 * <p>All HTTP requests affiliated with receipts are handled in the class.</p>
 *
 * @author Group 4
 * @version v1.3 (2024.05.22)
 */
@CrossOrigin
@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {
  @Autowired
  private ReceiptService receiptService;
  @Autowired
  private RentalService rentalService;
  @Autowired
  private UserService userService;
  @Autowired
  private AccessUserService accessUserService;

  private final Logger logger = LoggerFactory.getLogger(ReceiptController.class);

  /**
   * Returns a HTTP response to the request requesting to get all receipts.
   * 
   * <p>The response body contains all receipt data on success or a string with an error message on
   * error.</p>
   *
   * @return <p>200 OK on success + all receipt data</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user is not admin</p>
   */
  @Operation(
      summary = "Get all receipts",
      description = "Gets all receipts"
  )
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200",
        description = "All receipt data"
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Only authenticated users have access to all receipt data"
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Only admin users have access to all receipt data"
      )
  })
  @GetMapping
  public ResponseEntity<?> getAll() {
    ResponseEntity<?> response;
    User sessionUser = this.accessUserService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      logger.info("Sending all receipt data...");
      response = new ResponseEntity<>(this.receiptService.getAll(), HttpStatus.OK);
    } else if (sessionUser == null) {
      logger.error("User not authenticated, sending error message...");
      response = new ResponseEntity<>("Only authenticated users have access to all receipt data",
                                      HttpStatus.UNAUTHORIZED);
    } else {
      logger.error("User not admin, sending error message...");
      response = new ResponseEntity<>("Only admin users have access to all receipt data",
                                      HttpStatus.FORBIDDEN);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request requesting to get the receipt with the specified ID.
   *
   * <p>The response body contains receipt data on success or a string with an error message on
   * error.</p>
   *
   * @param id The specified ID
   * @return <p>200 OK on success</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user email does not match email of receipt user</p>
   *         <p>404 NOT FOUND if receipt is not found</p>
   */
  @Operation(
      summary = "Get receipt by ID",
      description = "Gets the receipt with the specified ID"
  )
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200",
        description = "Receipt data"
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Only authenticated users have access to receipt data"
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Users do not have access to receipt data for other users"
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Receipt with specified ID not found"
      )
  })
  @GetMapping("/{id}")
  public ResponseEntity<?> get(@PathVariable Long id) {
    ResponseEntity<?> response;
    User sessionUser = this.accessUserService.getSessionUser();
    if (sessionUser != null) {
      Optional<Receipt> receipt = this.receiptService.getOne(id);
      if (receipt.isPresent()) {
        Receipt existingReceipt = receipt.get();
        if (sessionUser.getEmail().equals(existingReceipt.getUser().getEmail())
            || sessionUser.isAdmin()) {
          logger.info("Receipt found, sending receipt data...");
          response = new ResponseEntity<>(existingReceipt, HttpStatus.OK);
        } else {
          logger.error("Email of receipt user does not match email of session user, sending "
                     + "error message...");
          response = new ResponseEntity<>("Users do not have access to receipt data for other "
                                        + "users", HttpStatus.FORBIDDEN);
        }
      } else {
        logger.error("Receipt not found, sending error message...");
        response = new ResponseEntity<>("Receipt with specified ID not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else {
      logger.error("User not authenticated, sending error message...");
      response = new ResponseEntity<>("Only authenticated users have access to receipt data",
                                      HttpStatus.UNAUTHORIZED);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request requesting to add the receipt with the specified total
   * price generated from the rental with the specified rental ID to the user with the specified
   * email.
   *
   * <p>The response body contains the generated ID of the receipt on success or a string with an
   * error message on error.</p>
   *
   * @param email      The specified email
   * @param rentalId   The specified rental ID
   * @param totalPrice The specified total price
   * @return <p>200 OK on success</p>
   *         <p>400 BAD REQUEST on error</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user email does not match email of rental user</p>
   *         <p>404 NOT FOUND if user or rental is not found</p>
   */
  @Operation(
      summary = "Add receipt",
      description = "Adds the receipt with the specified total price generated from the rental "
                  + "with the specified rental ID to the user with the specified email"
  )
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200",
        description = "Receipt added + ID of receipt"
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Error adding receipt + error message"
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Only authenticated users have access to add receipts"
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Users do not have access to add receipts for other users"
      ),
      @ApiResponse(
        responseCode = "404",
        description = "User with specified email or rental with specified rental ID not found"
      )
  })
  @PostMapping("/{email}/{rentalId}")
  public ResponseEntity<?> add(@PathVariable String email, @PathVariable Long rentalId,
                               @RequestBody int totalPrice) {
    ResponseEntity<?> response;
    User sessionUser = this.accessUserService.getSessionUser();
    if (sessionUser != null) {
      Optional<User> user = this.userService.getOneByEmail(email);
      Optional<Rental> rental = this.rentalService.getOne(rentalId);
      if (user.isPresent() && rental.isPresent()) {
        Rental existingRental = rental.get();
        if (sessionUser.getEmail().equals(user.get().getEmail()) || sessionUser.isAdmin()) {
          Receipt receipt =
              new Receipt(existingRental.getProvider().getConfiguration().getCar().getMake() + " "
                        + existingRental.getProvider().getConfiguration().getCar().getModel(),
                          existingRental.getProvider().getName(),
                          existingRental.getProvider().getLocation(),
                          existingRental.getStartDate().getTime(),
                          existingRental.getEndDate().getTime(), totalPrice);
          receipt.setUser(user.get());
          try {
            this.receiptService.add(receipt);
            logger.info("User and rental found and valid receipt data, sending generated ID of "
                      + "new receipt...");
            response = new ResponseEntity<>(receipt.getId(), HttpStatus.CREATED);
          } catch (IllegalArgumentException e) {
            logger.error("Invalid receipt data, sending error message...");
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
          }
        } else {
          logger.error("Email of user does not match email of session user, sending error "
                     + "message...");
          response = new ResponseEntity<>("Users do not have access to add receipts for other "
                                        + "users", HttpStatus.FORBIDDEN);
        }
      } else if (!user.isPresent()) {
        logger.error("User not found, sending error message...");
        response = new ResponseEntity<>("User with specified email not found",
                                        HttpStatus.NOT_FOUND);
      } else {
        logger.error("Rental not found, sending error message...");
        response = new ResponseEntity<>("Rental with specified rental ID not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else {
      logger.error("User not authenticated, sending error messsage...");
      response = new ResponseEntity<>("Only authenticated users have access to add receipts",
                                      HttpStatus.UNAUTHORIZED);
    }
    return response;
  }

  /**
   * Returns a response to the request requesting to delete the receipt with the specified ID.
   *
   * <p>The response body contains an empty string on success or a string with an error message on
   * error.</p>
   *
   * @param id The specifed ID
   * @return <p>200 OK on success</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user email does not match email of receipt user</p>
   *         <p>404 NOT FOUND if receipt is not found</p>
   */
  @Operation(
      summary = "Delete receipt by ID",
      description = "Deletes the receipt with the specified ID"
  )
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200",
        description = "Receipt deleted"
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Only authenticated users have access to delete receipts"
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Users do not have access to delete receipts of other users"
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Receipt with specified ID not found"
      )
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id) {
    ResponseEntity<String> response;
    User sessionUser = this.accessUserService.getSessionUser();
    if (sessionUser != null) {
      Optional<Receipt> receipt = this.receiptService.getOne(id);
      if (receipt.isPresent()) {
        Receipt existingReceipt = receipt.get();
        if (sessionUser.getEmail().equals(existingReceipt.getUser().getEmail())
            || sessionUser.isAdmin()) {
          logger.info("Receipt found, deleting receipt...");
          this.receiptService.delete(id);
          response = new ResponseEntity<>("", HttpStatus.OK);
        } else {
          logger.error("Email of receipt user does not match email of session user, sending "
                     + "error message...");
          response = new ResponseEntity<>("Users do not have access to delete receipts of other "
                                        + "users", HttpStatus.FORBIDDEN);
        }
      } else {
        logger.error("Receipt not found, sending error message...");
        response = new ResponseEntity<>("Receipt with specified ID not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else {
      logger.error("User not authenticated, sending error message...");
      response = new ResponseEntity<>("Only authenticated users have access to delete receipts",
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
    logger.error("Received total price could not be read, sending error message...");
    return new ResponseEntity<>("Total price not supplied or is on an invalid format",
                                HttpStatus.BAD_REQUEST);
  }
}
