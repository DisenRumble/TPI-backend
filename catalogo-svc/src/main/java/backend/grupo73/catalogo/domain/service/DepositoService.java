package backend.grupo73.catalogo.domain.service;

import backend.grupo73.catalogo.domain.dto.request.DepositoCreateReq;
import backend.grupo73.catalogo.domain.dto.response.DepositoRes;
import backend.grupo73.catalogo.domain.repository.DepositoModel;
import backend.grupo73.catalogo.domain.repository.DepositoRepositoryI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepositoService {

    private final DepositoRepositoryI depositoRepository;

    public DepositoRes create(DepositoCreateReq request) {
        DepositoModel model = new DepositoModel();
        model.setName(request.getName());
        model.setAddress(request.getAddress());
        depositoRepository.save(model);
        return new DepositoRes(model.getId(), model.getName(), model.getAddress());
    }

    public DepositoRes update(String id, DepositoCreateReq request) {
        DepositoModel model = depositoRepository.findById(id).orElseThrow();
        model.setName(request.getName());
        model.setAddress(request.getAddress());
        depositoRepository.save(model);
        return new DepositoRes(model.getId(), model.getName(), model.getAddress());
    }

    public List<DepositoRes> getAll() {
        return depositoRepository.findAll().stream()
                .map(model -> new DepositoRes(model.getId(), model.getName(), model.getAddress()))
                .collect(Collectors.toList());
    }
}
