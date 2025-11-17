package backend.grupo130.camiones.controller;

import backend.grupo130.camiones.dto.ActualizarCamionRequest;
import backend.grupo130.camiones.dto.CamionRequest;
import backend.grupo130.camiones.dto.CamionResponse;
import backend.grupo130.camiones.entity.EstadoCamion;
import backend.grupo130.camiones.service.CamionService;
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

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/camiones")
@RequiredArgsConstructor
public class CamionController {

    private final CamionService camionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CamionResponse registrar(@Valid @RequestBody CamionRequest request) {
        return camionService.registrar(request);
    }

    @GetMapping
    public List<CamionResponse> listar(@RequestParam(value = "estado", required = false) EstadoCamion estado) {
        return camionService.listar(estado);
    }

    @GetMapping("/{patente}")
    public CamionResponse obtener(@PathVariable String patente) {
        return camionService.obtener(patente);
    }

    @PutMapping("/{patente}")
    public CamionResponse actualizar(@PathVariable String patente,
                                     @Valid @RequestBody ActualizarCamionRequest request) {
        return camionService.actualizar(patente, request);
    }

    @GetMapping("/disponibles")
    public List<CamionResponse> disponibles(@RequestParam BigDecimal pesoKg,
                                            @RequestParam BigDecimal volumenM3) {
        return camionService.disponiblesParaCarga(pesoKg, volumenM3);
    }
}
