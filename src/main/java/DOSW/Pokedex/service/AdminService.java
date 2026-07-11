package DOSW.Pokedex.service;

import DOSW.Pokedex.exception.ResourceNotFoundException;
import DOSW.Pokedex.model.User;
import DOSW.Pokedex.model.UserEntity;
import DOSW.Pokedex.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final UserJpaRepository userRepo;

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepo.findAll().stream()
                .map(AdminService::toDomain)
                .toList();
    }

    @Transactional
    public User updateUserRole(Long userId, String newRole) {
        UserEntity entity = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        UserEntity updated = UserEntity.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .avatarUrl(entity.getAvatarUrl())
                .role(newRole)
                .provider(entity.getProvider())
                .providerId(entity.getProviderId())
                .enabled(entity.getEnabled())
                .build();
        UserEntity saved = userRepo.save(updated);
        return toDomain(saved);
    }

    @Transactional
    public void toggleUserEnabled(Long userId) {
        UserEntity entity = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        UserEntity updated = UserEntity.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .avatarUrl(entity.getAvatarUrl())
                .role(entity.getRole())
                .provider(entity.getProvider())
                .providerId(entity.getProviderId())
                .enabled(!entity.getEnabled())
                .build();
        userRepo.save(updated);
    }

    static User toDomain(UserEntity entity) {
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
