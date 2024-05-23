package no.ntnu.project.group4.webapp.security;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import no.ntnu.project.group4.webapp.models.Role;
import no.ntnu.project.group4.webapp.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * The AccessUserDetails class represents the class that contains the authentication information.
 * The class is needed by the AccessUserService class.
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
public class AccessUserDetails implements UserDetails {
  // The email of the user
  private final String username;
  private final String password;
  private final boolean isActive;
  private final List<GrantedAuthority> authorities = new LinkedList<>();

  /**
   * Constructs an instance of the AccessUserDetails class.
   *
   * @param user The specified user to copy data from
   */
  public AccessUserDetails(User user) {
    this.username = user.getEmail();
    this.password = user.getPassword();
    this.isActive = user.isActive();
    this.convertRoles(user.getRoles());
  }

  /**
   * Converts the specified roles the user has into instances of the SimpleGrantedAuthority class.
   *
   * @param roles The specified roles the user has
   */
  private void convertRoles(Set<Role> roles) {
    this.authorities.clear();
    for (Role role : roles) {
      this.authorities.add(new SimpleGrantedAuthority(role.getName()));
    }
  }

  /**
   * Getter for username.
   *
   * @return Username
   */
  @Override
  public String getUsername() {
    return this.username;
  }

  /**
   * Getter for password.
   *
   * @return Password
   */
  @Override
  public String getPassword() {
    return this.password;
  }

  /**
   * Getter for authorities.
   *
   * @return Authorities
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  /**
   * Checks if the user is not expired.
   *
   * @return True if the user is not expired or false otherwise
   */
  @Override
  public boolean isAccountNonExpired() {
    return this.isActive;
  }

  /**
   * Checks if the user is not locked.
   *
   * @return True if the user is not locked or false otherwise
   */
  @Override
  public boolean isAccountNonLocked() {
    return this.isActive;
  }

  /**
   * Checks if the user credentials is not expired.
   *
   * @return True if the user credentials is not expired or false otherwise
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return this.isActive;
  }

  /**
   * Checks if the user is enabled.
   *
   * @return True if the user is enabled or false otherwise
   */
  @Override
  public boolean isEnabled() {
    return true;
  }
}
