package backend.grupo130.ubicaciones.controller;

import backend.grupo130.ubicaciones.dto.DepositoRequest;
import backend.grupo130.ubicaciones.dto.DepositoResponse;
import backend.grupo130.ubicaciones.service.DepositoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/depositos")
@RequiredArgsConstructor
public class DepositoController {

    private final DepositoService depositoService;

    @GetMapping
    public List<DepositoResponse> listar() {
        return depositoService.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepositoResponse registrar(@Valid @RequestBody DepositoRequest request) {
        return depositoService.registrar(request);
    }

    @PutMapping("/{id}")
    public DepositoResponse actualizar(@PathVariable UUID id, @Valid @RequestBody DepositoRequest request) {
        return depositoService.actualizar(id, request);
    }
}
