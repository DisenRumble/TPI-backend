package backend.grupo130.ubicaciones.repository;

import backend.grupo130.ubicaciones.entity.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UbicacionRepository extends JpaRepository<Ubicacion, UUID> {
}
