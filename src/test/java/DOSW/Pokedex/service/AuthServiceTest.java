package DOSW.Pokedex.service;

import DOSW.Pokedex.TestFixtures;
import DOSW.Pokedex.model.User;
import DOSW.Pokedex.model.UserEntity;
import DOSW.Pokedex.repository.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserJpaRepository userRepo;

    @InjectMocks
    private AuthService service;

    private UserEntity newUserEntity;

    @BeforeEach
    void setUp() {
        newUserEntity = UserEntity.builder()
                .email("test@gmail.com")
                .username("test")
                .avatarUrl("https://example.com/avatar.png")
                .provider("google")
                .providerId("12345")
                .role("USER")
                .enabled(true)
                .build();
    }

    @Test
    @DisplayName("processOAuth2User debe crear usuario cuando no existe")
    void processOAuth2User_whenNewUser_creates() {
        when(userRepo.findByEmail("test@gmail.com")).thenReturn(Optional.empty());
        when(userRepo.save(any(UserEntity.class))).thenAnswer(invocation -> {
            UserEntity entity = invocation.getArgument(0);
            return UserEntity.builder()
                    .id(1L)
                    .username(entity.getUsername())
                    .email(entity.getEmail())
                    .avatarUrl(entity.getAvatarUrl())
                    .provider(entity.getProvider())
                    .providerId(entity.getProviderId())
                    .role(entity.getRole())
                    .enabled(entity.getEnabled())
                    .build();
        });

        User result = service.processOAuth2User("test@gmail.com", "Test", "https://example.com/avatar.png", "12345");

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("test@gmail.com");
        assertThat(result.getRole()).isEqualTo("USER");
        assertThat(result.getProvider()).isEqualTo("google");
        assertThat(result.getEnabled()).isTrue();

        verify(userRepo).findByEmail("test@gmail.com");
        verify(userRepo).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("processOAuth2User debe retornar usuario existente")
    void processOAuth2User_whenExisting_returnsUser() {
        UserEntity existingEntity = UserEntity.builder()
                .id(1L)
                .username("test")
                .email("test@gmail.com")
                .avatarUrl("https://example.com/avatar.png")
                .provider("google")
                .providerId("12345")
                .role("USER")
                .enabled(true)
                .build();
        when(userRepo.findByEmail("test@gmail.com")).thenReturn(Optional.of(existingEntity));

        User result = service.processOAuth2User("test@gmail.com", "Test", "https://example.com/avatar.png", "12345");

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);

        verify(userRepo).findByEmail("test@gmail.com");
        verify(userRepo, never()).save(any());
    }
}
