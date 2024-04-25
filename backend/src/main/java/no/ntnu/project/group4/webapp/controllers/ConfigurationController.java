package no.ntnu.project.group4.webapp.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.ntnu.project.group4.webapp.models.Configuration;
import no.ntnu.project.group4.webapp.services.ConfigurationService;

@CrossOrigin
@RestController
@RequestMapping("/api/configurations")
public class ConfigurationController {
  @Autowired
  private ConfigurationService configurationService;

  @GetMapping("/get")
  public Iterable<Configuration> getAll() {
    return this.configurationService.getAll();
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<Configuration> getOne(@PathVariable Long id) {
    ResponseEntity<Configuration> response;
    Optional<Configuration> configuration = this.configurationService.getOne(id);
    if (configuration.isPresent()) {
      response = ResponseEntity.ok(configuration.get());
    } else {
      response = ResponseEntity.notFound().build();
    }
    return response;
  }

  @DeleteMapping("/del/{id}")
  public HttpStatus deleteOne(@PathVariable Long id) {
    HttpStatus response;
    Optional<Configuration> configuration = this.configurationService.getOne(id);
    if (configuration.isPresent()) {
      this.configurationService.delete(id);
      response = HttpStatus.ACCEPTED;
    } else {
      response = HttpStatus.NOT_FOUND;
    }
    return response;
  }
}
