package no.ntnu.project.group4.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.ntnu.project.group4.webapp.models.User;
import no.ntnu.project.group4.webapp.services.UserService;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping ("/api/users")
public class UserController {
  @Autowired
  private AcessUserService userService;

  /**
   * Returns user information.
   *
   * @param email Email for which the user is requested
   * @return The user information or error code when not authorized
   */
  @GetMapping("/{email}")
  public ResponseEntity<?> getUser(@PathVariable String email) {
    User sessionUser = userService.getSessionUser();
    if (sessionUser != null && sessionUser.getEmail().equals(email)) {
      UserDto userData = new UserDto(sessionUser.getFirstName(), sessionUser.getLastName(),
                                     sessionUser.getEmail(), sessionUser.getPhoneNumber(),
                                     sessionUser.getDateOfBirth());
      return new ResponseEntity<>(userData, HttpStatus.OK);
    } else if (sessionUser == null) {
      return new ResponseEntity<>("User data accessible only to authenticated users",
                                  HttpStatus.UNAUTHORIZED);
    } else {
      return new ResponseEntity<>("User data for other users not accessible",
                                  HttpStatus.FORBIDDEN);
    }
  }

  /**
   * Updates user information.
   *
   * @param email Email for which the user is updated
   * @return HTTP 200 OK or error code with error message
   */
  @PutMapping("/{email}")
  public ResponseEntity<String> updateUser(@PathVariable String email,
                                           @RequestBody UserDto userData) {
    User sessionUser = userService.getSessionUser();
    ResponseEntity<String> response;
    if (sessionUser != null && sessionUser.getEmail().equals(email)) {
      if (userData != null) {
        if (userService.updateUser(sessionUser, userData)) {
          response = new ResponseEntity<>("", HttpStatus.OK);
        } else {
          response = new ResponseEntity<>("Could not update user data",
                                          HttpStatus.INTERNAL_SERVER_ERROR);
        }
      } else {
        response = new ResponseEntity<>("User data not supplied", HttpStatus.BAD_REQUEST);
      }
    } else if (sessionUser == null) {
      response = new ResponseEntity<>("User data accessible only to authenticated users",
                                      HttpStatus.UNAUTHORIZED);
    } else {
      response = new ResponseEntity<>("User data for other users not accessible",
                                      HttpStatus.FORBIDDEN);
    }
    return response;
  }
}
