package mx.huertos.parcel.exception;

import org.springframework.http.HttpStatus;
import java.util.UUID;

public class ResourceNotFoundException extends ApiException {
    public ResourceNotFoundException(UUID id) {
        super(HttpStatus.NOT_FOUND, "PARCEL_NOT_FOUND",
              "No existe una parcela con el id " + id);
    }
}