package backend.grupo73.catalogo.domain.service;

import backend.grupo73.catalogo.domain.dto.request.TarifaCreateReq;
import backend.grupo73.catalogo.domain.dto.response.TarifaRes;
import backend.grupo73.catalogo.domain.entity.Tarifa;
import backend.grupo73.catalogo.domain.repository.TarifaRepositoryI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TarifaService implements TarifaServiceI {

    private final TarifaRepositoryI tarifaRepository;

    @Override
    @Transactional
    public TarifaRes create(TarifaCreateReq request) {
        Tarifa tarifa = new Tarifa();
        tarifa.setTarifaPorKm(request.getTarifaPorKm());
        tarifa.setTarifaPorKgKm(request.getTarifaPorKgKm());
        tarifa.setTarifaEstadiaPorHora(request.getTarifaEstadiaPorHora());
        tarifa.setActiva(request.isActiva());
        tarifa = tarifaRepository.save(tarifa);
        return toDto(tarifa);
    }

    @Override
    @Transactional
    public TarifaRes update(UUID id, TarifaCreateReq request) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarifa no encontrada"));
        tarifa.setTarifaPorKm(request.getTarifaPorKm());
        tarifa.setTarifaPorKgKm(request.getTarifaPorKgKm());
        tarifa.setTarifaEstadiaPorHora(request.getTarifaEstadiaPorHora());
        tarifa.setActiva(request.isActiva());
        tarifa = tarifaRepository.save(tarifa);
        return toDto(tarifa);
    }

    @Override
    @Transactional(readOnly = true)
    public TarifaRes getTarifaVigente() {
        Tarifa tarifa = tarifaRepository.findByActivaTrue()
                .orElseThrow(() -> new RuntimeException("No se encontró ninguna tarifa activa."));
        return toDto(tarifa);
    }

    private TarifaRes toDto(Tarifa tarifa) {
        return new TarifaRes(
                tarifa.getId(),
                tarifa.getTarifaPorKm(),
                tarifa.getTarifaPorKgKm(),
                tarifa.getTarifaEstadiaPorHora(),
                tarifa.isActiva()
        );
    }
}
