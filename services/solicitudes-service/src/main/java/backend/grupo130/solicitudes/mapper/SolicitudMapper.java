package backend.grupo130.solicitudes.mapper;

import backend.grupo130.solicitudes.dto.ClienteResponse;
import backend.grupo130.solicitudes.dto.SolicitudResponse;
import backend.grupo130.solicitudes.dto.TarifaResponse;
import backend.grupo130.solicitudes.entity.Solicitud;

public final class SolicitudMapper {

    private SolicitudMapper() {
    }

    public static SolicitudResponse toResponse(Solicitud solicitud) {
        ClienteResponse clienteResponse = ClienteMapper.toResponse(solicitud.getCliente());
        TarifaResponse tarifaResponse = TarifaMapper.toResponse(solicitud.getTarifa());
        return new SolicitudResponse(
            solicitud.getId(),
            solicitud.getContenedorId(),
            solicitud.getRutaId(),
            solicitud.getEstado(),
            solicitud.getCantidadTramos(),
            solicitud.getCantidadDepositos(),
            solicitud.getCostoEstimado(),
            solicitud.getCostoFinal(),
            solicitud.getTiempoEstimadoHoras(),
            solicitud.getTiempoRealHoras(),
            solicitud.getFechaCreacion(),
            solicitud.getFechaActualizacion(),
            clienteResponse,
            tarifaResponse
        );
    }
}
