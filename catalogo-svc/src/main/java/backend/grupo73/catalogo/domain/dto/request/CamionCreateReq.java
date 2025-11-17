package backend.grupo73.catalogo.domain.dto.request;

import backend.grupo73.catalogo.domain.entity.EstadoCamion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CamionCreateReq {
    private String patente;
    private String descripcion;
    private BigDecimal capacidadMaximaPesoKg;
    private BigDecimal capacidadMaximaVolumenM3;
    private EstadoCamion estado;
    private boolean activo;
}
