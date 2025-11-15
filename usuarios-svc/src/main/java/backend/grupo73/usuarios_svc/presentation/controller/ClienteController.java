package backend.grupo73.usuarios_svc.presentation.controller;

import backend.grupo73.usuarios_svc.config.JwtUtils;
import backend.grupo73.usuarios_svc.config.exceptions.ApiError;
import backend.grupo73.usuarios_svc.config.exceptions.ApiException;
import backend.grupo73.usuarios_svc.domain.ClienteModel;
import backend.grupo73.usuarios_svc.domain.dto.request.ClienteCreateReq;
import backend.grupo73.usuarios_svc.domain.dto.response.ClienteRes;
import backend.grupo73.usuarios_svc.domain.repository.ClienteRepositoryI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteRepositoryI repo;

    @GetMapping("/me")
    public ClienteRes me(Authentication auth) {
        String sub = JwtUtils.sub(auth);
        var found = repo.findByKeycloakSub(sub).orElseGet(() -> {
            var nuevo = ClienteModel.builder()
                    .keycloakSub(sub)
                    .email(JwtUtils.email(auth))
                    .nombre(JwtUtils.givenName(auth))
                    .apellido(JwtUtils.familyName(auth))
                    .build();
            return repo.save(nuevo);
        });
        return ClienteRes.from(found);
    }

    @GetMapping("/search")
    public ClienteRes searchByEmail(@RequestParam String email) {
        return repo.findByEmail(email)
                .map(ClienteRes::from)
                .orElseThrow(() -> new ApiException(ApiError.NOT_FOUND,
                        "Cliente no encontrado"));
    }

    @PostMapping
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
