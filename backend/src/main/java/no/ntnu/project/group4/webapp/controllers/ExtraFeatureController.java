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

import no.ntnu.project.group4.webapp.models.ExtraFeature;
import no.ntnu.project.group4.webapp.services.ExtraFeatureService;

@CrossOrigin
@RestController
@RequestMapping("/api/extrafeatures")
public class ExtraFeatureController {
  @Autowired
  private ExtraFeatureService extraFeatureService;

  @GetMapping("/get")
  public Iterable<ExtraFeature> getAll() {
    return this.extraFeatureService.getAll();
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<ExtraFeature> getOne(@PathVariable Long id) {
    ResponseEntity<ExtraFeature> response;
    Optional<ExtraFeature> extraFeature = this.extraFeatureService.getOne(id);
    if (extraFeature.isPresent()) {
      response = ResponseEntity.ok(extraFeature.get());
    } else {
      response = ResponseEntity.notFound().build();
    }
    return response;
  }

  @DeleteMapping("/del/{id}")
  public HttpStatus deleteOne(@PathVariable Long id) {
    HttpStatus response;
    Optional<ExtraFeature> extraFeature = this.extraFeatureService.getOne(id);
    if (extraFeature.isPresent()) {
      this.extraFeatureService.delete(id);
      response = HttpStatus.ACCEPTED;
    } else {
      response = HttpStatus.NOT_FOUND;
    }
    return response;
  }
}
