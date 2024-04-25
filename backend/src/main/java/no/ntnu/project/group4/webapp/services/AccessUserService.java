package no.ntnu.project.group4.webapp.services;

import java.io.IOException;
import java.sql.Date;
import java.util.Optional;
import no.ntnu.project.group4.webapp.dto.UserDto;
import no.ntnu.project.group4.webapp.models.Role;
import no.ntnu.project.group4.webapp.models.User;
import no.ntnu.project.group4.webapp.repositories.RoleRepository;
import no.ntnu.project.group4.webapp.repositories.UserRepository;
import no.ntnu.project.group4.webapp.security.AccessUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

/**
 * Provides AccessUserDetails needed for authentication.
 */
@Service
public class AccessUserService implements UserDetailsService {
  private static final int MIN_PASSWORD_LENGTH = 8;
  @Autowired
  UserRepository userRepository;
  @Autowired
  RoleRepository roleRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByEmail(username);
    if (user.isPresent()) {
      return new AccessUserDetails(user.get());
    } else {
      throw new UsernameNotFoundException("User " + username + "not found");
    }
  }

  /**
   * Returns the user which is authenticated for the current session.
   *
   * @return User object or null if no user has logged in
   */
  public User getSessionUser() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication authentication = securityContext.getAuthentication();
    String username = authentication.getName();
    return userRepository.findByEmail(username).orElse(null);
  }

  /**
   * Checks if user with given username exists in the database.
   *
   * @param username Username of the user to check, case-sensitive
   * @return True if user exists or false otherwise
   */
  private boolean userExists(String username) {
    try {
      loadUserByUsername(username);
      return true;
    } catch (UsernameNotFoundException e) {
      return false;
    }
  }

  /**
   * Tries to create a new user.
   *
   * @param firstName First name of the new user
   * @param lastName Last name of the new user
   * @param email Email of the new user, this will act as the username
   * @param phoneNumber Phone number of the new user
   * @param password Plaintext password of the new user
   * @param dateOfBirth Date of birth of the new user
   * @throws IOException If creation of the user failed
   */
  public void tryCreateNewUser(String firstName, String lastName, String email, int phoneNumber,
                               String password, Date dateOfBirth)
  throws IOException {
    String errorMessage;
    if (email.isBlank()) {
      errorMessage = "Email cannot be empty";
    } else if (userExists(email)) {
      errorMessage = "Email already used";
    } else {
      errorMessage = checkPasswordRequirements(password);
      if (errorMessage == null) {
        createUser(firstName, lastName, email, phoneNumber, password, dateOfBirth);
      }
    }
    if (errorMessage != null) {
      throw new IOException(errorMessage);
    }
  }


  /**
   * Checks if password matches the requirements.
   *
   * @param password A password to check
   * @return Null if all OK or error message on error
   */
  private String checkPasswordRequirements(String password) {
    String errorMessage = null;
    if (password == null || password.length() == 0) {
      errorMessage = "Password cannot be empty";
    } else if (password.length() < MIN_PASSWORD_LENGTH) {
      errorMessage = "Password must be at least " + MIN_PASSWORD_LENGTH + " characters";
    }
    return errorMessage;
  }


  /**
   * Creates a new user in the database.
   *
   * @param username Username of the new user
   * @param password Plaintext password of the new user
   */
  private void createUser(String firstName, String lastName, String email, int phoneNumber,
                          String password, Date dateOfBirth) {
    Role userRole = roleRepository.findOneByName("ROLE_USER");
    if (userRole != null) {
      User user = new User(firstName, lastName, email, phoneNumber, createHash(password),
                           dateOfBirth);
      user.addRole(userRole);
      userRepository.save(user);
    }
  }

  /**
   * Creates a secure hash of a password.
   *
   * @param password Plaintext password
   * @return BCrypt hash, with random salt
   */
  private String createHash(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  // TODO Fix return clause
  /**
   * Updates user information.
   *
   * @param user User to update
   * @param profileData Profile data to set for the user
   * @return True on success or false otherwise
   */
  public boolean updateUser(User user, UserDto userData) {
    user.setFirstName(userData.getFirstName());
    user.setLastName(userData.getLastName());
    user.setEmail(userData.getEmail());
    user.setPhoneNumber(userData.getPhoneNumber());
    user.setDateOfBirth(userData.getDateOfBirth());
    userRepository.save(user);
    return true;
  }

  /**
   * Updates user password.
   * 
   * @param user User to update
   * @param password Password to set for the user
   * @return A string containing an error message, null if no errors occured
   */
  public String updateUserPassword(User user, String password) {
    String errorMessage = checkPasswordRequirements(password);
    if (errorMessage == null) {
      user.setPassword(createHash(password));
      userRepository.save(user);
    }
    return errorMessage;
  }
}
