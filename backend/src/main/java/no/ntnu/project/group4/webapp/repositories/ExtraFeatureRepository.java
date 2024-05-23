package no.ntnu.project.group4.webapp.repositories;

import no.ntnu.project.group4.webapp.models.ExtraFeature;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The ExtraFeatureRepository class represents the repository class for the extra feature entity.
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@Repository
public interface ExtraFeatureRepository extends CrudRepository<ExtraFeature, Long> {
}
