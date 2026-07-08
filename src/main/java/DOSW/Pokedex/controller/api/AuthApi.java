package DOSW.Pokedex.controller.api;

import DOSW.Pokedex.controller.dto.response.TokenResponse;
import DOSW.Pokedex.controller.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Auth", description = "Autenticación y perfil de usuario")
@RequestMapping("/v1/auth")
public interface AuthApi {

    @Operation(summary = "Obtener información del usuario autenticado")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/me")
    ResponseEntity<UserResponse> me();
}
