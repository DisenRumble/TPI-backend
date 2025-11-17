package backend.grupo130.contenedores.service;

import backend.grupo130.contenedores.dto.ActualizarContenedorRequest;
import backend.grupo130.contenedores.dto.CambioEstadoRequest;
import backend.grupo130.contenedores.dto.ContenedorResponse;
import backend.grupo130.contenedores.dto.CrearContenedorRequest;
import backend.grupo130.contenedores.entity.Contenedor;
import backend.grupo130.contenedores.entity.EstadoContenedor;
import backend.grupo130.contenedores.mapper.ContenedorMapper;
import backend.grupo130.contenedores.repository.ContenedorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContenedorService {

    private final ContenedorRepository contenedorRepository;

    @Transactional
    public ContenedorResponse registrar(CrearContenedorRequest request) {
        contenedorRepository.findByIdentificador(request.identificador())
            .ifPresent(existing -> {
                throw new IllegalArgumentException("Ya existe un contenedor con identificador " + request.identificador());
            });

        Contenedor contenedor = Contenedor.builder()
            .identificador(request.identificador())
            .pesoKg(request.pesoKg())
            .volumenM3(request.volumenM3())
            .clienteId(request.clienteId())
            .solicitudId(request.solicitudId())
            .depositoId(request.depositoId())
            .descripcion(request.descripcion())
            .estado(request.estado() != null ? request.estado() : EstadoContenedor.EN_ORIGEN)
            .build();

        return ContenedorMapper.toResponse(contenedorRepository.save(contenedor));
    }

    public List<ContenedorResponse> listar(EstadoContenedor estado, UUID depositoId, UUID clienteId) {
        List<Contenedor> contenedores;
        if (estado != null) {
            contenedores = contenedorRepository.findByEstado(estado);
        } else if (depositoId != null) {
            contenedores = contenedorRepository.findByDepositoId(depositoId);
        } else if (clienteId != null) {
            contenedores = contenedorRepository.findByClienteId(clienteId);
        } else {
            contenedores = contenedorRepository.findAll();
        }
        return contenedores.stream().map(ContenedorMapper::toResponse).toList();
    }

    public ContenedorResponse obtener(UUID id) {
        return ContenedorMapper.toResponse(buscarPorId(id));
    }

    @Transactional
    public ContenedorResponse actualizar(UUID id, ActualizarContenedorRequest request) {
        Contenedor contenedor = buscarPorId(id);
        if (request.pesoKg() != null) {
            contenedor.setPesoKg(request.pesoKg());
        }
        if (request.volumenM3() != null) {
            contenedor.setVolumenM3(request.volumenM3());
        }
        if (request.estado() != null) {
            contenedor.setEstado(request.estado());
        }
        if (request.clienteId() != null) {
            contenedor.setClienteId(request.clienteId());
        }
        if (request.solicitudId() != null) {
            contenedor.setSolicitudId(request.solicitudId());
        }
        if (request.depositoId() != null) {
            contenedor.setDepositoId(request.depositoId());
        }
        if (request.descripcion() != null) {
            contenedor.setDescripcion(request.descripcion());
        }
        return ContenedorMapper.toResponse(contenedorRepository.save(contenedor));
    }

    @Transactional
    public ContenedorResponse cambiarEstado(UUID id, CambioEstadoRequest request) {
        Contenedor contenedor = buscarPorId(id);
        contenedor.setEstado(request.estado());
        contenedor.setDepositoId(request.depositoId());
        contenedor.setSolicitudId(request.solicitudId());
        if (request.descripcion() != null) {
            contenedor.setDescripcion(request.descripcion());
        }
        return ContenedorMapper.toResponse(contenedorRepository.save(contenedor));
    }

    private Contenedor buscarPorId(UUID id) {
        return contenedorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Contenedor no encontrado: " + id));
    }
}
