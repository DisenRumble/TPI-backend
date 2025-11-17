package backend.grupo130.solicitudes.service;

import backend.grupo130.solicitudes.dto.TarifaRequest;
import backend.grupo130.solicitudes.dto.TarifaResponse;
import backend.grupo130.solicitudes.entity.Tarifa;
import backend.grupo130.solicitudes.mapper.TarifaMapper;
import backend.grupo130.solicitudes.repository.TarifaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TarifaService {

    private final TarifaRepository tarifaRepository;

    public List<TarifaResponse> listar(boolean soloVigentes) {
        List<Tarifa> tarifas = soloVigentes ? tarifaRepository.findByVigenteTrue() : tarifaRepository.findAll();
        return tarifas.stream().map(TarifaMapper::toResponse).toList();
    }

    public TarifaResponse crear(TarifaRequest request) {
        Tarifa tarifa = Tarifa.builder().vigente(true).build();
        TarifaMapper.updateEntity(tarifa, request);
        return TarifaMapper.toResponse(tarifaRepository.save(tarifa));
    }

    public TarifaResponse actualizar(UUID id, TarifaRequest request) {
        Tarifa tarifa = obtenerEntidad(id);
        TarifaMapper.updateEntity(tarifa, request);
        return TarifaMapper.toResponse(tarifaRepository.save(tarifa));
    }

    public Tarifa obtenerEntidad(UUID id) {
        return tarifaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tarifa no encontrada: " + id));
    }
}
