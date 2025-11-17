package backend.grupo130.solicitudes.mapper;

import backend.grupo130.solicitudes.dto.TarifaRequest;
import backend.grupo130.solicitudes.dto.TarifaResponse;
import backend.grupo130.solicitudes.entity.Tarifa;

public final class TarifaMapper {

    private TarifaMapper() {
    }

    public static TarifaResponse toResponse(Tarifa tarifa) {
        return new TarifaResponse(
            tarifa.getId(),
            tarifa.getCargoGestionPorTramo(),
            tarifa.getValorPorKilometro(),
            tarifa.getValorPorVolumen(),
            tarifa.getValorCombustibleLitro(),
            Boolean.TRUE.equals(tarifa.getVigente())
        );
    }

    public static void updateEntity(Tarifa tarifa, TarifaRequest request) {
        tarifa.setCargoGestionPorTramo(request.cargoGestionPorTramo());
        tarifa.setValorPorKilometro(request.valorPorKilometro());
        tarifa.setValorPorVolumen(request.valorPorVolumen());
        tarifa.setValorCombustibleLitro(request.valorCombustibleLitro());
        tarifa.setVigente(true);
    }
}
