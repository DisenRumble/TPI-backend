package backend.grupo73.solicitudes_svc.config.exceptions;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private final ApiError type;

    public ApiException(ApiError type, String message) {
        super(message);
        this.type = type;
    }
}
