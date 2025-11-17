package backend.grupo130.solicitudes.mapper;

import backend.grupo130.solicitudes.dto.ClienteRequest;
import backend.grupo130.solicitudes.dto.ClienteResponse;
import backend.grupo130.solicitudes.entity.Cliente;

public final class ClienteMapper {

    private ClienteMapper() {
    }

    public static ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
            cliente.getId(),
            cliente.getDni(),
            cliente.getNombre(),
            cliente.getApellido(),
            cliente.getTelefono(),
            cliente.getEmail(),
            cliente.getUsuarioId()
        );
    }

    public static void updateEntity(Cliente cliente, ClienteRequest request) {
        cliente.setNombre(request.nombre());
        cliente.setApellido(request.apellido());
        cliente.setTelefono(request.telefono());
        cliente.setEmail(request.email());
        cliente.setUsuarioId(request.usuarioId());
    }
}
