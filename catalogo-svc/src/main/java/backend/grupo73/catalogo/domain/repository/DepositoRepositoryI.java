package backend.grupo73.catalogo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositoRepositoryI extends JpaRepository<DepositoModel, String> {
}
