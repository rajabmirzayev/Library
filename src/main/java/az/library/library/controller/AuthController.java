package az.library.library.controller;

import az.library.library.dto.ApiResponse;
import az.library.library.dto.request.LoginRequest;
import az.library.library.dto.request.RegisterRequest;
import az.library.library.dto.request.RegisterUserRequest;
import az.library.library.dto.response.AuthResponse;
import az.library.library.dto.response.UserDetailedResponse;
import az.library.library.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Authentication əməliyyatları")
public class AuthController {

    private final UserService userService;

    @Operation(summary = "Yeni istifadəçi qeydiyyatı")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "İstifadəçi yaradıldı"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validasiya xətası", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDetailedResponse>> register(@Valid @RequestBody RegisterRequest request) {
        RegisterUserRequest serviceRequest = new RegisterUserRequest(
                request.getUsername(), request.getEmail(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(userService.register(serviceRequest)));
    }

    @Operation(summary = "İstifadəçi daxil olma")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Uğurlu giriş"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Yanlış istifadəçi adı və ya şifrə", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userService.login(request)));
    }
}
