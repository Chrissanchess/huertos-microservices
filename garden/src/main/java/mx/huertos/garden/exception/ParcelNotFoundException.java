package mx.huertos.garden.exception;

import org.springframework.http.HttpStatus;
import java.util.UUID;

public class ParcelNotFoundException extends ApiException {
    public ParcelNotFoundException(UUID id) {
        super(HttpStatus.NOT_FOUND, "PARCEL_NOT_FOUND",
              "No se encontró la parcela con id " + id + " en el servicio de parcelas");
    }
}