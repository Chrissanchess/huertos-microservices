package mx.huertos.parcel.service;

import mx.huertos.parcel.config.JwtConfig;
import mx.huertos.parcel.dto.LoginRequest;
import mx.huertos.parcel.dto.RefreshRequest;
import mx.huertos.parcel.dto.TokenResponse;
import mx.huertos.parcel.exception.InvalidCredentialsException;
import mx.huertos.parcel.exception.InvalidTokenException;
import mx.huertos.parcel.model.AppUser;
import mx.huertos.parcel.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;

@Service
public class AuthService {

    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokens;
    private final JwtDecoder refreshDecoder;
    private final String dummyHash;

    public AuthService(UserRepository users, PasswordEncoder passwordEncoder,
                       TokenService tokens, SecretKey jwtSecretKey) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.tokens = tokens;
        // Decoder propio para refresh tokens (el de seguridad solo acepta tokens de acceso)
        NimbusJwtDecoder d = NimbusJwtDecoder.withSecretKey(jwtSecretKey)
                .macAlgorithm(MacAlgorithm.HS256).build();
        d.setJwtValidator(JwtValidators.createDefaultWithIssuer(JwtConfig.ISSUER));
        this.refreshDecoder = d;
        // Hash de relleno para gastar el mismo tiempo cuando el usuario no existe
        this.dummyHash = passwordEncoder.encode("relleno-anti-enumeracion");
    }

    @Transactional(readOnly = true)
    public TokenResponse login(LoginRequest req) {
        AppUser user = users.findByUsername(req.username()).orElse(null);
        String hash = (user != null) ? user.getPasswordHash() : dummyHash;
        boolean ok = passwordEncoder.matches(req.password(), hash);
        if (user == null || !ok) {
            throw new InvalidCredentialsException();
        }
        return tokens.issueFor(user);
    }

    @Transactional(readOnly = true)
    public TokenResponse refresh(RefreshRequest req) {
        Jwt jwt;
        try {
            jwt = refreshDecoder.decode(req.refreshToken());
        } catch (JwtException e) {
            throw new InvalidTokenException();
        }
        if (!"refresh".equals(jwt.getClaimAsString("type"))) {
            throw new InvalidTokenException();
        }
        AppUser user = users.findByUsername(jwt.getSubject())
                .orElseThrow(InvalidTokenException::new);
        return tokens.issueFor(user);
    }
}