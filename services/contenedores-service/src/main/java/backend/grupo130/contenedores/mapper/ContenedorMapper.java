package backend.grupo130.contenedores.mapper;

import backend.grupo130.contenedores.dto.ContenedorResponse;
import backend.grupo130.contenedores.entity.Contenedor;

public final class ContenedorMapper {

    private ContenedorMapper() {
    }

    public static ContenedorResponse toResponse(Contenedor contenedor) {
        return new ContenedorResponse(
            contenedor.getId(),
            contenedor.getIdentificador(),
            contenedor.getPesoKg(),
            contenedor.getVolumenM3(),
            contenedor.getEstado(),
            contenedor.getClienteId(),
            contenedor.getSolicitudId(),
            contenedor.getDepositoId(),
            contenedor.getDescripcion(),
            contenedor.getFechaAlta(),
            contenedor.getFechaActualizacion()
        );
    }
}
