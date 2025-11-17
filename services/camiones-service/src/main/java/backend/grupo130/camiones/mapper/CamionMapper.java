package backend.grupo130.camiones.mapper;

import backend.grupo130.camiones.dto.CamionResponse;
import backend.grupo130.camiones.entity.Camion;

public final class CamionMapper {

    private CamionMapper() {
    }

    public static CamionResponse toResponse(Camion camion) {
        return new CamionResponse(
            camion.getPatente(),
            camion.getTransportista() != null ? TransportistaMapper.toResponse(camion.getTransportista()) : null,
            camion.getCapacidadVolumenM3(),
            camion.getCapacidadPesoKg(),
            camion.getConsumoLitrosKm(),
            camion.getCostoKm(),
            camion.getAnioFabricacion(),
            camion.getTipo(),
            camion.getDepositoId(),
            camion.getEstado(),
            camion.getFechaAlta(),
            camion.getFechaActualizacion()
        );
    }
}
