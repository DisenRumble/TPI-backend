package backend.grupo73.camiones.dto;

import lombok.Data;

@Data
public class CapacidadValidationRequest {
    private Long camionId;
    private double peso;
    private double volumen;
}