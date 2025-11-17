package backend.grupo130.camiones.service;

import backend.grupo130.camiones.dto.TransportistaRequest;
import backend.grupo130.camiones.dto.TransportistaResponse;
import backend.grupo130.camiones.entity.Transportista;
import backend.grupo130.camiones.mapper.TransportistaMapper;
import backend.grupo130.camiones.repository.TransportistaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransportistaService {

    private final TransportistaRepository transportistaRepository;

    public List<TransportistaResponse> listar() {
        return transportistaRepository.findAll().stream()
            .map(TransportistaMapper::toResponse)
            .toList();
    }

    public TransportistaResponse registrar(TransportistaRequest request) {
        Transportista transportista = new Transportista();
        TransportistaMapper.updateEntity(transportista, request);
        return TransportistaMapper.toResponse(transportistaRepository.save(transportista));
    }

    public Transportista obtenerEntidad(UUID id) {
        return transportistaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Transportista no encontrado: " + id));
    }
}
