package backend.grupo73.solicitudes_svc.domain.repository;

import backend.grupo73.solicitudes_svc.domain.model.EstadoSolicitud;
import backend.grupo73.solicitudes_svc.domain.model.SolicitudModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SolicitudRepository extends JpaRepository<SolicitudModel, UUID> {

    List<SolicitudModel> findByEstadoNot(EstadoSolicitud estado);

    Optional<SolicitudModel> findByContenedorIdAndEstado(UUID contenedorId, EstadoSolicitud estado);

    List<SolicitudModel> findByContenedorId(UUID contenedorId);

    Optional<SolicitudModel> findFirstByContenedorIdentificacionUnicaAndEstadoNot(String identificacionUnica, EstadoSolicitud estado);
}