package mx.huertos.garden.exception;

import org.springframework.http.HttpStatus;

public class ParcelServiceUnavailableException extends ApiException {
    public ParcelServiceUnavailableException(String reason) {
        super(HttpStatus.SERVICE_UNAVAILABLE, "PARCEL_SERVICE_UNAVAILABLE",
              "El servicio de parcelas no está disponible en este momento (" + reason + "). Intenta más tarde");
    }
}