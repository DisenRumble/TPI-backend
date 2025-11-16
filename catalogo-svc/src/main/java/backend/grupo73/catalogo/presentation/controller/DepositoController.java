package backend.grupo73.catalogo.presentation.controller;

import backend.grupo73.catalogo.domain.dto.request.DepositoCreateReq;
import backend.grupo73.catalogo.domain.dto.response.DepositoRes;
import backend.grupo73.catalogo.domain.service.DepositoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/depositos")
@RequiredArgsConstructor
public class DepositoController {

    private final DepositoService depositoService;

    @PostMapping
    public ResponseEntity<DepositoRes> create(@RequestBody DepositoCreateReq request) {
        return ResponseEntity.ok(depositoService.create(request));
    }

    @PutMapping("/{depositoId}")
    public ResponseEntity<DepositoRes> update(@PathVariable String depositoId, @RequestBody DepositoCreateReq request) {
        return ResponseEntity.ok(depositoService.update(depositoId, request));
    }

    @GetMapping
    public ResponseEntity<List<DepositoRes>> getAll() {
        return ResponseEntity.ok(depositoService.getAll());
    }
}
