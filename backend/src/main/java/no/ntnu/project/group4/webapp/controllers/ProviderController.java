package no.ntnu.project.group4.webapp.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.ntnu.project.group4.webapp.models.Configuration;
import no.ntnu.project.group4.webapp.models.Provider;
import no.ntnu.project.group4.webapp.models.User;
import no.ntnu.project.group4.webapp.services.AccessUserService;
import no.ntnu.project.group4.webapp.services.ConfigurationService;
import no.ntnu.project.group4.webapp.services.ProviderService;

/**
 * The ProviderController class represents the REST API controller class for providers.
 * 
 * <p>All HTTP requests affiliated with providers are handeled in this class.</p>
 * 
 * @author Group 4
 * @version v1.0 (2024.05.09)
 */
@CrossOrigin
@RestController
@RequestMapping("/api/providers")
public class ProviderController {
  @Autowired
  private ProviderService providerService;
  @Autowired
  private ConfigurationService configurationService;
  @Autowired
  private AccessUserService userService;

  /**
   * Returns an iterable containing all providers. When this endpoint is requested, a HTTP 200 OK
   * response will automatically be sent back.
   * 
   * @return 200 OK + provider data
   */
  @GetMapping
  public Iterable<Provider> getAll() {
    return this.providerService.getAll();
  }

  /**
   * Returns a response to the request of getting the provider with the specified ID.
   * 
   * <p>The response body contains (1) provider data or (2) a string that contains an error
   * message.</p>
   * 
   * @param id The specified ID
   * @return <p>200 OK on success</p>
   *         <p>404 NOT FOUND if provider is not found</p>
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> get(@PathVariable Long id) {
    ResponseEntity<?> response;
    Optional<Provider> provider = this.providerService.getOne(id);
    if (provider.isPresent()) {
      response = new ResponseEntity<>(provider.get(), HttpStatus.OK);
    } else {
      response = new ResponseEntity<>("Provider with specified ID not found",
                                      HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Returns a response to the request of adding the specified provider to the configuration with
   * the specified ID.
   * 
   * <p>The response body contains a string that is empty or contains an error message.</p>
   * 
   * @param id The specified ID
   * @param provider The specified provider
   * @return <p>201 CREATED on success</p>
   *         <p>400 BAD REQUEST on error</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user is not admin</p>
   *         <p>404 NOT FOUND if configuration is not found</p>
   */
  @PostMapping("/configurations/{id}")
  public ResponseEntity<String> add(@PathVariable Long id,
                                    @RequestBody Provider provider) {
    ResponseEntity<String> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      Optional<Configuration> configuration = this.configurationService.getOne(id);
      if (configuration.isPresent()) {
        provider.setConfiguration(configuration.get());
        try {
          this.providerService.add(provider);
          response = new ResponseEntity<>("", HttpStatus.CREATED);
        } catch (Exception e) {
          response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
      } else {
        response = new ResponseEntity<>("Configuration with specified ID not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else if (sessionUser == null) {
      response = new ResponseEntity<>("Only authenticated users have access to add providers",
                                      HttpStatus.UNAUTHORIZED);
    } else {
      response = new ResponseEntity<>("Only admin users have access to add providers",
                                      HttpStatus.FORBIDDEN);
    }
    return response;
  }

  /**
   * Returns a reponse to the request of deleting the provider with the specified ID.
   * 
   * <p>The response body contains a string that is empty or contains an error message.</p>
   * 
   * @param id The specified ID
   * @return <p>200 OK on success</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user is not admin</p>
   *         <p>404 NOT FOUND if provider is not found</p>
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id) {
    ResponseEntity<String> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      Optional<Provider> provider = this.providerService.getOne(id);
      if (provider.isPresent()) {
        this.providerService.delete(id);
        response = new ResponseEntity<>("", HttpStatus.OK);
      } else {
        response = new ResponseEntity<>("Provider with specified ID not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else if (sessionUser == null) {
      response = new ResponseEntity<>("Only authenticated users have access to delete providers",
                                      HttpStatus.UNAUTHORIZED);
    } else {
      response = new ResponseEntity<>("Only admin users have access to delete providers",
                                      HttpStatus.FORBIDDEN);
    }
    return response;
  }
}
