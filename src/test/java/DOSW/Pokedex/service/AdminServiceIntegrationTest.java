package DOSW.Pokedex.service;

import DOSW.Pokedex.AbstractIntegrationTest;
import DOSW.Pokedex.exception.ResourceNotFoundException;
import DOSW.Pokedex.model.User;
import DOSW.Pokedex.model.UserEntity;
import DOSW.Pokedex.repository.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@DisplayName("AdminService — Integration Tests")
class AdminServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserJpaRepository userRepo;

    @BeforeEach
    void setUp() {
        // Crear usuario de prueba (la H2 en memoria es compartida entre clases de test)
        if (userRepo.findByUsername("test-admin-user").isEmpty()) {
            userRepo.save(UserEntity.builder()
                    .username("test-admin-user")
                    .email("test-admin@pokedex.test")
                    .role("USER")
                    .provider("google")
                    .providerId("test-google-id")
                    .enabled(true)
                    .build());
        }
    }

    @Test
    @DisplayName("findAllUsers retorna todos los usuarios incluyendo el de prueba")
    void findAllUsers_returnsAllUsers() {
        List<User> users = adminService.findAllUsers();

        assertThat(users).isNotEmpty();
        assertThat(users).anyMatch(u -> "test-admin-user".equals(u.getUsername()));
    }

    @Test
    @DisplayName("updateUserRole cambia el rol del usuario existente")
    void updateUserRole_existingUser_updatesRole() {
        UserEntity entity = userRepo.findByUsername("test-admin-user").orElseThrow();
        Long userId = entity.getId();

        User updated = adminService.updateUserRole(userId, "ADMIN");

        assertThat(updated.getRole()).isEqualTo("ADMIN");
    }

    @Test
    @DisplayName("toggleUserEnabled desactiva y reactiva el usuario")
    void toggleUserEnabled_togglesEnabled() {
        UserEntity entity = userRepo.findByUsername("test-admin-user").orElseThrow();
        Long userId = entity.getId();

        // Desactivar
        adminService.toggleUserEnabled(userId);
        UserEntity afterDisable = userRepo.findById(userId).orElseThrow();
        assertThat(afterDisable.getEnabled()).isFalse();

        // Reactivar
        adminService.toggleUserEnabled(userId);
        UserEntity afterEnable = userRepo.findById(userId).orElseThrow();
        assertThat(afterEnable.getEnabled()).isTrue();
    }

    @Test
    @DisplayName("updateUserRole lanza ResourceNotFoundException para id inexistente")
    void updateUserRole_userNotFound_throwsException() {
        assertThatThrownBy(() -> adminService.updateUserRole(9999L, "ADMIN"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User");
    }

    @Test
    @DisplayName("toggleUserEnabled lanza ResourceNotFoundException para id inexistente")
    void toggleUserEnabled_userNotFound_throwsException() {
        assertThatThrownBy(() -> adminService.toggleUserEnabled(9999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User");
    }
}
