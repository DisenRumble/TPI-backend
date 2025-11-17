package backend.grupo130.solicitudes.controller;

import backend.grupo130.solicitudes.dto.ClienteRequest;
import backend.grupo130.solicitudes.dto.ClienteResponse;
import backend.grupo130.solicitudes.dto.RegistroClienteRequest;
import backend.grupo130.solicitudes.service.ClienteService;
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
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public List<ClienteResponse> listar() {
        return clienteService.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse crear(@Valid @RequestBody ClienteRequest request) {
        return clienteService.crearOActualizar(request);
    }

    @PostMapping("/registro")
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse registrar(@Valid @RequestBody RegistroClienteRequest request) {
        return clienteService.registrar(request);
    }
}
