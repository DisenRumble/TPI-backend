package backend.grupo73.solicitudes_svc.presentation.controller;

import backend.grupo73.solicitudes_svc.domain.dto.request.AsignarRutaReq;
import backend.grupo73.solicitudes_svc.domain.dto.request.SolicitudCreateReq;
import backend.grupo73.solicitudes_svc.domain.dto.response.SeguimientoContenedorRes;
import backend.grupo73.solicitudes_svc.domain.dto.response.SolicitudRes;
import backend.grupo73.solicitudes_svc.presentation.service.SolicitudService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/solicitudes")
@SecurityRequirement(name = "bearerAuth")
public class SolicitudController {

    private final SolicitudService solicitudService;

    // Registrar solicitud (cliente)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SolicitudRes crear(@Valid @RequestBody SolicitudCreateReq req) {
        return solicitudService.crearSolicitud(req);
    }

    // Seguimiento del contenedor (cliente)
    @GetMapping("/contenedores/{contenedorId}/seguimiento")
    public SeguimientoContenedorRes seguimiento(@PathVariable UUID contenedorId) {
        return solicitudService.obtenerSeguimientoPorContenedor(contenedorId);
    }

    // Solicitudes pendientes (operador)
    @GetMapping("/pendientes")
    public List<SolicitudRes> pendientes() {
        return solicitudService.obtenerSolicitudesPendientes();
    }

    // Asignar ruta (operador)
    @PatchMapping("/{solicitudId}/asignar-ruta")
    public SolicitudRes asignar(@PathVariable UUID solicitudId,
                                @Valid @RequestBody AsignarRutaReq req) {
        return solicitudService.asignarRuta(solicitudId, req);
    }

    // Finalizar (operador)
    @PatchMapping("/{solicitudId}/finalizar")
    public SolicitudRes finalizar(@PathVariable UUID solicitudId) {
        return solicitudService.finalizarSolicitud(solicitudId);
    }
}
