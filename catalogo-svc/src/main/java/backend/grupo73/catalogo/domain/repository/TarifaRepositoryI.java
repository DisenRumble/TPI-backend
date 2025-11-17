package backend.grupo73.catalogo.domain.repository;

import backend.grupo73.catalogo.domain.entity.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TarifaRepositoryI extends JpaRepository<Tarifa, UUID> {
    Optional<Tarifa> findByActivaTrue();
}
