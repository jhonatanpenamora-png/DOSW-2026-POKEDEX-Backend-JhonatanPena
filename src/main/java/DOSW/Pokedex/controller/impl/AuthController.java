package DOSW.Pokedex.controller.impl;

import DOSW.Pokedex.controller.api.AuthApi;
import DOSW.Pokedex.controller.dto.response.UserResponse;
import DOSW.Pokedex.controller.mapper.UserDtoMapper;
import DOSW.Pokedex.core.model.User;
import DOSW.Pokedex.core.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;
    private final UserDtoMapper mapper;

    @Override
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
