package backend.grupo73.camiones.controller;

import backend.grupo73.camiones.dto.CamionDTO;
import backend.grupo73.camiones.dto.CapacidadValidationRequest;
import backend.grupo73.camiones.dto.CreateCamionDTO;
import backend.grupo73.camiones.service.CamionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/camiones")
@RequiredArgsConstructor
public class CamionController {

    private final CamionService camionService;

    @PostMapping
    public ResponseEntity<CamionDTO> createCamion(@RequestBody CreateCamionDTO createCamionDTO) {
        return ResponseEntity.ok(camionService.createCamion(createCamionDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CamionDTO> updateCamion(@PathVariable Long id, @RequestBody CreateCamionDTO createCamionDTO) {
        return camionService.updateCamion(id, createCamionDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CamionDTO> getCamionById(@PathVariable Long id) {
        return camionService.getCamionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/validar-capacidad")
    public ResponseEntity<String> validarCapacidad(@RequestBody CapacidadValidationRequest request) {
        boolean esValido = camionService.validarCapacidad(request);
        if (esValido) {
            return ResponseEntity.ok("OK");
        } else {
            return ResponseEntity.badRequest().body("ERROR");
        }
    }

    @GetMapping("/disponibilidad")
    public ResponseEntity<List<CamionDTO>> getDisponibilidad(
            @RequestParam(required = false) LocalDate desde,
            @RequestParam(required = false) LocalDate hasta) {
        // TODO: Implementar la lógica de disponibilidad cruzando con el servicio de tramos
        return ResponseEntity.ok(Collections.emptyList());
    }
}