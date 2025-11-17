package backend.grupo73.usuarios_svc.presentation.controller;

import backend.grupo73.usuarios_svc.config.exceptions.ApiError;
import backend.grupo73.usuarios_svc.config.exceptions.ApiException;
import backend.grupo73.usuarios_svc.domain.ClienteModel;
import backend.grupo73.usuarios_svc.domain.dto.request.ClienteCreateReq;
import backend.grupo73.usuarios_svc.domain.dto.response.ClienteRes;
import backend.grupo73.usuarios_svc.domain.repository.ClienteRepositoryI;
import backend.grupo73.usuarios_svc.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteRepositoryI repo;
    private final ClienteService clienteService;

    @PostMapping("/api/clientes/auto-register")
    public ClienteRes autoRegister(@AuthenticationPrincipal Jwt jwt) {
        String sub = jwt.getSubject();
        String email = jwt.getClaim("email");
        String nombre = jwt.getClaim("given_name");
        String apellido = jwt.getClaim("family_name");
        return clienteService.findOrCreate(sub, email, nombre, apellido);
    }

    @GetMapping("/api/clientes/{id}")
    public ClienteRes getClienteById(@PathVariable UUID id) {
        return clienteService.getById(id);
    }

    @GetMapping("/clientes/me")
    public ClienteRes me(@AuthenticationPrincipal Jwt jwt) {
        String sub = jwt.getSubject();
        String email = jwt.getClaim("email");
        String nombre = jwt.getClaim("given_name");
        String apellido = jwt.getClaim("family_name");
        return clienteService.findOrCreate(sub, email, nombre, apellido);
    }

    @GetMapping("/clientes/search")
    public ClienteRes searchByEmail(@RequestParam String email) {
        return repo.findByEmail(email)
                .map(ClienteRes::from)
                .orElseThrow(() -> new ApiException(ApiError.NOT_FOUND, "Cliente no encontrado"));
    }

    @PostMapping("/clientes")
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteRes create(@RequestBody ClienteCreateReq req) {
        if (repo.findByEmail(req.email()).isPresent()) {
            throw new ApiException(ApiError.CONFLICT, "Email ya registrado");
        }
        var c = repo.save(ClienteModel.builder()
                .keycloakSub(req.keycloakSub())
                .email(req.email())
                .nombre(req.nombre())
                .apellido(req.apellido())
                .telefono(req.telefono())
                .direccion(req.direccion())
                .build());
        return ClienteRes.from(c);
    }
}
