package mx.huertos.parcel.service;

import mx.huertos.parcel.config.JwtConfig;
import mx.huertos.parcel.dto.TokenResponse;
import mx.huertos.parcel.model.AppUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class TokenService {

    private final JwtEncoder encoder;
    private final Duration accessTtl;
    private final Duration refreshTtl;

    public TokenService(JwtEncoder encoder,
                        @Value("${jwt.access-token-minutes}") long accessMinutes,
                        @Value("${jwt.refresh-token-days}") long refreshDays) {
        this.encoder = encoder;
        this.accessTtl = Duration.ofMinutes(accessMinutes);
        this.refreshTtl = Duration.ofDays(refreshDays);
    }

    public TokenResponse issueFor(AppUser user) {
        String access = encode(user.getUsername(), "access", List.of(user.getRole()), accessTtl);
        // El refresh token NO lleva roles: al renovar se leen de nuevo desde la base de datos
        String refresh = encode(user.getUsername(), "refresh", List.of(), refreshTtl);
        return new TokenResponse(access, refresh, "Bearer", accessTtl.toSeconds());
    }

    private String encode(String subject, String type, List<String> roles, Duration ttl) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(JwtConfig.ISSUER)
                .subject(subject)
                .issuedAt(now)
                .expiresAt(now.plus(ttl))
                .claim("type", type)
                .claim("roles", roles)
                .build();
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return encoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }
}