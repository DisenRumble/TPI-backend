package backend.grupo130.solicitudes.repository;

import backend.grupo130.solicitudes.entity.EstadoSolicitud;
import backend.grupo130.solicitudes.entity.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SolicitudRepository extends JpaRepository<Solicitud, UUID> {
    List<Solicitud> findByEstado(EstadoSolicitud estado);
    Optional<Solicitud> findByContenedorId(UUID contenedorId);
    List<Solicitud> findByClienteId(UUID clienteId);
}
