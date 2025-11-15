package backend.grupo73.solicitudes_svc.domain.repository;

import backend.grupo73.solicitudes_svc.domain.model.ContenedorModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ContenedorRepository extends JpaRepository<ContenedorModel, UUID> {

    Optional<ContenedorModel> findByIdentificacionUnica(String identificacionUnica);
}
