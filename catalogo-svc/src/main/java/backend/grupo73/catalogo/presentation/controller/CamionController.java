package backend.grupo73.catalogo.presentation.controller;

import backend.grupo73.catalogo.domain.dto.request.CamionCreateReq;
import backend.grupo73.catalogo.domain.dto.response.CamionRes;
import backend.grupo73.catalogo.domain.service.CamionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/camiones")
@RequiredArgsConstructor
public class CamionController {

    private final CamionService camionService;

    @PostMapping
    public ResponseEntity<CamionRes> create(@RequestBody CamionCreateReq request) {
        return ResponseEntity.ok(camionService.create(request));
    }

    @PutMapping("/{camionId}")
    public ResponseEntity<CamionRes> update(@PathVariable String camionId, @RequestBody CamionCreateReq request) {
        return ResponseEntity.ok(camionService.update(camionId, request));
    }

    @GetMapping
    public ResponseEntity<List<CamionRes>> getAll() {
        return ResponseEntity.ok(camionService.getAll());
    }
}
