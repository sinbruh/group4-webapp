package no.ntnu.project.group4.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.ntnu.project.group4.webapp.dto.UserDto;
import no.ntnu.project.group4.webapp.models.User;
import no.ntnu.project.group4.webapp.services.AccessUserService;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
  @Autowired
  private AccessUserService userService;

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
                                     sessionUser.getDateOfBirth().getTime());
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
   * Updates user information except password.
   *
   * @param email Email for which the user is updated
   * @param userData User data to update the user with
   * @return HTTP 200 OK or error code with error message
   */
  @PutMapping("/user/{email}")
  public ResponseEntity<String> updateUser(@PathVariable String email,
                                           @RequestBody UserDto userData) {
    ResponseEntity<String> response;
    User sessionUser = userService.getSessionUser();
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

  /**
   * Updates user password.
   * 
   * @param email Email for which the user is updated
   * @param password Password to update the user password with
   * @return HTTP 200 OK or error code with error message
   */
  @PutMapping("/password/{email}")
  public ResponseEntity<String> updateUserPassword(@PathVariable String email,
                                                   @RequestBody String password) {
    ResponseEntity<String> response;
    User sessionUser = userService.getSessionUser();
    if (sessionUser != null && sessionUser.getEmail().equals(email)) {
      if (password != null) {
        String errorMessage = userService.updateUserPassword(sessionUser, password);
        if (errorMessage == null) {
          response = new ResponseEntity<>("", HttpStatus.OK);
        } else {
          response = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
      } else {
        response = new ResponseEntity<>("Password not supplied", HttpStatus.BAD_REQUEST);
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

  /**
   * Deletes user.
   * 
   * @param email Email for which the user is deleted
   * @return HTTP 200 OK or error code when not authorized
   */
  @DeleteMapping("/{email}")
  public ResponseEntity<String> deleteUser(@PathVariable String email) {
    ResponseEntity<String> response;
    User sessionUser = userService.getSessionUser();
    if (sessionUser != null && sessionUser.getEmail().equals(email)) {
      userService.deleteUser(sessionUser);
      response = new ResponseEntity<>("", HttpStatus.OK);
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
