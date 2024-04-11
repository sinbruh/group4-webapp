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
   * @return OK + JWT token or UNAUTHORIZED
   */
  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        authenticationRequest.getEmail(),
        authenticationRequest.getPassword())
      );
    } catch (BadCredentialsException e) {
      return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
    }
    final UserDetails userDetails = userService.loadUserByEmail(
      authenticationRequest.getEmail()
    );
    final String jwt = jwtUtil.generateToken(userDetails);
    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }

  /**
   * This method processes data received from the register form (HTTP POST).
   *
   * @return OK or BAD REQUEST
   */
  @PostMapping("/register")
  public ResponseEntity<String> registerProcess(@RequestBody RegisterDto registerData) {
    ResponseEntity<String> response;
    try {
      // TODO Change parameters in method call
      userService.tryCreateNewUser(registerData.getEmail(), registerData.getPassword());
      response = new ResponseEntity<>(HttpStatus.OK);
    } catch (IOException e) {
      response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return response;
  }
}
