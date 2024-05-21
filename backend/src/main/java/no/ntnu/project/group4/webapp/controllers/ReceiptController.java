package no.ntnu.project.group4.webapp.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.ntnu.project.group4.webapp.models.Receipt;
import no.ntnu.project.group4.webapp.models.Rental;
import no.ntnu.project.group4.webapp.models.User;
import no.ntnu.project.group4.webapp.services.AccessUserService;
import no.ntnu.project.group4.webapp.services.ReceiptService;
import no.ntnu.project.group4.webapp.services.RentalService;
import no.ntnu.project.group4.webapp.services.UserService;

/**
 * The ReceiptController class represents the controller class for the receipt entity.
 * 
 * <p>All HTTP requests affiliated with receipts are handled in the class.</p>
 * 
 * @author Group 4
 * @version v1.0 (2024.05.21)
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

  /**
   * Returns a HTTP response to the request requesting to add the receipt with the specified total
   * price generated from the rental with the specified rental ID to the user with the specified
   * email.
   * 
   * <p>The response body contains the generated ID of the receipt on success or a string with an
   * error message on error.</p>
   * 
   * @param email The specified email
   * @param rentalId The specified rental ID
   * @param totalPrice The specified total price
   * @return <p>200 OK on success</p>
   *         <p>400 BAD REQUEST on error</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user email does not match email of rental user</p>
   *         <p>404 NOT FOUND if user or rental is not found</p>
   */
  @Operation(
    summary = "Add receipt",
    description = "Adds the receipt with the specified total price generated from the rental " +
                  "with the specified rental ID to the user with the specified email"
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
      description = "User with specified email of rental with specified rental ID not found"
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
        if (sessionUser.getEmail().equals(existingRental.getUser().getEmail()) ||
            sessionUser.isAdmin()) {
          Receipt receipt =
            new Receipt(existingRental.getProvider().getConfiguration().getCar().getMake() + " " +
                        existingRental.getProvider().getConfiguration().getCar().getModel(),
                        existingRental.getProvider().getName(),
                        existingRental.getProvider().getLocation(),
                        existingRental.getStartDate().getTime(),
                        existingRental.getEndDate().getTime(), totalPrice);
            receipt.setUser(user.get());
          try {
            this.receiptService.add(receipt);
            response = new ResponseEntity<>(receipt.getId(), HttpStatus.CREATED);
          } catch (IllegalArgumentException e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
          }
        } else {
          response = new ResponseEntity<>("Users do not have access to add receipts for other " +
                                          "users", HttpStatus.FORBIDDEN);
        }
      } else if (!user.isPresent()) {
        response = new ResponseEntity<>("User with specified email not found",
                                        HttpStatus.NOT_FOUND);
      } else {
        response = new ResponseEntity<>("Rental with specified rental ID not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else {
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
  public ResponseEntity<String> delete(@PathVariable Long id) {
    ResponseEntity<String> response;
    User sessionUser = this.accessUserService.getSessionUser();
    if (sessionUser != null) {
      Optional<Receipt> receipt = this.receiptService.getOne(id);
      if (receipt.isPresent()) {
        Receipt existingReceipt = receipt.get();
        if (sessionUser.getEmail().equals(existingReceipt.getUser().getEmail()) ||
            sessionUser.isAdmin()) {
          this.receiptService.delete(id);
          response = new ResponseEntity<>("", HttpStatus.OK);
        } else {
          response = new ResponseEntity<>("Users do not have access to delete receipts of other " +
                                          "users", HttpStatus.FORBIDDEN);
        }
      } else {
        response = new ResponseEntity<>("Receipt with specified ID not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else {
      response = new ResponseEntity<>("Only authenticated users have access to delete receipts",
                                      HttpStatus.UNAUTHORIZED);
    }
    return response;
  }
}
