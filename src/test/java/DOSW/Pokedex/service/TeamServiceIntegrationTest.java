package DOSW.Pokedex.service;

import DOSW.Pokedex.AbstractIntegrationTest;
import DOSW.Pokedex.exception.ResourceNotFoundException;
import DOSW.Pokedex.exception.UnauthorizedException;
import DOSW.Pokedex.model.Team;
import DOSW.Pokedex.model.TeamEntity;
import DOSW.Pokedex.repository.TeamJpaRepository;
import DOSW.Pokedex.repository.UserJpaRepository;
import DOSW.Pokedex.model.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("TeamService — Integration Tests")
class TeamServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamJpaRepository teamRepo;

    @Autowired
    private UserJpaRepository userRepo;

    private Long userId;
    private Long otherUserId;

    @BeforeEach
    void setUp() {
        userId = 1L;
        otherUserId = 2L;

        // Ensure user exists (the H2 database is shared across test classes,
        // so test-data-pokemon.sql from another class may or may not have run)
        if (userRepo.findById(userId).isEmpty()) {
            userRepo.save(UserEntity.builder()
                    .id(1L)
                    .username("trainer1")
                    .email("trainer1@gmail.com")
                    .role("USER")
                    .provider("google")
                    .providerId("google-123")
                    .enabled(true)
                    .build());
        }
        if (userRepo.findById(otherUserId).isEmpty()) {
            userRepo.save(UserEntity.builder()
                    .id(2L)
                    .username("admin1")
                    .email("admin1@gmail.com")
                    .role("ADMIN")
                    .provider("google")
                    .providerId("google-456")
                    .enabled(true)
                    .build());
        }

        // Clean teams from previous tests
        teamRepo.deleteAll();

        // Create a team directly via repository for testing
        UserEntity user = userRepo.findById(userId).orElseThrow();
        TeamEntity team = TeamEntity.builder()
                .user(user)
                .name("Test Team")
                .build();
        teamRepo.save(team);
    }

    @Test
    @DisplayName("findMyTeams returns teams for the authenticated user")
    void findMyTeams_existingUser_returnsTeams() {
        List<Team> teams = teamService.findMyTeams(userId);

        assertThat(teams).isNotEmpty();
        assertThat(teams.get(0).getName()).isEqualTo("Test Team");
    }

    @Test
    @DisplayName("findById returns team when owned by the requesting user")
    void findById_ownTeam_returnsTeam() {
        List<Team> teams = teamService.findMyTeams(userId);
        Long teamId = teams.get(0).getId();

        Team result = teamService.findById(teamId, userId);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Test Team");
    }

    @Test
    @DisplayName("findById throws UnauthorizedException when accessing other user's team")
    void findById_otherUsersTeam_throwsUnauthorized() {
        List<Team> teams = teamService.findMyTeams(userId);
        Long teamId = teams.get(0).getId();

        assertThatThrownBy(() -> teamService.findById(teamId, otherUserId))
                .isInstanceOf(UnauthorizedException.class);
    }

    @Test
    @DisplayName("delete removes team when owned by the requesting user")
    void delete_ownTeam_removesTeam() {
        List<Team> teams = teamService.findMyTeams(userId);
        Long teamId = teams.get(0).getId();

        teamService.delete(teamId, userId);

        assertThatThrownBy(() -> teamService.findById(teamId, userId))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("delete throws UnauthorizedException for other user's team")
    void delete_otherUsersTeam_throwsUnauthorized() {
        List<Team> teams = teamService.findMyTeams(userId);
        Long teamId = teams.get(0).getId();

        assertThatThrownBy(() -> teamService.delete(teamId, otherUserId))
                .isInstanceOf(UnauthorizedException.class);
    }

    @Test
    @DisplayName("delete throws ResourceNotFoundException for non-existent team")
    void delete_nonExistentTeam_throwsNotFound() {
        assertThatThrownBy(() -> teamService.delete(9999L, userId))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
