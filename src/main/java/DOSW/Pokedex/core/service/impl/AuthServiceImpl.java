package DOSW.Pokedex.core.service.impl;

import DOSW.Pokedex.core.model.User;
import DOSW.Pokedex.core.service.interfaces.AuthService;
import DOSW.Pokedex.core.service.interfaces.UserPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserPersistencePort userPort;

    @Override
    @Transactional
    public User processOAuth2User(String email, String name, String avatarUrl, String providerId) {
        log.info("Procesando usuario OAuth2: {}", email);

        return userPort.findByEmail(email)
                .map(existing -> {
                    if (!existing.getEnabled()) {
                        throw new RuntimeException("Cuenta desactivada");
                    }
                    return existing;
                })
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .username(email.split("@")[0])
                            .email(email)
                            .avatarUrl(avatarUrl)
                            .provider("google")
                            .providerId(providerId)
                            .role("USER")
                            .enabled(true)
                            .build();
                    return userPort.save(newUser);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userPort.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + email));
    }
}
