package DOSW.Pokedex.controller.api;

import DOSW.Pokedex.controller.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin", description = "Administración de usuarios")
@RequestMapping("/v1/admin")
@SecurityRequirement(name = "Bearer Authentication")
public interface AdminApi {

    @Operation(summary = "Listar todos los usuarios", description = "Solo ADMIN")
    @GetMapping("/users")
    ResponseEntity<List<UserResponse>> findAllUsers();

    @Operation(summary = "Cambiar rol de usuario", description = "Solo ADMIN")
    @PutMapping("/users/{userId}/role")
    ResponseEntity<UserResponse> updateUserRole(@PathVariable Long userId, @RequestParam String role);

    @Operation(summary = "Activar/desactivar usuario", description = "Solo ADMIN")
    @PutMapping("/users/{userId}/toggle")
    ResponseEntity<Void> toggleUserEnabled(@PathVariable Long userId);
}
