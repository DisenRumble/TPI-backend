package backend.grupo130.ubicaciones.controller;

import backend.grupo130.ubicaciones.dto.DistanciaRequest;
import backend.grupo130.ubicaciones.dto.DistanciaResponse;
import backend.grupo130.ubicaciones.service.DistanciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/geografia")
@RequiredArgsConstructor
public class GeografiaController {

    private final DistanciaService distanciaService;

    @PostMapping("/distancia")
    public DistanciaResponse calcularDistancia(@Valid @RequestBody DistanciaRequest request) {
        return distanciaService.calcular(request);
    }
}
