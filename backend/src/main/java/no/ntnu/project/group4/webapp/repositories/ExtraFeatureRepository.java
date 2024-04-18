package no.ntnu.project.group4.webapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import no.ntnu.project.group4.webapp.models.ExtraFeature;

@Repository
public interface ExtraFeatureRepository extends CrudRepository<ExtraFeature, Long> {
}
