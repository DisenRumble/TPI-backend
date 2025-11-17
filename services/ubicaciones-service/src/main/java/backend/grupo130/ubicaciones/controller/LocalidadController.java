package backend.grupo130.ubicaciones.controller;

import backend.grupo130.ubicaciones.dto.BarrioRequest;
import backend.grupo130.ubicaciones.dto.BarrioResponse;
import backend.grupo130.ubicaciones.dto.CiudadRequest;
import backend.grupo130.ubicaciones.dto.CiudadResponse;
import backend.grupo130.ubicaciones.dto.ProvinciaRequest;
import backend.grupo130.ubicaciones.dto.ProvinciaResponse;
import backend.grupo130.ubicaciones.service.BarrioService;
import backend.grupo130.ubicaciones.service.CiudadService;
import backend.grupo130.ubicaciones.service.ProvinciaService;
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

@RestController
@RequestMapping("/api/localidades")
@RequiredArgsConstructor
public class LocalidadController {

    private final ProvinciaService provinciaService;
    private final CiudadService ciudadService;
    private final BarrioService barrioService;

    @GetMapping("/provincias")
    public List<ProvinciaResponse> provincias() {
        return provinciaService.listar();
    }

    @PostMapping("/provincias")
    @ResponseStatus(HttpStatus.CREATED)
    public ProvinciaResponse crearProvincia(@Valid @RequestBody ProvinciaRequest request) {
        return provinciaService.crear(request);
    }

    @GetMapping("/provincias/{id}/ciudades")
    public List<CiudadResponse> ciudadesPorProvincia(@PathVariable Long id) {
        return ciudadService.listarPorProvincia(id);
    }

    @PostMapping("/ciudades")
    @ResponseStatus(HttpStatus.CREATED)
    public CiudadResponse crearCiudad(@Valid @RequestBody CiudadRequest request) {
        return ciudadService.crear(request);
    }

    @GetMapping("/ciudades/{id}/barrios")
    public List<BarrioResponse> barriosPorCiudad(@PathVariable Long id) {
        return barrioService.listarPorCiudad(id);
    }

    @PostMapping("/barrios")
    @ResponseStatus(HttpStatus.CREATED)
    public BarrioResponse crearBarrio(@Valid @RequestBody BarrioRequest request) {
        return barrioService.crear(request);
    }
}
