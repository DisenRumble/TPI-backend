package backend.grupo73.catalogo.presentation.controller;

import backend.grupo73.catalogo.domain.dto.request.ValidacionCapacidadReq;
import backend.grupo73.catalogo.domain.service.ValidacionServiceI; // Changed to interface
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validaciones")
@RequiredArgsConstructor
public class ValidacionController {

    private final ValidacionServiceI validacionService;

    @PostMapping("/camion-capacidad")
    public ResponseEntity<Boolean> validarCapacidad(@RequestBody ValidacionCapacidadReq request) {
        return ResponseEntity.ok(validacionService.validarCapacidad(request));
    }
}
