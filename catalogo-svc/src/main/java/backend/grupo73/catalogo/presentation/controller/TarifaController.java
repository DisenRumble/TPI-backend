package backend.grupo73.catalogo.presentation.controller;

import backend.grupo73.catalogo.domain.dto.request.TarifaCreateReq;
import backend.grupo73.catalogo.domain.dto.response.TarifaRes;
import backend.grupo73.catalogo.domain.service.TarifaServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tarifas")
@RequiredArgsConstructor
public class TarifaController {

    private final TarifaServiceI tarifaService;

    @PostMapping
    public ResponseEntity<TarifaRes> create(@RequestBody TarifaCreateReq request) {
        return ResponseEntity.ok(tarifaService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarifaRes> update(@PathVariable UUID id, @RequestBody TarifaCreateReq request) {
        return ResponseEntity.ok(tarifaService.update(id, request));
    }

    @GetMapping
    public ResponseEntity<TarifaRes> getTarifaVigente() {
        return ResponseEntity.ok(tarifaService.getTarifaVigente());
    }
}
