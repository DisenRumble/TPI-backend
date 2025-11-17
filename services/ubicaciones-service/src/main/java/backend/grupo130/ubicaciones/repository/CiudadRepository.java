package backend.grupo130.ubicaciones.repository;

import backend.grupo130.ubicaciones.entity.Ciudad;
import backend.grupo130.ubicaciones.entity.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CiudadRepository extends JpaRepository<Ciudad, Long> {
    List<Ciudad> findByProvincia(Provincia provincia);
}
