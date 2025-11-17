package backend.grupo130.tramos.repository;

import backend.grupo130.tramos.entity.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RutaRepository extends JpaRepository<Ruta, UUID> {
    Optional<Ruta> findBySolicitudId(UUID solicitudId);
}
