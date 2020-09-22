package ch.supsi.webapp.web.repository;

import ch.supsi.webapp.web.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Author, String> {
}
