package backend.grupo130.tramos.controller;

import backend.grupo130.tramos.dto.ActualizarTramoEstadoRequest;
import backend.grupo130.tramos.dto.TramoResponse;
import backend.grupo130.tramos.service.TramoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tramos")
@RequiredArgsConstructor
public class TramoController {

    private final TramoService tramoService;

    @GetMapping("/ruta/{rutaId}")
    public List<TramoResponse> listarPorRuta(@PathVariable UUID rutaId) {
        return tramoService.listarPorRuta(rutaId);
    }

    @PutMapping("/{id}")
    public TramoResponse actualizar(@PathVariable UUID id,
                                    @Valid @RequestBody ActualizarTramoEstadoRequest request) {
        return tramoService.actualizar(id, request);
    }
}
