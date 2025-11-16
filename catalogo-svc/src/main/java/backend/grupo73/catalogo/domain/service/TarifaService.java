package backend.grupo73.catalogo.domain.service;

import backend.grupo73.catalogo.domain.dto.request.TarifaCreateReq;
import backend.grupo73.catalogo.domain.dto.response.TarifaRes;
import backend.grupo73.catalogo.domain.repository.TarifaModel;
import backend.grupo73.catalogo.domain.repository.TarifaRepositoryI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TarifaService {

    private final TarifaRepositoryI tarifaRepository;

    public TarifaRes create(TarifaCreateReq request) {
        TarifaModel model = new TarifaModel();
        model.setName(request.getName());
        model.setPrecio(request.getPrecio());
        model.setVigente(true);
        model.setFecha(LocalDate.now());
        tarifaRepository.save(model);
        return new TarifaRes(model.getId(), model.getName(), model.getPrecio());
    }

    public TarifaRes update(String id, TarifaCreateReq request) {
        TarifaModel model = tarifaRepository.findById(id).orElseThrow();
        model.setName(request.getName());
        model.setPrecio(request.getPrecio());
        tarifaRepository.save(model);
        return new TarifaRes(model.getId(), model.getName(), model.getPrecio());
    }

    public TarifaRes getVigente() {
        TarifaModel model = tarifaRepository.findByVigente(true).orElseThrow();
        return new TarifaRes(model.getId(), model.getName(), model.getPrecio());
    }
}
