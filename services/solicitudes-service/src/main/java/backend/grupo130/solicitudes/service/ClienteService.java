package backend.grupo130.solicitudes.service;

import backend.grupo130.solicitudes.dto.ClienteRequest;
import backend.grupo130.solicitudes.dto.ClienteResponse;
import backend.grupo130.solicitudes.dto.RegistroClienteRequest;
import backend.grupo130.solicitudes.entity.Cliente;
import backend.grupo130.solicitudes.mapper.ClienteMapper;
import backend.grupo130.solicitudes.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final KeycloakAdminClient keycloakAdminClient;

    public List<ClienteResponse> listar() {
        return clienteRepository.findAll()
            .stream()
            .map(ClienteMapper::toResponse)
            .toList();
    }

    @Transactional
    public ClienteResponse registrar(RegistroClienteRequest request) {
        UUID usuarioId = keycloakAdminClient.registrarUsuarioCliente(request);
        ClienteRequest clienteRequest = new ClienteRequest(
            request.dni(),
            request.nombre(),
            request.apellido(),
            request.telefono(),
            request.email(),
            usuarioId
        );
        return crearOActualizar(clienteRequest);
    }

    public ClienteResponse crearOActualizar(ClienteRequest request) {
        Cliente cliente = clienteRepository.findByDni(request.dni())
            .map(existing -> actualizarEntidad(existing, request))
            .orElseGet(() -> nuevoCliente(request));
        return ClienteMapper.toResponse(clienteRepository.save(cliente));
    }

    public Cliente obtenerPorId(UUID id) {
        return clienteRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado: " + id));
    }

    @Transactional
    public Cliente resolverCliente(UUID clienteId, ClienteRequest datos) {
        if (clienteId != null) {
            return obtenerPorId(clienteId);
        }
        if (datos != null) {
            return clienteRepository.findByDni(datos.dni())
                .map(existing -> actualizarEntidad(existing, datos))
                .orElseGet(() -> clienteRepository.save(nuevoCliente(datos)));
        }
        throw new IllegalArgumentException("Debe indicar un cliente existente o los datos para registrarlo.");
    }

    private Cliente nuevoCliente(ClienteRequest request) {
        return Cliente.builder()
            .dni(request.dni())
            .nombre(request.nombre())
            .apellido(request.apellido())
            .telefono(request.telefono())
            .email(request.email())
            .usuarioId(request.usuarioId())
            .build();
    }

    private Cliente actualizarEntidad(Cliente cliente, ClienteRequest request) {
        ClienteMapper.updateEntity(cliente, request);
        return cliente;
    }
}
