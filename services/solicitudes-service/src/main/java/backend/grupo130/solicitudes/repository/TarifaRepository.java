package backend.grupo130.solicitudes.repository;

import backend.grupo130.solicitudes.entity.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TarifaRepository extends JpaRepository<Tarifa, UUID> {
    List<Tarifa> findByVigenteTrue();
}
