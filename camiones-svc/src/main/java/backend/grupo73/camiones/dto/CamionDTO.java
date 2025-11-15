package backend.grupo73.camiones.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CamionDTO {
    private Long id;
    private String dominio;
    private String nombreTransportista;
    private String telefono;
    private double capPeso;
    private double capVolumen;
    private double costoKmBase;
    private double consumoLK_m;
    private boolean activo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}