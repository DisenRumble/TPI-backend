package backend.grupo73.solicitudes_svc.domain.repository;

import backend.grupo73.solicitudes_svc.domain.model.EstadiaDepositoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EstadiaDepositoRepository extends JpaRepository<EstadiaDepositoModel, UUID> {
}
