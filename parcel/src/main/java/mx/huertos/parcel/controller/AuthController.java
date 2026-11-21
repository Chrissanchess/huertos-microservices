package mx.huertos.parcel.controller;

import jakarta.validation.Valid;
import mx.huertos.parcel.dto.LoginRequest;
import mx.huertos.parcel.dto.RefreshRequest;
import mx.huertos.parcel.dto.TokenResponse;
import mx.huertos.parcel.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest request) {
        return service.login(request);
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(@Valid @RequestBody RefreshRequest request) {
        return service.refresh(request);
    }
}