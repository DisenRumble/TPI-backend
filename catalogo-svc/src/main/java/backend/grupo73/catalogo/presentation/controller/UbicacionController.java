package backend.grupo73.catalogo.presentation.controller;

import backend.grupo73.catalogo.domain.dto.request.UbicacionCreateReq;
import backend.grupo73.catalogo.domain.dto.response.UbicacionRes;
import backend.grupo73.catalogo.domain.service.UbicacionServiceI; // Changed to interface
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ubicaciones")
@RequiredArgsConstructor
public class UbicacionController {

    private final UbicacionServiceI ubicacionService;

    @PostMapping
    public ResponseEntity<UbicacionRes> create(@RequestBody UbicacionCreateReq request) {
        return ResponseEntity.ok(ubicacionService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UbicacionRes> update(@PathVariable UUID id, @RequestBody UbicacionCreateReq request) {
        return ResponseEntity.ok(ubicacionService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UbicacionRes> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(ubicacionService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<UbicacionRes>> search(@RequestParam(required = false) String search) {
        return ResponseEntity.ok(ubicacionService.search(search == null ? "" : search));
    }
}
