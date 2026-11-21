package mx.huertos.parcel.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        long expiresIn      // segundos de vida del access token
) {}