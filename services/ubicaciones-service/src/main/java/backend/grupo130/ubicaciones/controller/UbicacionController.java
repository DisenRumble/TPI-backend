package backend.grupo130.ubicaciones.controller;

import backend.grupo130.ubicaciones.dto.UbicacionRequest;
import backend.grupo130.ubicaciones.dto.UbicacionResponse;
import backend.grupo130.ubicaciones.service.UbicacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ubicaciones")
@RequiredArgsConstructor
public class UbicacionController {

    private final UbicacionService ubicacionService;

    @GetMapping
    public List<UbicacionResponse> listar() {
        return ubicacionService.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UbicacionResponse registrar(@Valid @RequestBody UbicacionRequest request) {
        return ubicacionService.registrar(request);
    }

    @GetMapping("/{id}")
    public UbicacionResponse obtener(@PathVariable UUID id) {
        return ubicacionService.obtener(id);
    }
}
