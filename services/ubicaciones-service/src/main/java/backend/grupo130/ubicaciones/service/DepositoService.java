package backend.grupo130.ubicaciones.service;

import backend.grupo130.ubicaciones.dto.DepositoRequest;
import backend.grupo130.ubicaciones.dto.DepositoResponse;
import backend.grupo130.ubicaciones.entity.Deposito;
import backend.grupo130.ubicaciones.entity.Ubicacion;
import backend.grupo130.ubicaciones.mapper.DepositoMapper;
import backend.grupo130.ubicaciones.repository.DepositoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepositoService {

    private final DepositoRepository depositoRepository;
    private final UbicacionService ubicacionService;

    @Transactional
    public DepositoResponse registrar(DepositoRequest request) {
        Ubicacion ubicacion = ubicacionService.obtenerEntidad(request.ubicacionId());
        Deposito deposito = Deposito.builder()
            .nombre(request.nombre())
            .ubicacion(ubicacion)
            .capacidadMaxima(request.capacidadMaxima())
            .telefono(request.telefono())
            .activo(request.activo() != null ? request.activo() : Boolean.TRUE)
            .build();
        return DepositoMapper.toResponse(depositoRepository.save(deposito));
    }

    public List<DepositoResponse> listar() {
        return depositoRepository.findAll().stream()
            .map(DepositoMapper::toResponse)
            .toList();
    }

    public DepositoResponse actualizar(UUID id, DepositoRequest request) {
        Deposito deposito = obtenerEntidad(id);
        deposito.setNombre(request.nombre());
        if (request.ubicacionId() != null) {
            deposito.setUbicacion(ubicacionService.obtenerEntidad(request.ubicacionId()));
        }
        deposito.setCapacidadMaxima(request.capacidadMaxima());
        deposito.setTelefono(request.telefono());
        if (request.activo() != null) {
            deposito.setActivo(request.activo());
        }
        return DepositoMapper.toResponse(depositoRepository.save(deposito));
    }

    public Deposito obtenerEntidad(UUID id) {
        return depositoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Depósito no encontrado: " + id));
    }
}
