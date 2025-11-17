package backend.grupo73.usuarios_svc.domain.dto.request;

import backend.grupo73.usuarios_svc.config.exceptions.ApiException;
import backend.grupo73.usuarios_svc.config.exceptions.ApiError;

public record ClienteCreateReq(
        String keycloakSub,
        String email,
        String nombre,
        String apellido,
        String telefono,
        String direccion
) {
    public ClienteCreateReq {
        if (isBlank(keycloakSub) || isBlank(email) || isBlank(nombre) || isBlank(apellido)) {
            throw new ApiException(ApiError.MISSING_REQUIRED_FIELDS,
                    "keycloakSub, email, nombre y apellido son obligatorios");
        }
        if (!email.matches("^[\\w.!#$%&’*+/=?`{|}~^-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new ApiException(ApiError.INVALID_EMAIL, "Email inválido");
        }

        keycloakSub = keycloakSub.trim();
        email = email.trim();
        nombre = nombre.trim();
        apellido = apellido.trim();
        telefono = trimOrNull(telefono);
        direccion = trimOrNull(direccion);
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static String trimOrNull(String s) {
        return s == null ? null : s.trim();
    }
}
