package backend.grupo73.usuarios_svc.domain.dto.response;

import backend.grupo73.usuarios_svc.domain.ClienteModel;
import java.util.UUID;

public record ClienteRes(
        UUID id,
        String email,
        String nombre,
        String apellido,
        String telefono,
        String direccion
) {
    public static ClienteRes from(ClienteModel c) {
        return new ClienteRes(
                c.getId(), c.getEmail(), c.getNombre(), c.getApellido(),
                c.getTelefono(), c.getDireccion()
        );
    }
}
