package backend.grupo73.rutas_svc.presentation.controller;

import backend.grupo73.rutas_svc.domain.dto.request.CalcularRutaReq;
import backend.grupo73.rutas_svc.domain.dto.response.RutasCalculadasRes;
import backend.grupo73.rutas_svc.domain.model.RutaCalculadaModel;
import backend.grupo73.rutas_svc.service.RutasServiceI;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/rutas")
@Tag(name = "Rutas", description = "Endpoints para el cálculo de rutas con OSRM")
@SecurityRequirement(name = "bearerAuth")
public class RutasController {

    private final RutasServiceI rutasService;

    public RutasController(RutasServiceI rutasService) {
        this.rutasService = rutasService;
    }

    @PostMapping("/calcular")
    public ResponseEntity<RutasCalculadasRes> calcularRuta(@Valid @RequestBody CalcularRutaReq request) {
        List<RutaCalculadaModel> modelos = rutasService.calcularRutasConAlternativas(request);
        return ResponseEntity.ok(RutasCalculadasRes.from(modelos));
    }
}
