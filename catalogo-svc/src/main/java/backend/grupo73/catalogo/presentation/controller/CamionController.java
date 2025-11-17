package backend.grupo73.catalogo.presentation.controller;

import backend.grupo73.catalogo.domain.dto.request.CamionCreateReq;
import backend.grupo73.catalogo.domain.dto.response.CamionRes;
import backend.grupo73.catalogo.domain.service.CamionServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/camiones")
@RequiredArgsConstructor
public class CamionController {

    private final CamionServiceI camionService;

    @PostMapping
    public ResponseEntity<CamionRes> create(@RequestBody CamionCreateReq request) {
        return ResponseEntity.ok(camionService.create(request));
    }

    @PutMapping("/{camionId}")
    public ResponseEntity<CamionRes> update(@PathVariable UUID camionId, @RequestBody CamionCreateReq request) {
        return ResponseEntity.ok(camionService.update(camionId, request));
    }

    @GetMapping("/{camionId}")
    public ResponseEntity<CamionRes> getById(@PathVariable UUID camionId) {
        return ResponseEntity.ok(camionService.getById(camionId));
    }

    @GetMapping
    public ResponseEntity<List<CamionRes>> getAll() {
        return ResponseEntity.ok(camionService.getAll());
    }
}
