package backend.grupo130.camiones.mapper;

import backend.grupo130.camiones.dto.TransportistaRequest;
import backend.grupo130.camiones.dto.TransportistaResponse;
import backend.grupo130.camiones.entity.Transportista;

public final class TransportistaMapper {

    private TransportistaMapper() {
    }

    public static TransportistaResponse toResponse(Transportista transportista) {
        return new TransportistaResponse(
            transportista.getId(),
            transportista.getNombreCompleto(),
            transportista.getTelefono(),
            transportista.getEmail(),
            transportista.getUsuarioId(),
            transportista.getLicencia(),
            transportista.getSalario(),
            Boolean.TRUE.equals(transportista.getActivo())
        );
    }

    public static void updateEntity(Transportista transportista, TransportistaRequest request) {
        transportista.setNombreCompleto(request.nombreCompleto());
        transportista.setTelefono(request.telefono());
        transportista.setEmail(request.email());
        transportista.setUsuarioId(request.usuarioId());
        transportista.setLicencia(request.licencia());
        transportista.setSalario(request.salario());
        if (transportista.getActivo() == null) {
            transportista.setActivo(true);
        }
    }
}
