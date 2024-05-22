package no.ntnu.project.group4.webapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Optional;
import no.ntnu.project.group4.webapp.models.Configuration;
import no.ntnu.project.group4.webapp.models.Provider;
import no.ntnu.project.group4.webapp.models.User;
import no.ntnu.project.group4.webapp.services.AccessUserService;
import no.ntnu.project.group4.webapp.services.ConfigurationService;
import no.ntnu.project.group4.webapp.services.ProviderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * The ProviderController class represents the REST API controller class for providers.
 *
 * <p>All HTTP requests affiliated with providers are handled in this class.</p>
 *
 * @author Group 4
 * @version v1.7 (2024.05.22)
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

  private final Logger logger = LoggerFactory.getLogger(ProviderController.class);

  /**
   * Returns an iterable containing all providers. When this endpoint is requested, a HTTP 200 OK
   * response will automatically be sent back.
   *
   * @return 200 OK + provider data
   */
  @Operation(summary = "Get all providers")
  @GetMapping
  public Iterable<Provider> getAll() {
    logger.info("Sending all provider data...");
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
   * <p>404 NOT FOUND if provider is not found</p>
   */
  @Operation(
      summary = "Get provider by ID",
      description = "Returns the provider with the specified ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Provider data"),
      @ApiResponse(responseCode = "404", description = "Provider with specified ID not found")
  })
  @GetMapping("/{id}")
  public ResponseEntity<?> get(@PathVariable Long id) {
    ResponseEntity<?> response;
    Optional<Provider> provider = this.providerService.getOne(id);
    if (provider.isPresent()) {
      logger.info("Provider found, sending provider data...");
      response = new ResponseEntity<>(provider.get(), HttpStatus.OK);
    } else {
      logger.error("Provider not found, sending error message...");
      response = new ResponseEntity<>("Provider with specified ID not found",
          HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request requesting to add the specified provider to the
   * configuration with the specified configuration ID.
   *
   * <p>The response body contains the generated ID of the specified provider on success or a
   * string with an error message on error.</p>
   *
   * @param configId The specified configuration ID
   * @param provider The specified provider
   * @return <p>201 CREATED on success + ID</p>
   *         <p>400 BAD REQUEST on error</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user is not admin</p>
   *         <p>404 NOT FOUND if configuration is not found</p>
   */
  @Operation(
      summary = "Add provider",
      description = "Adds the specified provider to the configuration with the specified configuration ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "ID of provider"),
      @ApiResponse(responseCode = "400", description = "Error message"),
      @ApiResponse(responseCode = "401", description = "Only authenticated users have access to add providers"),
      @ApiResponse(responseCode = "403", description = "Only admin users have access to add providers"),
      @ApiResponse(responseCode = "404", description = "Configuration with specified configuration ID not found")
  })
  @PostMapping("/{configId}")
  public ResponseEntity<?> add(@PathVariable Long configId,
                                    @RequestBody Provider provider) {
    ResponseEntity<?> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      Optional<Configuration> configuration = this.configurationService.getOne(configId);
      if (configuration.isPresent()) {
        provider.setConfiguration(configuration.get());
        try {
          this.providerService.add(provider);
          logger.info("Configuration found and valid provider data, sending generated ID of new " +
                      "provider...");
          response = new ResponseEntity<>(provider.getId(), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
          logger.error("Invalid provider data, sending error message...");
          response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
      } else {
        logger.error("Configuration not found, sending error message...");
        response = new ResponseEntity<>("Configuration with specified ID not found",
            HttpStatus.NOT_FOUND);
      }
    } else if (sessionUser == null) {
      logger.error("User not authenticated, sending error message...");
      response = new ResponseEntity<>("Only authenticated users have access to add providers",
          HttpStatus.UNAUTHORIZED);
    } else {
      logger.error("User not admin, sending error message...");
      response = new ResponseEntity<>("Only admin users have access to add providers",
          HttpStatus.FORBIDDEN);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request requesting to update the provider with the specified ID
   * with the specified provider.
   * 
   * <p>The response body contains an empty string on success or a string with an error message on
   * error.</p>
   * 
   * <p><b>NB!</b> This method does not allow updating which configuration the provider belongs
   * to.</p>
   * 
   * @param id The specified ID
   * @param provider The specified provider
   * @return <p>200 OK on success</p>
   *         <p>400 BAD REQUEST on error</p>
   *         <p>401 UNAUTHORIZED if user is not authorized</p>
   *         <p>403 FORBIDDEN if user is not admin</p>
   *         <p>404 NOT FOUND if provider is not found</p>
   */
  @Operation(
    summary = "Update provider",
    description = "Updates the provider with the specified ID with the specified provider"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Provider was updated"
    ),
    @ApiResponse(
      responseCode = "400",
      description = "Error adding provider"
    ),
    @ApiResponse(
      responseCode = "401",
      description = "Only authenticated users have access to add providers"
    ),
    @ApiResponse(
      responseCode = "403",
      description = "Only admin users have access to add providers"
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Provider with specified ID not found"
    )
  })
  @PutMapping("/{id}")
  public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Provider provider) {
    ResponseEntity<String> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      try {
        if (this.providerService.update(id, provider)) {
          logger.info("Provider found and valid provider data, updating provider...");
          response = new ResponseEntity<>("", HttpStatus.OK);
        } else {
          logger.error("Provider not found, sending error message...");
          response = new ResponseEntity<>("Provider with specified ID not found",
                                          HttpStatus.NOT_FOUND);
        }
      } catch (IllegalArgumentException e) {
        logger.error("Invalid provider data, sending error message...");
        response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
      }
    } else if (sessionUser == null) {
      logger.error("User not authenticated, sending error message...");
      response = new ResponseEntity<>("Only authenticated users have access to update providers",
                                      HttpStatus.UNAUTHORIZED);
    } else {
      logger.error("User not admin, sending error message...");
      response = new ResponseEntity<>("Only admin users have access to update users",
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
  @Operation(
      summary = "Delete provider",
      description = "Deletes the provider with the specified ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Provider deleted"),
      @ApiResponse(responseCode = "401", description = "Only authenticated users have access to delete providers"),
      @ApiResponse(responseCode = "403", description = "Only admin users have access to delete providers"),
      @ApiResponse(responseCode = "404", description = "Provider with specified ID not found")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id) {
    ResponseEntity<String> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      if (this.providerService.delete(id)) {
        logger.info("Provider found, deleting provider...");
        response = new ResponseEntity<>("", HttpStatus.OK);
      } else {
        logger.error("Provider not found, sending error message...");
        response = new ResponseEntity<>("Provider with specified ID not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else if (sessionUser == null) {
      logger.error("User not authenticated, sending error message...");
      response = new ResponseEntity<>("Only authenticated users have access to delete providers",
                                      HttpStatus.UNAUTHORIZED);
    } else {
      logger.error("User not admin, sending error message...");
      response = new ResponseEntity<>("Only admin users have access to delete providers",
                                      HttpStatus.FORBIDDEN);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request requesting to toggle availability for the provider with
   * the specified ID.
   * 
   * <p>The response body contains an empty string on success or a string with an error message on
   * error.</p>
   * 
   * @param id The specified ID
   * @return <p>200 OK on success</p>
   *         <p>401 UNAUTHORIZED if user is not authorized</p>
   *         <p>403 FORBIDDEN if user is not admin</p>
   *         <p>404 NOT FOUND if provider with specified ID is not found</p>
   *         <p>500 INTERNAL SERVER ERROR if an error occurs when updating provider</p>
   */
  @Operation(
    summary = "Toggle availability for provider",
    description = "Toggles availability for the provider with the specified ID"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Availability toggled for provider"
    ),
    @ApiResponse(
      responseCode = "401",
      description = "Only authenticated users have access to toggle availability for providers"
    ),
    @ApiResponse(
      responseCode = "403",
      description = "Only admin users have access to toggle availability for providers"
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Provider with specified ID not found"
    ),
    @ApiResponse(
      responseCode = "500",
      description = "Could not toggle availability for provider with specified ID"
    )
  })
  @PutMapping("/{id}/availability")
  public ResponseEntity<String> toggleAvailability(@PathVariable Long id) {
    ResponseEntity<String> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      Optional<Provider> provider = this.providerService.getOne(id);
      if (provider.isPresent()) {
        Provider existingProvider = provider.get();
        existingProvider.toggleAvailable();
        try {
          this.providerService.update(id, existingProvider);
          logger.info("Provider found, toggling availability for provider...");
          response = new ResponseEntity<>("", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
          logger.error("Could not toggle availability for provider (error updating provider), " +
                       "sending error message...");
          response = new ResponseEntity<>("Could not toggle availability for provider with " +
                                          "specified ID", HttpStatus.INTERNAL_SERVER_ERROR);
        }
      } else {
        logger.error("Provider not found, sending error message...");
        response = new ResponseEntity<>("Provider with specified ID not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else if (sessionUser == null) {
      logger.error("User not authenticated, sending error message...");
      response = new ResponseEntity<>("Only authenticated users have access to toggle " +
                                      "availability for providers", HttpStatus.UNAUTHORIZED);
    } else {
      logger.error("User not admin, sending error message...");
      response = new ResponseEntity<>("Only admin users have access to toggle availability for " +
                                      "providers", HttpStatus.FORBIDDEN);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request requesting to toggle visibility for the provider with
   * the specified ID.
   * 
   * <p>The response body contains an empty string on success or a string with an error message on
   * error.</p>
   * 
   * @param id The specified ID
   * @return <p>200 OK on success</p>
   *         <p>401 UNAUTHORIZED if user is not authorized</p>
   *         <p>403 FORBIDDEN if user is not admin</p>
   *         <p>404 NOT FOUND if provider with specified ID is not found</p>
   *         <p>500 INTERNAL SERVER ERROR if an error occurs when updating provider</p>
   */
  @Operation(
    summary = "Toggle visibility for provider",
    description = "Toggles visibility for the provider with the specified ID"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Visibility toggled for provider"
    ),
    @ApiResponse(
      responseCode = "401",
      description = "Only authenticated users have access to toggle visibility for providers"
    ),
    @ApiResponse(
      responseCode = "403",
      description = "Only admin users have access to toggle visibility for providers"
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Provider with specified ID not found"
    ),
    @ApiResponse(
      responseCode = "500",
      description = "Could not toggle visibility for provider with specified ID"
    )
  })
  @PutMapping("/{id}/visibility")
  public ResponseEntity<String> toggleVisibility(@PathVariable Long id) {
    ResponseEntity<String> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      Optional<Provider> provider = this.providerService.getOne(id);
      if (provider.isPresent()) {
        Provider existingProvider = provider.get();
        existingProvider.toggleVisible();
        try {
          this.providerService.update(id, existingProvider);
          logger.info("Provider found, updating visibility for provider...");
          response = new ResponseEntity<>("", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
          logger.error("Could not toggle visibility for provider (error updating provider), " +
                       "sending error message...");
          response = new ResponseEntity<>("Could not toggle visibility for provider with " +
                                          "specified ID", HttpStatus.INTERNAL_SERVER_ERROR);
        }
      } else {
        logger.error("Provider not found, sending error message...");
        response = new ResponseEntity<>("Provider with specified ID not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else if (sessionUser == null) {
      logger.error("User not authenticated, sending error message...");
      response = new ResponseEntity<>("Only authenticated users have access to toggle " +
                                      "visibility for providers", HttpStatus.UNAUTHORIZED);
    } else {
      logger.error("User not admin, sending error message...");
      response = new ResponseEntity<>("Only admin users have access to toggle visibility for " +
                                      "providers", HttpStatus.FORBIDDEN);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request causing the specified
   * MethodArgumentTypeMismatchException.
   * 
   * @param e The specified MethodArgumentTypeMismatchException
   * @return 400 BAD REQUEST with an error message
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<String> handlePathVarException(MethodArgumentTypeMismatchException e) {
    logger.error("Received HTTP request could not be read, sending error message...");
    return new ResponseEntity<>("HTTP request contains a value on an invalid format",
                                HttpStatus.BAD_REQUEST);
  }

  /**
   * Returns a HTTP response to the request causing the specified HttpMessageNotReadableException.
   * 
   * @param e The specified HttpMessageNotReadableException
   * @return 400 BAD REQUEST with an error message
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<String> handleRequestBodyException(HttpMessageNotReadableException e) {
    logger.error("Received provider data could not be read, sending error message...");
    return new ResponseEntity<>("Provider data not supplied or contains a parameter on an " +
                                "invalid format", HttpStatus.BAD_REQUEST);
  }
}
