package backend.grupo130.camiones.repository;

import backend.grupo130.camiones.entity.Camion;
import backend.grupo130.camiones.entity.EstadoCamion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface CamionRepository extends JpaRepository<Camion, String> {
    List<Camion> findByEstado(EstadoCamion estado);
    List<Camion> findByEstadoAndCapacidadPesoKgGreaterThanEqualAndCapacidadVolumenM3GreaterThanEqual(
        EstadoCamion estado,
        BigDecimal peso,
        BigDecimal volumen
    );
}
