package backend.grupo73.camiones.dto;

import lombok.Data;

@Data
public class CreateCamionDTO {
    private String dominio;
    private String nombreTransportista;
    private String telefono;
    private double capPeso;
    private double capVolumen;
    private double costoKmBase;
    private double consumoLK_m;
}