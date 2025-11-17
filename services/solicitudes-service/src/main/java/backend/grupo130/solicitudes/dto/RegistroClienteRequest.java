package backend.grupo130.solicitudes.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistroClienteRequest(
    @NotBlank String dni,
    @NotBlank @Size(max = 60) String nombre,
    @NotBlank @Size(max = 60) String apellido,
    @Size(max = 30) String telefono,
    @NotBlank @Email String email,
    @NotBlank String username,
    @NotBlank @Size(min = 6) String password
) { }
