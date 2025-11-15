package backend.grupo73.usuarios_svc.domain.repository;

import backend.grupo73.usuarios_svc.domain.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ClienteRepositoryI extends JpaRepository<ClienteModel, UUID> {
    Optional<ClienteModel> findByKeycloakSub(String sub);

    Optional<ClienteModel> findByEmail(String email);
}
