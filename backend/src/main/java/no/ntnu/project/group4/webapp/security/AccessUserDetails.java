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
 * Contains authentication information, needed by UserDetailsService.
 */
public class AccessUserDetails implements UserDetails {
  // The email of the user
  private final String username;
  private final String password;
  private final boolean isActive;
  private final List<GrantedAuthority> authorities = new LinkedList<>();

  /**
   * Create access object.
   *
   * @param user The user to copy data from
   */
  public AccessUserDetails(User user) {
    this.username = user.getEmail();
    this.password = user.getPassword();
    this.isActive = user.isActive();
    this.convertRoles(user.getRoles());
  }

  private void convertRoles(Set<Role> roles) {
    this.authorities.clear();
    for (Role role : roles) {
      this.authorities.add(new SimpleGrantedAuthority(role.getName()));
    }
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
    return this.isActive;
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.isActive;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return this.isActive;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
