package backend.grupo73.rutas_svc.domain.dto.response;

import backend.grupo73.rutas_svc.domain.model.RutaCalculadaModel;

import java.util.List;

public record RutasCalculadasRes(
        List<RutaAlternativaRes> alternativas
) {
    public static RutasCalculadasRes from(List<RutaCalculadaModel> models) {
        List<RutaAlternativaRes> alternativasRes =
                java.util.stream.IntStream.range(0, models.size())
                        .mapToObj(i -> RutaAlternativaRes.from(
                                models.get(i),
                                i + 1,
                                i == 0
                        ))
                        .toList();

        return new RutasCalculadasRes(alternativasRes);
    }
}
