package backend.grupo130.tramos.repository;

import backend.grupo130.tramos.entity.Ruta;
import backend.grupo130.tramos.entity.Tramo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TramoRepository extends JpaRepository<Tramo, UUID> {
    List<Tramo> findByRutaOrderByOrden(Ruta ruta);
}
