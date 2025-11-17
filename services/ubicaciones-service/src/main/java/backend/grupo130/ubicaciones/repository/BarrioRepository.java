package backend.grupo130.ubicaciones.repository;

import backend.grupo130.ubicaciones.entity.Barrio;
import backend.grupo130.ubicaciones.entity.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BarrioRepository extends JpaRepository<Barrio, Long> {
    List<Barrio> findByCiudad(Ciudad ciudad);
}
