package mx.huertos.parcel.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

/** Responde 401 y 403 en JSON con el mismo formato ApiError del resto de la API. */
@Component
public class ApiSecurityErrorHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

    @Override   // 401: no hay credenciales o el token es inválido/expirado
    public void commence(HttpServletRequest req, HttpServletResponse res,
                         AuthenticationException ex) throws IOException {
        boolean badToken = ex instanceof OAuth2AuthenticationException;
        res.setHeader("WWW-Authenticate", "Bearer");
        write(req, res, HttpStatus.UNAUTHORIZED,
                badToken ? "INVALID_TOKEN" : "AUTHENTICATION_REQUIRED",
                badToken ? "El token es inválido o expiró"
                         : "Se requiere autenticación para acceder a este recurso");
    }

    @Override   // 403: autenticado, pero sin el rol necesario
    public void handle(HttpServletRequest req, HttpServletResponse res,
                       AccessDeniedException ex) throws IOException {
        write(req, res, HttpStatus.FORBIDDEN, "ACCESS_DENIED",
                "No tienes permisos para realizar esta acción");
    }

    private void write(HttpServletRequest req, HttpServletResponse res,
                       HttpStatus status, String code, String message) throws IOException {
        res.setStatus(status.value());
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String path = req.getRequestURI().replace("\\", "\\\\").replace("\"", "\\\"");
        res.getWriter().write("""
                {"timestamp":"%s","status":%d,"error":"%s","code":"%s","message":"%s","path":"%s","details":[]}"""
                .formatted(Instant.now(), status.value(), status.getReasonPhrase(), code, message, path));
    }
}