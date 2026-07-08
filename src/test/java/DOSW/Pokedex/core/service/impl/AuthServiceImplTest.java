package DOSW.Pokedex.core.service.impl;

import DOSW.Pokedex.TestFixtures;
import DOSW.Pokedex.core.model.User;
import DOSW.Pokedex.core.service.interfaces.UserPersistencePort;
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
class AuthServiceImplTest {

    @Mock
    private UserPersistencePort userPort;

    @InjectMocks
    private AuthServiceImpl service;

    private User newUser;

    @BeforeEach
    void setUp() {
        newUser = User.builder()
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
        when(userPort.findByEmail("test@gmail.com")).thenReturn(Optional.empty());
        when(userPort.save(any(User.class))).thenAnswer(invocation -> {
            User saved = invocation.getArgument(0);
            return saved.toBuilder().id(1L).build();
        });

        User result = service.processOAuth2User("test@gmail.com", "Test", "https://example.com/avatar.png", "12345");

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("test@gmail.com");
        assertThat(result.getRole()).isEqualTo("USER");
        assertThat(result.getProvider()).isEqualTo("google");
        assertThat(result.getEnabled()).isTrue();

        verify(userPort).findByEmail("test@gmail.com");
        verify(userPort).save(any(User.class));
    }

    @Test
    @DisplayName("processOAuth2User debe retornar usuario existente")
    void processOAuth2User_whenExisting_returnsUser() {
        User existing = newUser.toBuilder().id(1L).build();
        when(userPort.findByEmail("test@gmail.com")).thenReturn(Optional.of(existing));

        User result = service.processOAuth2User("test@gmail.com", "Test", "https://example.com/avatar.png", "12345");

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);

        verify(userPort).findByEmail("test@gmail.com");
        verify(userPort, never()).save(any());
    }
}
