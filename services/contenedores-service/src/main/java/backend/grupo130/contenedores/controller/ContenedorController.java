package backend.grupo130.contenedores.controller;

import backend.grupo130.contenedores.dto.ActualizarContenedorRequest;
import backend.grupo130.contenedores.dto.CambioEstadoRequest;
import backend.grupo130.contenedores.dto.ContenedorResponse;
import backend.grupo130.contenedores.dto.CrearContenedorRequest;
import backend.grupo130.contenedores.entity.EstadoContenedor;
import backend.grupo130.contenedores.service.ContenedorService;
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
@RequestMapping("/api/contenedores")
@RequiredArgsConstructor
public class ContenedorController {

    private final ContenedorService contenedorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContenedorResponse registrar(@Valid @RequestBody CrearContenedorRequest request) {
        return contenedorService.registrar(request);
    }

    @GetMapping
    public List<ContenedorResponse> listar(@RequestParam(value = "estado", required = false) EstadoContenedor estado,
                                           @RequestParam(value = "depositoId", required = false) UUID depositoId,
                                           @RequestParam(value = "clienteId", required = false) UUID clienteId) {
        return contenedorService.listar(estado, depositoId, clienteId);
    }

    @GetMapping("/{id}")
    public ContenedorResponse obtener(@PathVariable UUID id) {
        return contenedorService.obtener(id);
    }

    @PutMapping("/{id}")
    public ContenedorResponse actualizar(@PathVariable UUID id,
                                         @Valid @RequestBody ActualizarContenedorRequest request) {
        return contenedorService.actualizar(id, request);
    }

    @PutMapping("/{id}/estado")
    public ContenedorResponse cambiarEstado(@PathVariable UUID id,
                                            @Valid @RequestBody CambioEstadoRequest request) {
        return contenedorService.cambiarEstado(id, request);
    }
}
