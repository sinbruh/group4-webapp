package no.ntnu.project.group4.webapp.controllers;

import java.io.IOException;
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

import no.ntnu.project.group4.webapp.dto.AuthenticationRequest;
import no.ntnu.project.group4.webapp.dto.AuthenticationResponse;
import no.ntnu.project.group4.webapp.dto.RegisterDto;
import no.ntnu.project.group4.webapp.security.JwtUtil;
import no.ntnu.project.group4.webapp.services.AccessUserService;

/**
 * Controller responsible for authentication.
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
   * HTTP POST request to /authenticate.
   *
   * @param authenticationRequest The request JSON object containing email and password
   * @return <p>200 OK on success + JWT token</p>
   *         <p>401 UNAUTHORIZED if invalid email or password</p>
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
   * This method processes data received from the register form (HTTP POST).
   *
   * @return <p>200 OK on success</p>
   *         <p>400 BAD REQUEST on error</p>
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
