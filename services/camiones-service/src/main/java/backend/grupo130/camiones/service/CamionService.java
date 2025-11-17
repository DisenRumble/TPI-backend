package backend.grupo130.camiones.service;

import backend.grupo130.camiones.dto.ActualizarCamionRequest;
import backend.grupo130.camiones.dto.CamionRequest;
import backend.grupo130.camiones.dto.CamionResponse;
import backend.grupo130.camiones.entity.Camion;
import backend.grupo130.camiones.entity.EstadoCamion;
import backend.grupo130.camiones.entity.Transportista;
import backend.grupo130.camiones.mapper.CamionMapper;
import backend.grupo130.camiones.repository.CamionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CamionService {

    private final CamionRepository camionRepository;
    private final TransportistaService transportistaService;

    @Transactional
    public CamionResponse registrar(CamionRequest request) {
        Transportista transportista = transportistaService.obtenerEntidad(request.transportistaId());
        Camion camion = Camion.builder()
            .patente(request.patente())
            .transportista(transportista)
            .capacidadVolumenM3(request.capacidadVolumenM3())
            .capacidadPesoKg(request.capacidadPesoKg())
            .consumoLitrosKm(request.consumoLitrosKm())
            .costoKm(request.costoKm())
            .anioFabricacion(request.anioFabricacion())
            .tipo(request.tipo())
            .depositoId(request.depositoId())
            .estado(EstadoCamion.DISPONIBLE)
            .build();
        return CamionMapper.toResponse(camionRepository.save(camion));
    }

    public List<CamionResponse> listar(EstadoCamion estado) {
        List<Camion> camiones = estado == null ? camionRepository.findAll() : camionRepository.findByEstado(estado);
        return camiones.stream().map(CamionMapper::toResponse).toList();
    }

    public CamionResponse obtener(String patente) {
        return CamionMapper.toResponse(buscarPorPatente(patente));
    }

    @Transactional
    public CamionResponse actualizar(String patente, ActualizarCamionRequest request) {
        Camion camion = buscarPorPatente(patente);
        if (request.transportistaId() != null) {
            Transportista transportista = transportistaService.obtenerEntidad(request.transportistaId());
            camion.setTransportista(transportista);
        }
        if (request.capacidadVolumenM3() != null) {
            camion.setCapacidadVolumenM3(request.capacidadVolumenM3());
        }
        if (request.capacidadPesoKg() != null) {
            camion.setCapacidadPesoKg(request.capacidadPesoKg());
        }
        if (request.consumoLitrosKm() != null) {
            camion.setConsumoLitrosKm(request.consumoLitrosKm());
        }
        if (request.costoKm() != null) {
            camion.setCostoKm(request.costoKm());
        }
        if (request.anioFabricacion() != null) {
            camion.setAnioFabricacion(request.anioFabricacion());
        }
        if (request.tipo() != null) {
            camion.setTipo(request.tipo());
        }
        if (request.depositoId() != null) {
            camion.setDepositoId(request.depositoId());
        }
        if (request.estado() != null) {
            camion.setEstado(request.estado());
        }
        return CamionMapper.toResponse(camionRepository.save(camion));
    }

    public List<CamionResponse> disponiblesParaCarga(BigDecimal peso, BigDecimal volumen) {
        List<Camion> camiones = camionRepository
            .findByEstadoAndCapacidadPesoKgGreaterThanEqualAndCapacidadVolumenM3GreaterThanEqual(
                EstadoCamion.DISPONIBLE,
                peso,
                volumen
            );
        return camiones.stream().map(CamionMapper::toResponse).toList();
    }

    private Camion buscarPorPatente(String patente) {
        return camionRepository.findById(patente)
            .orElseThrow(() -> new EntityNotFoundException("Camión no encontrado: " + patente));
    }
}
