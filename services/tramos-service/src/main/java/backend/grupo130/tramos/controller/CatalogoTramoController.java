package backend.grupo130.tramos.controller;

import backend.grupo130.tramos.dto.EstadoTramoRequest;
import backend.grupo130.tramos.dto.EstadoTramoResponse;
import backend.grupo130.tramos.dto.TipoTramoRequest;
import backend.grupo130.tramos.dto.TipoTramoResponse;
import backend.grupo130.tramos.service.CatalogoTramoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalogo-tramos")
@RequiredArgsConstructor
public class CatalogoTramoController {

    private final CatalogoTramoService catalogoTramoService;

    @PostMapping("/estados")
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoTramoResponse crearEstado(@Valid @RequestBody EstadoTramoRequest request) {
        return catalogoTramoService.crearEstado(request);
    }

    @GetMapping("/estados")
    public List<EstadoTramoResponse> listarEstados() {
        return catalogoTramoService.listarEstados();
    }

    @PostMapping("/tipos")
    @ResponseStatus(HttpStatus.CREATED)
    public TipoTramoResponse crearTipo(@Valid @RequestBody TipoTramoRequest request) {
        return catalogoTramoService.crearTipo(request);
    }

    @GetMapping("/tipos")
    public List<TipoTramoResponse> listarTipos() {
        return catalogoTramoService.listarTipos();
    }
}
