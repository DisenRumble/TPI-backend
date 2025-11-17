package backend.grupo130.contenedores.repository;

import backend.grupo130.contenedores.entity.Contenedor;
import backend.grupo130.contenedores.entity.EstadoContenedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContenedorRepository extends JpaRepository<Contenedor, UUID> {
    Optional<Contenedor> findByIdentificador(String identificador);
    List<Contenedor> findByEstado(EstadoContenedor estado);
    List<Contenedor> findByDepositoId(UUID depositoId);
    List<Contenedor> findByClienteId(UUID clienteId);
}
