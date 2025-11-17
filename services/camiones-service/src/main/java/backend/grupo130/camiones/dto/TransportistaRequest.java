package backend.grupo130.camiones.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record TransportistaRequest(
    @NotBlank @Size(max = 80) String nombreCompleto,
    @Size(max = 30) String telefono,
    @Email String email,
    UUID usuarioId,
    @Size(max = 30) String licencia,
    BigDecimal salario
) { }
