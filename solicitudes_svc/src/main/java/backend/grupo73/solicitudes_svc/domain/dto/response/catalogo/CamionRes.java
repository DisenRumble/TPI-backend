package backend.grupo73.solicitudes_svc.domain.dto.response.catalogo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CamionRes {
    private UUID id;
    private String patente;
    private String descripcion;
    private BigDecimal capacidadMaximaPesoKg;
    private BigDecimal capacidadMaximaVolumenM3;
    private String estado; // ACTIVO, EN_MANTENIMIENTO, etc.
    private Boolean activo;
}
