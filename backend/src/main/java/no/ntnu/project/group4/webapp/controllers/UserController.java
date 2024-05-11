package no.ntnu.project.group4.webapp.controllers;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

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
import no.ntnu.project.group4.webapp.services.UserService;

/**
 * The UserController class represents the REST API controller class for users.
 * 
 * <p>All HTTP requests affiliated with users are handeled in this class.</p>
 * 
 * @author Group 4
 * @version v1.1 (2024.05.10)
 */
@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
  @Autowired
  private AccessUserService accessUserService;
  @Autowired
  private UserService userService;

  /**
   * Returns a response to the request of getting all user data.
   * 
   * <p>The response body contains (1) user transfer data or (2) a string that contains an error
   * message.</p>
   * 
   * @return <p>200 OK on success + user transfer data</p>
   *         <p>401 UNAUTHORIZED if user is not authorized</p>
   *         <p>403 FORBIDDEN if user is not admin</p>
   */
  @GetMapping
  public ResponseEntity<?> getAll() {
    ResponseEntity<?> response;
    User sessionUser = this.accessUserService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      Iterable<User> users = this.userService.getAll();
      Set<UserDto> userData = new LinkedHashSet<>();
      for (User user : users) {
        UserDto userDataObj = new UserDto(user.getFirstName(), user.getLastName(), user.getEmail(),
                                          user.getPhoneNumber(), user.getDateOfBirth().getTime());
        userData.add(userDataObj);
      }
      response = new ResponseEntity<>(userData, HttpStatus.OK);
    } else if (sessionUser == null) {
      response = new ResponseEntity<>("Only authenticated users have access to all user data",
                                      HttpStatus.UNAUTHORIZED);
    } else {
      response = new ResponseEntity<>("Only admin users have access to all user data",
                                      HttpStatus.FORBIDDEN);
    }
    return response;
  }

  /**
   * Returns a response to the request of getting the user data of the user with the specified
   * email.
   * 
   * <p>The response body contains (1) user transfer data or (2) a string that contains an error
   * message.</p>
   *
   * @param email The specified email
   * @return <p>200 OK on success + user transfer data</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user email does not match email</p>
   *         <p>404 NOT FOUND if user is not found</p>
   */
  @GetMapping("/{email}")
  public ResponseEntity<?> get(@PathVariable String email) {
    ResponseEntity<?> response;
    User sessionUser = this.accessUserService.getSessionUser();
    if (sessionUser != null) {
      Optional<User> user = this.userService.getOneByEmail(email);
      if (user.isPresent()) {
        if (sessionUser.getEmail().equals(email) || sessionUser.isAdmin()) {
          User foundUser = user.get();
          UserDto userData = new UserDto(foundUser.getFirstName(), foundUser.getLastName(),
                                         foundUser.getEmail(), foundUser.getPhoneNumber(),
                                         foundUser.getDateOfBirth().getTime());
          response = new ResponseEntity<>(userData, HttpStatus.OK);
        } else {
          response = new ResponseEntity<>("Users do not have access to user data of other users",
                                          HttpStatus.FORBIDDEN);
        }
      } else {
        response = new ResponseEntity<>("User with specified email not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else {
      response = new ResponseEntity<>("Only authenticated users have access to user data",
                                      HttpStatus.UNAUTHORIZED);
    }
    return response;
  }

  // TODO Investigate HTTP response 500 INTERNAL SERVER ERROR
  /**
   * Returns a response to the request of updating the user data of the user with the specified
   * email with the specified user data.
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
   *         <p>404 NOT FOUND if user is not found</p>
   *         <p>500 INTERNAL SERVER ERROR if an error occured when updating user data</p>
   */
  @PutMapping("/user/{email}")
  public ResponseEntity<String> update(@PathVariable String email, @RequestBody UserDto userData) {
    ResponseEntity<String> response;
    User sessionUser = this.accessUserService.getSessionUser();
    if (sessionUser != null) {
      Optional<User> user = this.userService.getOneByEmail(email);
      if (user.isPresent()) {
        if (sessionUser.getEmail().equals(email) || sessionUser.isAdmin()) {
          if (userData != null) {
            if (this.accessUserService.updateUser(user.get(), userData)) {
              response = new ResponseEntity<>("", HttpStatus.OK);
            } else {
              response = new ResponseEntity<>("Could not update user data",
                                              HttpStatus.INTERNAL_SERVER_ERROR);
            }
          } else {
            response = new ResponseEntity<>("User data not supplied", HttpStatus.BAD_REQUEST);
          }
        } else {
          response = new ResponseEntity<>("Users do not have access to update user data of " +
                                          "other users", HttpStatus.FORBIDDEN);
        }
      } else {
        response = new ResponseEntity<>("User with specified email not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else {
      response = new ResponseEntity<>("Only authenticated users have access to update user data",
                                      HttpStatus.UNAUTHORIZED);
    }
    return response;
  }

  /**
   * Returns a response to the request of updating the user password of the user with the specified
   * email with the specified password.
   * 
   * <p>The response body contains a string that is empty or contains an error message.</p>
   * 
   * @param email The specified email
   * @param password The specified password
   * @return <p>200 OK on success</p>
   *         <p>400 BAD REQUEST on error</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user email does not match email</p>
   *         <p>404 NOT FOUND if user is not found</p>
   */
  @PutMapping("/password/{email}")
  public ResponseEntity<String> updatePassword(@PathVariable String email,
                                               @RequestBody String password) {
    ResponseEntity<String> response;
    User sessionUser = this.accessUserService.getSessionUser();
    if (sessionUser != null) {
      Optional<User> user = this.userService.getOneByEmail(email);
      if (user.isPresent()) {
        if (sessionUser.getEmail().equals(email) || sessionUser.isAdmin()) {
          if (password != null) {
            String errorMessage = this.accessUserService.updateUserPassword(sessionUser, password);
            if (errorMessage == null) {
              response = new ResponseEntity<>("", HttpStatus.OK);
            } else {
              response = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            }
          } else {
            response = new ResponseEntity<>("Password not supplied", HttpStatus.BAD_REQUEST);
          }
        } else {
          response = new ResponseEntity<>("Users do not have access to update user password of " +
                                          "other users", HttpStatus.FORBIDDEN);
        }
      } else {
        response = new ResponseEntity<>("User with specified email not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else {
      response = new ResponseEntity<>("Only authenticated users have access to update user " +
                                      "password", HttpStatus.UNAUTHORIZED);
    }
    return response;
  }

  /**
   * Returns a response to the request of deleting the user with the specified email.
   * 
   * <p>The response body contains a string that is empty or contains an error message.</p>
   * 
   * @param email The specified email
   * @return <p>200 OK on success</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user email does not match email</p>
   *         <p>404 NOT FOUND if user is not found</p>
   */
  @DeleteMapping("/{email}")
  public ResponseEntity<String> delete(@PathVariable String email) {
    ResponseEntity<String> response;
    User sessionUser = this.accessUserService.getSessionUser();
    if (sessionUser != null) {
      Optional<User> user = this.userService.getOneByEmail(email);
      if (user.isPresent()) {
        if (sessionUser.getEmail().equals(email) || sessionUser.isAdmin()) {
          this.accessUserService.deleteUser(user.get());
          response = new ResponseEntity<>("", HttpStatus.OK);
        } else {
          response = new ResponseEntity<>("Users do not have access to delete other users",
                                          HttpStatus.FORBIDDEN);
        }
      } else {
        response = new ResponseEntity<>("User with specified email not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else {
      response = new ResponseEntity<>("Only authenticated users have access to delete users",
                                      HttpStatus.UNAUTHORIZED);
    }
    return response;
  }
}
