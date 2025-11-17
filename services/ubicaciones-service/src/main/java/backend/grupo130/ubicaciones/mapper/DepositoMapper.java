package backend.grupo130.ubicaciones.mapper;

import backend.grupo130.ubicaciones.dto.DepositoResponse;
import backend.grupo130.ubicaciones.dto.UbicacionResponse;
import backend.grupo130.ubicaciones.entity.Deposito;

public final class DepositoMapper {

    private DepositoMapper() {
    }

    public static DepositoResponse toResponse(Deposito deposito) {
        UbicacionResponse ubicacionResponse = UbicacionMapper.toResponse(deposito.getUbicacion());
        return new DepositoResponse(
            deposito.getId(),
            deposito.getNombre(),
            ubicacionResponse,
            deposito.getCapacidadMaxima(),
            deposito.getTelefono(),
            Boolean.TRUE.equals(deposito.getActivo())
        );
    }
}
