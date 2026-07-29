package az.library.library.controller;

import az.library.library.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User", description = "İstifadəçi əməliyyatları")
public class UserController {

    @Operation(summary = "İstifadəçi profilini göstər")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<Map<String, Object>>> profile(@AuthenticationPrincipal Object principal) {
        return ResponseEntity.ok(ApiResponse.success(Map.of("profile", principal)));
    }
}
