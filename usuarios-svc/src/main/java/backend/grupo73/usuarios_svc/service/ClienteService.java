package backend.grupo73.usuarios_svc.service;

import backend.grupo73.usuarios_svc.domain.dto.response.ClienteRes;
import java.util.UUID;

public interface ClienteService {

    ClienteRes findOrCreate(String sub, String email, String nombre, String apellido);

    ClienteRes getById(UUID id);
}
