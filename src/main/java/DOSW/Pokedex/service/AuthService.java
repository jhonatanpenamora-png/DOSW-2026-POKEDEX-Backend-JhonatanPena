package DOSW.Pokedex.service;

import DOSW.Pokedex.model.User;
import DOSW.Pokedex.model.UserEntity;
import DOSW.Pokedex.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserJpaRepository userRepo;

    @Transactional
    public User processOAuth2User(String email, String name, String avatarUrl, String providerId) {
        log.info("Procesando usuario OAuth2: {}", email);

        return userRepo.findByEmail(email)
                .map(existing -> {
                    if (!existing.getEnabled()) {
                        throw new RuntimeException("Cuenta desactivada");
                    }
                    return toDomain(existing);
                })
                .orElseGet(() -> {
                    UserEntity entity = UserEntity.builder()
                            .username(email.split("@")[0])
                            .email(email)
                            .avatarUrl(avatarUrl)
                            .provider("google")
                            .providerId(providerId)
                            .role("USER")
                            .enabled(true)
                            .build();
                    UserEntity saved = userRepo.save(entity);
                    return toDomain(saved);
                });
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepo.findById(id)
                .map(AuthService::toDomain)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepo.findByEmail(email)
                .map(AuthService::toDomain)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + email));
    }

    private static User toDomain(UserEntity entity) {
        if (entity == null) return null;
        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .avatarUrl(entity.getAvatarUrl())
                .role(entity.getRole())
                .provider(entity.getProvider())
                .providerId(entity.getProviderId())
                .enabled(entity.getEnabled())
                .build();
    }
}
