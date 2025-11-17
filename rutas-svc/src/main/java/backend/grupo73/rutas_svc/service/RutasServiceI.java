package backend.grupo73.rutas_svc.service;

import backend.grupo73.rutas_svc.domain.dto.request.CalcularRutaReq;
import backend.grupo73.rutas_svc.domain.model.RutaCalculadaModel;

import java.util.List;

public interface RutasServiceI {

    RutaCalculadaModel calcularRuta(CalcularRutaReq request);

    List<RutaCalculadaModel> calcularRutasConAlternativas(CalcularRutaReq request);
}
