package mx.huertos.garden.client;

import mx.huertos.garden.dto.ParcelDto;
import mx.huertos.garden.exception.ParcelNotFoundException;
import mx.huertos.garden.exception.ParcelServiceUnavailableException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.UUID;

@Component
public class ParcelClient {

    private final RestClient restClient;

    public ParcelClient(RestClient parcelRestClient) {
        this.restClient = parcelRestClient;
    }

    public ParcelDto getById(UUID id) {
        try {
            return restClient.get()
                    .uri("/api/v1/parcels/{id}", id)
                    .retrieve()
                    .onStatus(s -> s.value() == 404, (req, res) -> {
                        throw new ParcelNotFoundException(id);
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                        throw new ParcelServiceUnavailableException(
                                "respondió con error " + res.getStatusCode().value());
                    })
                    .body(ParcelDto.class);
        } catch (ResourceAccessException e) {
            throw new ParcelServiceUnavailableException("sin conexión o tiempo de espera agotado");
        }
    }

    public List<ParcelDto> getAll() {
        try {
            return restClient.get()
                    .uri("/api/v1/parcels")
                    .retrieve()
                    .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                        throw new ParcelServiceUnavailableException(
                                "respondió con error " + res.getStatusCode().value());
                    })
                    .body(new ParameterizedTypeReference<List<ParcelDto>>() {});
        } catch (ResourceAccessException e) {
            throw new ParcelServiceUnavailableException("sin conexión o tiempo de espera agotado");
        }
    }
}