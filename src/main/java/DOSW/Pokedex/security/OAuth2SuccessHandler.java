package DOSW.Pokedex.security;

import DOSW.Pokedex.core.model.User;
import DOSW.Pokedex.core.service.interfaces.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthService authService;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String avatarUrl = oAuth2User.getAttribute("picture");
        String providerId = oAuth2User.getName();

        User user = authService.processOAuth2User(email, name, avatarUrl, providerId);

        var userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password("")
                .roles(user.getRole())
                .disabled(!user.getEnabled())
                .build();

        String jwt = jwtService.generateToken(userDetails);
        log.info("Usuario autenticado via Google: {}", email);

        String redirectUrl = "http://localhost:5173/auth/callback?token=" + jwt;
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
