package mx.huertos.parcel.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends ApiException {
    public InvalidTokenException() {
        super(HttpStatus.UNAUTHORIZED, "INVALID_REFRESH_TOKEN",
              "El refresh token es inválido o expiró. Inicia sesión de nuevo");
    }
}