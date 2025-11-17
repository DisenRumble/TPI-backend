package backend.grupo130.solicitudes.controller;

import backend.grupo130.solicitudes.dto.SeguimientoResponse;
import backend.grupo130.solicitudes.dto.SolicitudEstadoUpdateRequest;
import backend.grupo130.solicitudes.dto.SolicitudRequest;
import backend.grupo130.solicitudes.dto.SolicitudResponse;
import backend.grupo130.solicitudes.entity.EstadoSolicitud;
import backend.grupo130.solicitudes.service.SolicitudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/solicitudes")
@RequiredArgsConstructor
public class SolicitudController {

    private final SolicitudService solicitudService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SolicitudResponse crear(@Valid @RequestBody SolicitudRequest request) {
        return solicitudService.crearSolicitud(request);
    }

    @GetMapping
    public List<SolicitudResponse> listar(@RequestParam(value = "estado", required = false) EstadoSolicitud estado) {
        return solicitudService.listar(estado);
    }

    @GetMapping("/{id}")
    public SolicitudResponse obtener(@PathVariable UUID id) {
        return solicitudService.obtenerPorId(id);
    }

    @PutMapping("/{id}/estado")
    public SolicitudResponse actualizarEstado(@PathVariable UUID id,
                                              @Valid @RequestBody SolicitudEstadoUpdateRequest request) {
        return solicitudService.actualizarEstado(id, request);
    }

    @GetMapping("/seguimiento/{contenedorId}")
    public SeguimientoResponse seguimiento(@PathVariable UUID contenedorId) {
        return solicitudService.obtenerSeguimientoPorContenedor(contenedorId);
    }
}
