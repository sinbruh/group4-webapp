package no.ntnu.group4.webapp.service;

import java.util.Optional;

import no.ntnu.group4.webapp.model.User;
import no.ntnu.group4.webapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
   * Try to find a user with the given ID regardless of if it exists or not
   * 
   * @param id The given ID
   * @return A user with the given ID regardless of if it exists or not
   */
  public Optional<User> getOne(int id) {
    return this.userRepository.findById(id);
  }

  /**
   * Add the given user to the database.
   * 
   * @param user The given user
   * @return The ID of the given user
   * @throws IllegalArgumentException If the given user is invalid
   */
  public int add(User user) {
    if (!user.isValid()) {
      throw new IllegalArgumentException("User is invalid");
    }
    this.userRepository.save(user);
    return user.getId();
  }

  /**
   * Try to delete a user with the given ID.
   * 
   * @param id The given ID
   * @return True if the user was found and thus deleted or false otherwise
   */
  public boolean delete(int id) {
    Optional<User> user = this.userRepository.findById(id);
    if (user.isPresent()) {
      this.userRepository.deleteById(id);
    }
    return user.isPresent();
  }

  /**
   * Try to update a user with the given ID.
   * 
   * @param id The given ID
   * @param user The updated user metadata
   * @throws IllegalArgumentException If the current user is not found or the updated user metadata
   *                                  has an ID mismatch or is invalid
   */
  public void update(int id, User user) {
    Optional<User> currentUser = this.userRepository.findById(id);
    if (!currentUser.isPresent()) {
      throw new IllegalArgumentException("User not found");
    }
    if (user.getId() != id) {
      throw new IllegalArgumentException("ID mismatch");
    }
    if (!user.isValid()) {
      throw new IllegalArgumentException("User is invalid");
    }
    this.userRepository.save(user);
  }
}
