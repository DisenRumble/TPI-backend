package backend.grupo130.tramos.controller;

import backend.grupo130.tramos.dto.RutaRequest;
import backend.grupo130.tramos.dto.RutaResponse;
import backend.grupo130.tramos.service.RutaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/rutas")
@RequiredArgsConstructor
public class RutaController {

    private final RutaService rutaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RutaResponse crear(@Valid @RequestBody RutaRequest request) {
        return rutaService.crearRuta(request);
    }

    @GetMapping("/{id}")
    public RutaResponse obtener(@PathVariable UUID id) {
        return rutaService.obtenerRuta(id);
    }

    @GetMapping("/solicitud/{solicitudId}")
    public RutaResponse porSolicitud(@PathVariable UUID solicitudId) {
        return rutaService.obtenerPorSolicitud(solicitudId);
    }
}
