package DOSW.Pokedex.controller;

import DOSW.Pokedex.dto.response.UserResponse;
import DOSW.Pokedex.mapper.UserDtoMapper;
import DOSW.Pokedex.model.User;
import DOSW.Pokedex.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "Autenticación y perfil de usuario")
@RequestMapping("/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserDtoMapper mapper;

    @Operation(summary = "Obtener información del usuario autenticado")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> me() {
        return ResponseEntity.ok(mapper.toResponse(getCurrentUser()));
    }

    private User getCurrentUser() {
        Authentication auth = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("No autenticado");
        }

        String email = auth.getName();
        return authService.findByEmail(email);
    }
}
