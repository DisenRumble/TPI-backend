package backend.grupo73.catalogo.presentation.controller;

import backend.grupo73.catalogo.domain.dto.request.TarifaCreateReq;
import backend.grupo73.catalogo.domain.dto.response.TarifaRes;
import backend.grupo73.catalogo.domain.service.TarifaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarifas")
@RequiredArgsConstructor
public class TarifaController {

    private final TarifaService tarifaService;

    @PostMapping
    public ResponseEntity<TarifaRes> create(@RequestBody TarifaCreateReq request) {
        return ResponseEntity.ok(tarifaService.create(request));
    }

    @PutMapping("/{tarifaId}")
    public ResponseEntity<TarifaRes> update(@PathVariable Long tarifaId, @RequestBody TarifaCreateReq request) {
        return ResponseEntity.ok(tarifaService.update(tarifaId, request));
    }

    @GetMapping("/vigente")
    public ResponseEntity<TarifaRes> getVigente() {
        return ResponseEntity.ok(tarifaService.getVigente());
    }
}
