package no.ntnu.project.group4.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import no.ntnu.project.group4.webapp.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
