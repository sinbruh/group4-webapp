package no.ntnu.project.group4.webapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.IOException;
import no.ntnu.project.group4.webapp.dto.AuthenticationRequest;
import no.ntnu.project.group4.webapp.dto.AuthenticationResponse;
import no.ntnu.project.group4.webapp.dto.RegisterDto;
import no.ntnu.project.group4.webapp.security.JwtUtil;
import no.ntnu.project.group4.webapp.services.AccessUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The AuthenticationController class represents the REST API controller class for authentication.
 *
 * <p>All HTTP requests affiliated with authentication is handled in this class.</p>
 *
 * @author Group 4
 * @version v1.2 (2024.05.22)
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
public class AuthenticationController {
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private AccessUserService userService;
  @Autowired
  private JwtUtil jwtUtil;

  private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

  /**
   * Returns a HTTP response to the request requesting to authenticate the user with the specified
   * authentication request.
   *
   * <p>The response body contains a JWT token on success or a string with an error message on
   * error.</p>
   *
   * @param authenticationRequest The specified authentication request
   * @return <p>200 OK on success + JWT token</p>
   *         <p>401 UNAUTHORIZED if invalid email or password</p>
   */

  @Operation(
      summary = "Authenticate user",
      description = "Authenticates user with the specified authentication request"
  )
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200",
        description = "User authenticated"
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Invalid email or password"
      )
  })
  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticate(
      @Parameter(description = "The authentication request")
      @RequestBody AuthenticationRequest authenticationRequest
  ) {
    try {
      this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
          authenticationRequest.getEmail(),
          authenticationRequest.getPassword()));
    } catch (BadCredentialsException e) {
      logger.error("Incorrect login credentials, sending error message...");
      return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
    }
    final UserDetails userDetails = this.userService.loadUserByUsername(
        authenticationRequest.getEmail());
    final String jwt = this.jwtUtil.generateToken(userDetails);
    logger.info("Correct login credentials, sending JWT token...");
    return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
  }

  /**
   * Returns a HTTP response to the request requesting to register a new user with the specified
   * register DTO.
   *
   * <p>The response body contains a string that is empty on success or contains an error message
   * on error.</p>
   *
   * @return <p>200 OK on success</p>
   *         <p>400 BAD REQUEST on error</p>
   */
  @Operation(
      summary = "Register a new user",
      description = "Registers a new user with the specified register DTO"
  )
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200",
        description = "New user registered"
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Error registering new user"
      )
  })
  @PostMapping("/register")
  public ResponseEntity<String> registerProcess(
      @Parameter(description = "The register DTO")
      @RequestBody RegisterDto registerData
  ) {
    ResponseEntity<String> response;
    try {
      this.userService.tryCreateNewUser(registerData.getFirstName(), registerData.getLastName(),
          registerData.getEmail(), registerData.getPhoneNumber(),
          registerData.getPassword(), registerData.getDateOfBirth());
      logger.info("Valid register data, registering new user...");
      response = new ResponseEntity<>("", HttpStatus.OK);
    } catch (IOException e) {
      logger.error("Invalid register data, sending error message...");
      response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request causing the specified HttpMessageNotReadableException.
   *
   * @param e The specified HttpMessageNotReadableException
   * @return 400 BAD REQUEST with an error message
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<String> handleRequestBodyException(HttpMessageNotReadableException e) {
    logger.error("Received user data could not be read, sending error message...");
    return new ResponseEntity<>("User data not supplied or contains a parameter on an invalid "
                              + "format", HttpStatus.BAD_REQUEST);
  }
}
