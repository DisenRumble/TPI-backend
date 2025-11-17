package backend.grupo73.catalogo.domain.repository;

import backend.grupo73.catalogo.domain.entity.Camion; // Import the new Camion entity
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID; // Import UUID

@Repository
public interface CamionRepositoryI extends JpaRepository<Camion, UUID> {
}
