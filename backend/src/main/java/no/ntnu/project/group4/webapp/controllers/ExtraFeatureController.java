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
import no.ntnu.project.group4.webapp.models.ExtraFeature;
import no.ntnu.project.group4.webapp.services.ConfigurationService;
import no.ntnu.project.group4.webapp.services.ExtraFeatureService;

@CrossOrigin
@RestController
@RequestMapping("/api/extrafeatures")
public class ExtraFeatureController {
  @Autowired
  private ExtraFeatureService extraFeatureService;
  @Autowired
  private ConfigurationService configurationService;

  @GetMapping("/get")
  public Iterable<ExtraFeature> getAll() {
    return this.extraFeatureService.getAll();
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<ExtraFeature> get(@PathVariable Long id) {
    ResponseEntity<ExtraFeature> response;
    Optional<ExtraFeature> extraFeature = this.extraFeatureService.getOne(id);
    if (extraFeature.isPresent()) {
      response = ResponseEntity.ok(extraFeature.get());
    } else {
      response = ResponseEntity.notFound().build();
    }
    return response;
  }

  /**
   * Adds the specified extra feature to the configuration with the specified ID in the database.
   * 
   * @param id The specified ID
   * @param extraFeature The specified extra feature
   * @return 201 CREATED on success or 400 BAD REQUEST or 404 NOT FOUND on error
   */
  @PostMapping("/add/configurations/{id}")
  public ResponseEntity<String> add(@PathVariable Long id,
                                    @RequestBody ExtraFeature extraFeature) {
    ResponseEntity<String> response;
    Optional<Configuration> configuration = this.configurationService.getOne(id);
    if (configuration.isPresent()) {
      extraFeature.setConfiguration(configuration.get());
      try {
        this.extraFeatureService.add(extraFeature);
        response = new ResponseEntity<>("", HttpStatus.CREATED);
      } catch (Exception e) {
        response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
      }
    } else {
      response = new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Deletes the extra feature with the specified ID from the database.
   * 
   * @param id The specified ID
   * @return 200 OK on success or 404 NOT FOUND on error
   */
  @DeleteMapping("/del/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id) {
    ResponseEntity<String> response;
    Optional<ExtraFeature> extraFeature = this.extraFeatureService.getOne(id);
    if (extraFeature.isPresent()) {
      this.extraFeatureService.delete(id);
      response = new ResponseEntity<>("", HttpStatus.OK);
    } else {
      response = new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }
    return response;
  }
}
