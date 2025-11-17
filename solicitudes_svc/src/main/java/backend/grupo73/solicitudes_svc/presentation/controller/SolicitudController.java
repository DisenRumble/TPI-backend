package backend.grupo73.solicitudes_svc.presentation.controller;

import backend.grupo73.solicitudes_svc.domain.dto.request.AsignarCamionReq;
import backend.grupo73.solicitudes_svc.domain.dto.request.RegistrarEstadiaReq;
import backend.grupo73.solicitudes_svc.domain.dto.request.RegistrarTramoRealReq;
import backend.grupo73.solicitudes_svc.domain.dto.request.SolicitudCreateReq;
import backend.grupo73.solicitudes_svc.domain.dto.response.SeguimientoContenedorRes;
import backend.grupo73.solicitudes_svc.domain.dto.response.SolicitudRes;
import backend.grupo73.solicitudes_svc.domain.dto.response.rutas.AlternativaRes;
import backend.grupo73.solicitudes_svc.presentation.service.SolicitudServiceI;
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

    private final SolicitudServiceI solicitudService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SolicitudRes crear(@Valid @RequestBody SolicitudCreateReq req) {
        return solicitudService.crearSolicitud(req);
    }

    @GetMapping("/contenedores/{contenedorId}/seguimiento")
    public SeguimientoContenedorRes seguimiento(@PathVariable UUID contenedorId) {
        return solicitudService.obtenerSeguimientoPorContenedor(contenedorId);
    }

    @GetMapping("/pendientes")
    public List<SolicitudRes> pendientes() {
        return solicitudService.obtenerSolicitudesPendientes();
    }

    @GetMapping("/{solicitudId}/rutas-disponibles")
    public List<AlternativaRes> getRutasDisponibles(@PathVariable UUID solicitudId) {
        return solicitudService.getRutasDisponibles(solicitudId).getAlternativas();
    }

    @PatchMapping("/{solicitudId}/tramos/{tramoId}/asignar-camion")
    public SolicitudRes asignarCamionATramo(@PathVariable UUID solicitudId, @PathVariable UUID tramoId, @Valid @RequestBody AsignarCamionReq req) {
        return solicitudService.asignarCamionATramo(solicitudId, tramoId, req.camionId());
    }

    @PatchMapping("/{solicitudId}/tramos/{tramoId}/finalizar")
    public SolicitudRes registrarTramoReal(@PathVariable UUID solicitudId, @PathVariable UUID tramoId, @Valid @RequestBody RegistrarTramoRealReq req) {
        return solicitudService.registrarTramoReal(solicitudId, tramoId, req.distanciaRealKm(), req.tiempoRealMin());
    }

    @PostMapping("/{solicitudId}/estadias")
    @ResponseStatus(HttpStatus.CREATED)
    public SolicitudRes registrarEstadiaDeposito(@PathVariable UUID solicitudId, @Valid @RequestBody RegistrarEstadiaReq req) {
        return solicitudService.registrarEstadiaDeposito(solicitudId, req.depositoUbicacionId(), req.fechaHoraEntrada(), req.fechaHoraSalida());
    }

    @PatchMapping("/{solicitudId}/calcular-costo-real")
    public SolicitudRes calcularCostoReal(@PathVariable UUID solicitudId) {
        return solicitudService.calcularCostoReal(solicitudId);
    }

    @PatchMapping("/{solicitudId}/finalizar")
    public SolicitudRes finalizar(@PathVariable UUID solicitudId) {
        return solicitudService.finalizarSolicitud(solicitudId);
    }
}
