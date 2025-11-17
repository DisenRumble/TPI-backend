package backend.grupo73.catalogo.domain.dto.response;

import backend.grupo73.catalogo.domain.entity.EstadoCamion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CamionRes {
    private UUID id;
    private String patente;
    private String descripcion;
    private BigDecimal capacidadMaximaPesoKg;
    private BigDecimal capacidadMaximaVolumenM3;
    private EstadoCamion estado;
    private boolean activo;
}
