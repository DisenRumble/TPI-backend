package backend.grupo130.camiones.repository;

import backend.grupo130.camiones.entity.Transportista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransportistaRepository extends JpaRepository<Transportista, UUID> {
}
