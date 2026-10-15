package mx.huertos.parcel.exception;

import org.springframework.http.HttpStatus;

public class InvalidSoilTypeException extends ApiException {
    public InvalidSoilTypeException(String value) {
        super(HttpStatus.BAD_REQUEST, "INVALID_SOIL_TYPE",
              "Tipo de suelo inválido: '" + value + "'. Valores permitidos: arcilloso, limoso, arenoso");
    }
}