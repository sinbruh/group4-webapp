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

/**
 * The UserController class represents the REST API controller class for users.
 * 
 * <p>All HTTP requests affiliated with users are handeled in this class.</p>
 * 
 * @author Group 4
 * @version v1.0 (2024.05.09)
 */
@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
  @Autowired
  private AccessUserService userService;

  /**
   * Returns a HTTP response to the request of getting the user data of the user with the specified
   * email.
   * 
   * <p>The response body contains (1) user data or (2) a string that contains an error
   * message.</p>
   *
   * @param email The specified email
   * @return <p>200 OK on success + user data</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user email does not match email</p>
   */
  @GetMapping("/{email}")
  public ResponseEntity<?> get(@PathVariable String email) {
    ResponseEntity<?> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.getEmail().equals(email)) {
      UserDto userData = new UserDto(sessionUser.getFirstName(), sessionUser.getLastName(),
                                     sessionUser.getEmail(), sessionUser.getPhoneNumber(),
                                     sessionUser.getDateOfBirth().getTime());
      response = new ResponseEntity<>(userData, HttpStatus.OK);
    } else if (sessionUser == null) {
      response = new ResponseEntity<>("Only authenticated users have access to user data",
                                      HttpStatus.UNAUTHORIZED);
    } else {
      response = new ResponseEntity<>("Users do not have access to user data of other users",
                                      HttpStatus.FORBIDDEN);
    }
    return response;
  }

  // TODO Investigate HTTP response 500 INTERNAL SERVER ERROR
  /**
   * Returns a HTTP response to the request of updating the user data of the user with the
   * specified email with the specified user data.
   * 
   * <p>All the user data is updated except the user password.</p>
   * 
   * <p>The response body contains a string that is empty or contains an error message.</p>
   *
   * @param email The specified email
   * @param userData The specified user data
   * @return <p>200 OK on success</p>
   *         <p>400 BAD REQUEST on error</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user email does not match email</p>
   *         <p>500 INTERNAL SERVER ERROR if an error occured when updating user data</p>
   */
  @PutMapping("/user/{email}")
  public ResponseEntity<String> update(@PathVariable String email, @RequestBody UserDto userData) {
    ResponseEntity<String> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.getEmail().equals(email)) {
      if (userData != null) {
        if (this.userService.updateUser(sessionUser, userData)) {
          response = new ResponseEntity<>("", HttpStatus.OK);
        } else {
          response = new ResponseEntity<>("Could not update user data",
                                          HttpStatus.INTERNAL_SERVER_ERROR);
        }
      } else {
        response = new ResponseEntity<>("User data not supplied", HttpStatus.BAD_REQUEST);
      }
    } else if (sessionUser == null) {
      response = new ResponseEntity<>("Only authenticated users have access to update user data",
                                      HttpStatus.UNAUTHORIZED);
    } else {
      response = new ResponseEntity<>("Users do not have access to update user data of other " +
                                      "users", HttpStatus.FORBIDDEN);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request of updating the user password of the user with the
   * specified email with the specified password.
   * 
   * <p>The response body contains a string that is empty or contains an error message.</p>
   * 
   * @param email The specified email
   * @param password The specified password
   * @return <p>200 OK on success</p>
   *         <p>400 BAD REQUEST on error</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user email does not match email</p>
   */
  @PutMapping("/password/{email}")
  public ResponseEntity<String> updatePassword(@PathVariable String email,
                                               @RequestBody String password) {
    ResponseEntity<String> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.getEmail().equals(email)) {
      if (password != null) {
        String errorMessage = this.userService.updateUserPassword(sessionUser, password);
        if (errorMessage == null) {
          response = new ResponseEntity<>("", HttpStatus.OK);
        } else {
          response = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
      } else {
        response = new ResponseEntity<>("Password not supplied", HttpStatus.BAD_REQUEST);
      }
    } else if (sessionUser == null) {
      response = new ResponseEntity<>("Only authenticated users have access to update user " +
                                      "password", HttpStatus.UNAUTHORIZED);
    } else {
      response = new ResponseEntity<>("Users do not have access to update user password of other" +
                                      "users", HttpStatus.FORBIDDEN);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request of deleting the user with the specified email.
   * 
   * <p>The response body contains a string that is empty or contains an error message.</p>
   * 
   * @param email The specified email
   * @return <p>200 OK on success</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user email does not match email</p>
   */
  @DeleteMapping("/{email}")
  public ResponseEntity<String> delete(@PathVariable String email) {
    ResponseEntity<String> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.getEmail().equals(email)) {
      this.userService.deleteUser(sessionUser);
      response = new ResponseEntity<>("", HttpStatus.OK);
    } else if (sessionUser == null) {
      response = new ResponseEntity<>("Only authenticated users have access to delete users",
                                      HttpStatus.UNAUTHORIZED);
    } else {
      response = new ResponseEntity<>("Users do not have access to delete other users",
                                      HttpStatus.FORBIDDEN);
    }
    return response;
  }
}
