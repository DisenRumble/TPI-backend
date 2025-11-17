package backend.grupo130.solicitudes.repository;

import backend.grupo130.solicitudes.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    Optional<Cliente> findByDni(String dni);
}
