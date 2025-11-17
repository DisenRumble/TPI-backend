package backend.grupo73.rutas_svc.config.exceptions;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    @Getter
    private final ApiError type;

    public ApiException(ApiError type, String message) {
        super(message);
        this.type = type;
    }
}
