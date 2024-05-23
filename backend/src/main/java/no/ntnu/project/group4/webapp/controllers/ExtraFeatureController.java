package no.ntnu.project.group4.webapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Optional;
import no.ntnu.project.group4.webapp.models.Configuration;
import no.ntnu.project.group4.webapp.models.ExtraFeature;
import no.ntnu.project.group4.webapp.models.User;
import no.ntnu.project.group4.webapp.services.AccessUserService;
import no.ntnu.project.group4.webapp.services.ConfigurationService;
import no.ntnu.project.group4.webapp.services.ExtraFeatureService;
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
 * The ExtraFeatureController class represents the REST API controller class for extra features.
 *
 * <p>All HTTP requests affiliated with extra features are handeld in this class.</p>
 *
 * @author Group 4
 * @version v1.6 (2024.05.22)
 */
@CrossOrigin
@RestController
@RequestMapping("/api/extrafeatures")
public class ExtraFeatureController {
  @Autowired
  private ExtraFeatureService extraFeatureService;
  @Autowired
  private ConfigurationService configurationService;
  @Autowired
  private AccessUserService userService;

  private final Logger logger = LoggerFactory.getLogger(ExtraFeatureController.class);

  /**
   * Returns an iterable containing all extra features. When this endpoint is requested, a HTTP 200
   * OK response will automatically be sent back.
   *
   * @return 200 OK + all extra feature data
   */
  @Operation(
      summary = "Get all extra features",
      description = "Gets all extra features"
  )
  @ApiResponse(
      responseCode = "200",
      description = "All extra feature data"
  )
  @GetMapping
  public Iterable<ExtraFeature> getAll() {
    logger.info("Sending all extra feature data...");
    return this.extraFeatureService.getAll();
  }

  /**
   * Returns a HTTP response to the request requesting to get the extra feature with the specified
   * ID.
   *
   * <p>The response body contains extra feature data on success or a string that with an error
   * message.</p>
   *
   * @param id The specified ID
   * @return <p>200 OK on success + extra feature data</p>
   *         <p>404 NOT FOUND if extra feature is not found</p>
   */
  @Operation(
      summary = "Get extra feature by ID",
      description = "Gets the extra feature with the specified ID"
  )
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200",
        description = "Extra feature data"
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Extra feature with specified ID not found"
      )
  })
  @GetMapping("/{id}")
  public ResponseEntity<?> get(
      @Parameter(description = "The ID of the extra feature to get")
      @PathVariable Long id
  ) {
    ResponseEntity<?> response;
    Optional<ExtraFeature> extraFeature = this.extraFeatureService.getOne(id);
    if (extraFeature.isPresent()) {
      logger.info("Extra feature found, sending extra feature data...");
      response = new ResponseEntity<>(extraFeature.get(), HttpStatus.OK);
    } else {
      logger.error("Extra feature not found, sending error message...");
      response = new ResponseEntity<>("Extra feature with specified ID not found",
                                      HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request requesting to add the specified extra feature to the
   * configuration with the specified configuration ID.
   *
   * <p>The response body contains the generated ID of the specified extra feature on success or
   * contains or a string with an error message on error.</p>
   *
   * @param configId     The specified configuration ID
   * @param extraFeature The specified extra feature
   * @return <p>201 CREATED on success + ID</p>
   *         <p>400 BAD REQUEST on error</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user is not admin</p>
   *         <p>404 NOT FOUND if configuration is not found</p>
   */
  @Operation(
      summary = "Add extra feature",
      description = "Adds the specified extra feature to the configuration with the specified "
                  + "configuration ID"
  )
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "201",
        description = "Extra feature added + ID of extra feature"
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Error adding extra feature + error message"
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Only authenticated users have access to add extra features"
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Only admin users have access to add extra features"
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Configuration with specified configuration ID not found"
      )
  })
  @PostMapping("/{configId}")
  public ResponseEntity<?> add(
      @Parameter(description = "The ID of the configuration to add to")
      @PathVariable Long configId,
      @Parameter(description = "The extra featore to add")
      @RequestBody ExtraFeature extraFeature
  ) {
    ResponseEntity<?> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      Optional<Configuration> configuration = this.configurationService.getOne(configId);
      if (configuration.isPresent()) {
        extraFeature.setConfiguration(configuration.get());
        try {
          this.extraFeatureService.add(extraFeature);
          logger.info("Configuration found and valid extra feature data, sending generated ID "
                    + "of new extra feature...");
          response = new ResponseEntity<>(extraFeature.getId(), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
          logger.error("Invalid extra feature data, sending error message...");
          response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
      } else {
        logger.error("Configuration not found, sending error message...");
        response = new ResponseEntity<>("Configuration with specified ID not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else if (sessionUser == null) {
      logger.error("User not authenticated, sending error message...");
      response = new ResponseEntity<>("Only authenticated users have access to add extra features",
                                      HttpStatus.UNAUTHORIZED);
    } else {
      logger.error("User not admin, sending error message...");
      response = new ResponseEntity<>("Only admin users have access to add extra features",
                                      HttpStatus.FORBIDDEN);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request requesting to update the extra feature with the
   * specified ID with the specified extra feature.
   *
   * <p>The response body contains an empty string on success or a string with an error message on
   * error.</p>
   *
   * <p><b>NB!</b> This method does not allow updating which configuration the extra feature
   * belongs to.</p>
   *
   * @param id           The specified ID
   * @param extraFeature The specified extra feature
   * @return <p>200 OK success</p>
   *         <p>400 BAD REQUEST on error</p>
   *         <p>401 UNAUTHORIZED if user is not authorized</p>
   *         <p>403 FORBIDDEN if user is not admin</p>
   *         <p>404 NOT FOUND is extra feature is not found</p>
   */
  @Operation(
      summary = "Update extra feature",
      description = "Updates the extra feature with the specified ID with the specified extra "
                  + "feature"
  )
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200",
        description = "Extra feature updated"
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Error updating extra feature + error message"
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Only authenticated users have access to add extra features"
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Only admin users have access to add extra features"
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Extra feature with specified ID not found"
      )
  })
  @PutMapping("/{id}")
  public ResponseEntity<String> update(
      @Parameter(description = "The ID of the extra feature to update")
      @PathVariable Long id,
      @Parameter(description = "The extra feature to update the existing extra feature with")
      @RequestBody ExtraFeature extraFeature
  ) {
    ResponseEntity<String> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      try {
        if (this.extraFeatureService.update(id, extraFeature)) {
          logger.info("Extra feature found and valid extra feature data, updating extra "
                    + "feature...");
          response = new ResponseEntity<>("", HttpStatus.OK);
        } else {
          logger.error("Extra feature not found, sending error message...");
          response = new ResponseEntity<>("Extra feature with specified ID not found",
                                          HttpStatus.NOT_FOUND);
        }
      } catch (IllegalArgumentException e) {
        logger.error("Invalid extra feature data, sending error message...");
        response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
      }
    } else if (sessionUser == null) {
      logger.error("User not authenticated, sending error message...");
      response = new ResponseEntity<>("Only authenticated users have access to update extra "
                                    + "features", HttpStatus.UNAUTHORIZED);
    } else {
      logger.error("User not admin, sending error message...");
      response = new ResponseEntity<>("Only admin users have access to update extra features",
                                      HttpStatus.FORBIDDEN);
    }
    return response;
  }

  /**
   * Returns a HTTP response to the request requesting to delete the extra feature with the
   * specified ID.
   *
   * <p>The response body contains an empty string on success or a string with an error message on
   * error.</p>
   *
   * @param id The specified ID
   * @return <p>200 OK on success</p>
   *         <p>401 UNAUTHORIZED if user is not authenticated</p>
   *         <p>403 FORBIDDEN if user is not admin</p>
   *         <p>404 NOT FOUND if extra feature is not found</p>
   */
  @Operation(
      summary = "Delete extra feature",
      description = "Deletes the extra feature with the specified ID"
  )
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200",
        description = "Extra feature deleted"
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Only authenticated users have access to delete extra features"
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Only admin users have access to delete extra features"
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Extra feature with specified ID not found"
      )
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(
      @Parameter(description = "The ID of the extra feature to delete")
      @PathVariable Long id
  ) {
    ResponseEntity<String> response;
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      if (this.extraFeatureService.delete(id)) {
        logger.info("Extra feature found, deleting extra feature...");
        response = new ResponseEntity<>("", HttpStatus.OK);
      } else {
        logger.error("Extra feature not found, sending error message...");
        response = new ResponseEntity<>("Extra feature with specified ID not found",
                                        HttpStatus.NOT_FOUND);
      }
    } else if (sessionUser == null) {
      logger.error("User not authenticated, sending error message...");
      response = new ResponseEntity<>("Only authenticated users have access to delete extra "
                                    + "features", HttpStatus.UNAUTHORIZED);
    } else {
      logger.error("User not admin, sending error message...");
      response = new ResponseEntity<>("Only admin users have access to delete extra features",
                                      HttpStatus.FORBIDDEN);
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
    logger.error("Received extra feature data could not be read, sending error message...");
    return new ResponseEntity<>("Extra feature data not supplied or contains a parameter on an "
                              + "invalid format", HttpStatus.BAD_REQUEST);
  }
}
