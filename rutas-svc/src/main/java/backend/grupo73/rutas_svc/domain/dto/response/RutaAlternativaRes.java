package backend.grupo73.rutas_svc.domain.dto.response;

import backend.grupo73.rutas_svc.domain.model.RutaCalculadaModel;

import java.math.BigDecimal;
import java.util.List;

public record RutaAlternativaRes(
        Integer id,
        boolean esPrincipal,
        List<TramoRes> tramos,
        BigDecimal distanciaTotalKm,
        Integer duracionTotalMinutos
) {
    public static RutaAlternativaRes from(RutaCalculadaModel m, int index, boolean esPrincipal) {
        List<TramoRes> tramosRes = m.getTramos()
                .stream()
                .map(TramoRes::from)
                .toList();

        return new RutaAlternativaRes(
                index,
                esPrincipal,
                tramosRes,
                BigDecimal.valueOf(m.getDistanciaTotalKm()),
                (int) Math.round(m.getDuracionTotalMinutos())
        );
    }
}
