package no.ntnu.project.group4.webapp.controllers;

import java.io.IOException;
import no.ntnu.project.group4.webapp.dto.AuthenticationRequest;
import no.ntnu.project.group4.webapp.dto.AuthenticationResponse;
import no.ntnu.project.group4.webapp.dto.RegisterDto;
import no.ntnu.project.group4.webapp.security.JwtUtil;
import no.ntnu.project.group4.webapp.services.AccessUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The AuthenticationController class represents the REST API controller class for authentication.
 *
 * <p>All HTTP requests affiliated with authentication is handeled in this class.</p>
 *
 * @author Group 4
 * @version v1.0 (2024.05.09)
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

  /**
   * Returns a response to the request of authenticating the user in the specified authentication
   * request.
   *
   * <p>The response body contains (1) a JWT token or (2) a string that contains an error
   * message.</p>
   *
   * @param authenticationRequest The specified authentication request
   * @return <p>200 OK on success + JWT token</p>
   * <p>401 UNAUTHORIZED if invalid email or password</p>
   */
  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
    ResponseEntity<?> response;
    try {
      this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
          authenticationRequest.getEmail(),
          authenticationRequest.getPassword())
      );
      final UserDetails userDetails = this.userService.loadUserByUsername(
          authenticationRequest.getEmail()
      );
      final String jwt = this.jwtUtil.generateToken(userDetails);
      response = new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
    } catch (BadCredentialsException e) {
      response = new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
    }
    return response;
  }

  /**
   * Returns a response to the request of registering the user in the specified register DTO.
   *
   * <p>The response body contains a string that is empty or contains an error message.</p>
   *
   * @return <p>200 OK on success</p>
   * <p>400 BAD REQUEST on error</p>
   */
  @PostMapping("/register")
  public ResponseEntity<String> registerProcess(@RequestBody RegisterDto registerData) {
    ResponseEntity<String> response;
    try {
      this.userService.tryCreateNewUser(registerData.getFirstName(), registerData.getLastName(),
          registerData.getEmail(), registerData.getPhoneNumber(),
          registerData.getPassword(), registerData.getDateOfBirth());
      response = new ResponseEntity<>("", HttpStatus.OK);
    } catch (IOException e) {
      response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return response;
  }
}
