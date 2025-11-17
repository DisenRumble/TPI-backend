package backend.grupo130.camiones.controller;

import backend.grupo130.camiones.dto.TransportistaRequest;
import backend.grupo130.camiones.dto.TransportistaResponse;
import backend.grupo130.camiones.service.TransportistaService;
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
@RequestMapping("/api/transportistas")
@RequiredArgsConstructor
public class TransportistaController {

    private final TransportistaService transportistaService;

    @GetMapping
    public List<TransportistaResponse> listar() {
        return transportistaService.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransportistaResponse registrar(@Valid @RequestBody TransportistaRequest request) {
        return transportistaService.registrar(request);
    }
}
