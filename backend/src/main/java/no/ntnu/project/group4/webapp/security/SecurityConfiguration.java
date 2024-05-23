package no.ntnu.project.group4.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * The SecurityConfiguration class represents the security configuration for the API.
 *
 * <p>Creates AuthenticationManager - set up authentication type.</p>
 *
 * <p>The @EnableMethodSecurity is needed so that each endpoint can specify which role it
 * requires.</p>
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {
  /**
   * A service providing our users from the database.
   */
  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private JwtRequestFilter jwtRequestFilter;

  /**
   * This method will be called automatically by the framework to find the authentication to use.
   * Here we tell that we want to load users from a database
   *
   * @param auth Authentication builder
   * @throws Exception When user service is not found
   */
  @Autowired
  protected void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }

  /**
   * This method will be called automatically by the framework to find the authentication to use.
   * 
   * <p>HttpMethod is used when authorizing HTTP requests to distinguish between different types of
   * requests. It is only used where same endpoints have different HTTP request types.</p>
   *
   * @param http HttpSecurity setting builder
   * @throws Exception When security configuration fails
   */
  @Bean
  public SecurityFilterChain configureAuthorizationFilterChain(HttpSecurity http) throws Exception {
    http
        // Disable CSRF and CORS checks. Without this it will be hard to make automated tests
        .csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        // The following is accessible for everyone
        .authorizeHttpRequests(
          (auth) -> auth.requestMatchers("/api/cars/**").permitAll()
        )
        .authorizeHttpRequests(
          (auth) -> auth.requestMatchers("/api/configurations/**").permitAll()
        )
        .authorizeHttpRequests(
          (auth) -> auth.requestMatchers("/api/extrafeatures/**").permitAll()
        )
        .authorizeHttpRequests(
          (auth) -> auth.requestMatchers("/api/providers/**").permitAll()
        )
        .authorizeHttpRequests(
          (auth) -> auth.requestMatchers("/api/rentals/**").permitAll()
        )
        .authorizeHttpRequests(
          (auth) -> auth.requestMatchers("/api/receipts/**").permitAll()
        )
        .authorizeHttpRequests(
          (auth) -> auth.requestMatchers("/api/users/**").permitAll()
        )
        // Authentication and registering is accessible for everyone
        .authorizeHttpRequests(
          (auth) -> auth.requestMatchers("/api/authenticate").permitAll()
        )
        .authorizeHttpRequests(
          (auth) -> auth.requestMatchers("/api/register").permitAll()
        )
        // API documentation is accessible
        .authorizeHttpRequests(
          (auth) -> auth.requestMatchers("/swagger-ui.html").permitAll()
        )
        .authorizeHttpRequests(
          (auth) -> auth.requestMatchers("/swagger-ui/**").permitAll()
        )
        .authorizeHttpRequests(
          (auth) -> auth.requestMatchers("/v3/api-docs/**").permitAll()
        )
        .authorizeHttpRequests(
          (auth) -> auth.requestMatchers("/api-docs/**").permitAll()
        )
        // Allow HTTP OPTIONS requests - CORS pre-flight requests
        .authorizeHttpRequests(
          (auth) -> auth.requestMatchers(HttpMethod.OPTIONS).permitAll()
        )
        // Any other request will be authenticated with a stateless policy
        .authorizeHttpRequests(
          (auth) -> auth.anyRequest().authenticated()
        )
        // Enable stateless session policy
        .sessionManagement((session) ->
          session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // Enable our JWT authentication filter
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  /**
   * This is needed since Spring Boot 2.0, see
   * https://stackoverflow.com/questions/52243774/consider-defining-a-bean-of-type-org-springframework-security-authentication-au
   * for more information.
   *
   * @return Authentication manager
   * @throws Exception On authentication config error
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }

  /**
   * This method is called to decide what encryption to use for password checking.
   *
   * @return The password encryptor
   */
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
