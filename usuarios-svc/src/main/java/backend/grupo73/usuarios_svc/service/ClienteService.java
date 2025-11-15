package backend.grupo73.usuarios_svc.service;

import backend.grupo73.usuarios_svc.domain.dto.response.ClienteRes;
import java.util.UUID;

public interface ClienteService {
    /**
     * Busca un cliente por su Keycloak SUB. Si no lo encuentra,
     * lo crea utilizando los datos proporcionados y lo guarda en la base de datos.
     *
     * @param sub      El ID de Keycloak (subject).
     * @param email    El email del usuario.
     * @param nombre   El nombre del usuario.
     * @param apellido El apellido del usuario.
     * @return El ClienteRes del cliente encontrado o recién creado.
     */
    ClienteRes findOrCreate(String sub, String email, String nombre, String apellido);

    /**
     * Busca un cliente por su ID (UUID).
     *
     * @param id El UUID del cliente a buscar.
     * @return El ClienteRes del cliente encontrado.
     * @throws backend.grupo73.usuarios_svc.config.exceptions.ApiException si no se encuentra el cliente.
     */
    ClienteRes getById(UUID id);
}
