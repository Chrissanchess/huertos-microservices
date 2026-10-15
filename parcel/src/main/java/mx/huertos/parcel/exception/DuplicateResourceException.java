package mx.huertos.parcel.exception;

import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends ApiException {
    public DuplicateResourceException(String name) {
        super(HttpStatus.CONFLICT, "PARCEL_DUPLICATED",
              "Ya existe una parcela registrada con el nombre '" + name + "'");
    }
}