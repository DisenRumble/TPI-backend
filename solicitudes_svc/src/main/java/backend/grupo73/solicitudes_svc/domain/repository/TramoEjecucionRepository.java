package backend.grupo73.solicitudes_svc.domain.repository;

import backend.grupo73.solicitudes_svc.domain.model.TramoEjecucionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TramoEjecucionRepository extends JpaRepository<TramoEjecucionModel, UUID> {
}
