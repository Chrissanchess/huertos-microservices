package mx.huertos.parcel.dto;

import java.time.Instant;
import java.util.List;

public record ApiError(
        Instant timestamp,
        int status,
        String error,
        String code,
        String message,
        String path,
        List<FieldDetail> details
) {
    public record FieldDetail(String field, String message) {}
}