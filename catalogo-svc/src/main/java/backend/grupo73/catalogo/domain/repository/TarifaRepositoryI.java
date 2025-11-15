package backend.grupo73.catalogo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TarifaRepositoryI extends JpaRepository<TarifaModel, Long> {
    Optional<TarifaModel> findByVigente(boolean vigente);
}
