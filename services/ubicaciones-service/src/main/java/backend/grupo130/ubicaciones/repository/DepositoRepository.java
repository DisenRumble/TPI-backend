package backend.grupo130.ubicaciones.repository;

import backend.grupo130.ubicaciones.entity.Deposito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepositoRepository extends JpaRepository<Deposito, UUID> {
}
