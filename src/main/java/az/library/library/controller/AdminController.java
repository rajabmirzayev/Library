package az.library.library.controller;

import az.library.library.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@Tag(name = "Admin", description = "Admin əməliyyatları")
public class AdminController {

    @Operation(summary = "Admin dashboard")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Dashboard datası"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Qadağan olunmuş giriş", content = @Content)
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<Map<String, String>>> dashboard() {
        return ResponseEntity.ok(ApiResponse.success(Map.of("dashboard", "Admin panel")));
    }
}
