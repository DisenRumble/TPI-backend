package backend.grupo73.usuarios_svc.service.impl;

import backend.grupo73.usuarios_svc.config.exceptions.ApiError;
import backend.grupo73.usuarios_svc.config.exceptions.ApiException;
import backend.grupo73.usuarios_svc.domain.ClienteModel;
import backend.grupo73.usuarios_svc.domain.dto.response.ClienteRes;
import backend.grupo73.usuarios_svc.domain.repository.ClienteRepositoryI;
import backend.grupo73.usuarios_svc.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepositoryI clienteRepository;

    @Override
    @Transactional
    public ClienteRes findOrCreate(String sub, String email, String nombre, String apellido) {
        Optional<ClienteModel> clienteOpt = clienteRepository.findByKeycloakSub(sub);

        if (clienteOpt.isPresent()) {
            return ClienteRes.from(clienteOpt.get());
        }

        clienteOpt = clienteRepository.findByEmail(email);

        if (clienteOpt.isPresent()) {
            ClienteModel cliente = clienteOpt.get();
            cliente.setKeycloakSub(sub);
            return ClienteRes.from(clienteRepository.save(cliente));
        }

        ClienteModel nuevoCliente = ClienteModel.builder()
                .keycloakSub(sub)
                .email(email)
                .nombre(nombre)
                .apellido(apellido)
                .build();
        return ClienteRes.from(clienteRepository.save(nuevoCliente));
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteRes getById(UUID id) {
        return clienteRepository.findById(id)
                .map(ClienteRes::from)
                .orElseThrow(() -> new ApiException(ApiError.NOT_FOUND, "Cliente no encontrado con ID: " + id));
    }
}
