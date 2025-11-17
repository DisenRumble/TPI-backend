package backend.grupo130.solicitudes.controller;

import backend.grupo130.solicitudes.dto.TarifaRequest;
import backend.grupo130.solicitudes.dto.TarifaResponse;
import backend.grupo130.solicitudes.service.TarifaService;
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
@RequestMapping("/api/tarifas")
@RequiredArgsConstructor
public class TarifaController {

    private final TarifaService tarifaService;

    @GetMapping
    public List<TarifaResponse> listar(@RequestParam(value = "vigentes", defaultValue = "true") boolean vigentes) {
        return tarifaService.listar(vigentes);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TarifaResponse crear(@Valid @RequestBody TarifaRequest request) {
        return tarifaService.crear(request);
    }

    @PutMapping("/{id}")
    public TarifaResponse actualizar(@PathVariable UUID id, @Valid @RequestBody TarifaRequest request) {
        return tarifaService.actualizar(id, request);
    }
}
