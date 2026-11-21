package mx.huertos.parcel.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends ApiException {
    public InvalidCredentialsException() {
        // Mensaje genérico: no revela si falló el usuario o la contraseña
        super(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS", "Usuario o contraseña incorrectos");
    }
}