package backend.grupo73.catalogo.domain.repository;

import backend.grupo73.catalogo.domain.entity.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, UUID> {
    List<Ubicacion> findByNombreContainingIgnoreCase(String nombre);
}
