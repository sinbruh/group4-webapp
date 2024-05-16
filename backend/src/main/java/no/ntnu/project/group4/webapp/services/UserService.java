package no.ntnu.project.group4.webapp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.ntnu.project.group4.webapp.models.User;
import no.ntnu.project.group4.webapp.repositories.UserRepository;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  /**
   * Get all users in the database.
   * 
   * @return All users in the database
   */
  public Iterable<User> getAll() {
    return this.userRepository.findAll();
  }

  /**
   * Try to find a user with the given email regardless of if it exists or not
   * 
   * @param id The given email
   * @return A user with the given email regardless of if it exists or not
   */
  public Optional<User> getOne(String email) {
    return this.userRepository.findById(email);
  }

  /**
   * Add the given user to the database.
   * 
   * @param user The given user
   * @return The email of the given user
   * @throws IllegalArgumentException If the given user is invalid
   */
  public String add(User user) {
    if (!user.isValid()) {
      throw new IllegalArgumentException("User is invalid");
    }
    this.userRepository.save(user);
    return user.getEmail();
  }

  /**
   * Try to delete a user with the given email.
   * 
   * @param id The given email
   * @return True if the user was found and thus deleted or false otherwise
   */
  public boolean delete(String email) {
    Optional<User> user = this.userRepository.findById(email);
    if (user.isPresent()) {
      this.userRepository.deleteById(email);
    }
    return user.isPresent();
  }

  /**
   * Try to update a user with the given email.
   * 
   * @param id The given email
   * @param user The updated user metadata
   * @throws IllegalArgumentException If the current user is not found or the updated user metadata
   *                                  has an email mismatch or is invalid
   */
  public void update(String email, User user) {
    Optional<User> currentUser = this.userRepository.findById(email);
    if (!currentUser.isPresent()) {
      throw new IllegalArgumentException("User not found");
    }
    if (user.getEmail() != email) {
      throw new IllegalArgumentException("Email mismatch");
    }
    if (!user.isValid()) {
      throw new IllegalArgumentException("User is invalid");
    }
    this.userRepository.save(user);
  }
}
