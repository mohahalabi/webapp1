package ch.supsi.webapp.web.repository;

import ch.supsi.webapp.web.model.Asta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AstaRepository extends JpaRepository<Asta, Integer> {

//    Optional<Asta> findByIdClosedTrue(int id);
//
//    Optional<Asta> findByIdClosedFalse(int id);
}
