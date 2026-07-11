package DOSW.Pokedex.controller;

import DOSW.Pokedex.dto.request.TeamRequest;
import DOSW.Pokedex.dto.response.TeamPokemonResponse;
import DOSW.Pokedex.dto.response.TeamResponse;
import DOSW.Pokedex.mapper.TeamDtoMapper;
import DOSW.Pokedex.model.Team;
import DOSW.Pokedex.model.TeamPokemon;
import DOSW.Pokedex.service.AuthService;
import DOSW.Pokedex.service.TeamService;
import DOSW.Pokedex.repository.UserJpaRepository;
import DOSW.Pokedex.security.JwtService;
import DOSW.Pokedex.security.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = TeamController.class, excludeAutoConfiguration = {
        SecurityAutoConfiguration.class,
        SecurityFilterAutoConfiguration.class,
        OAuth2ClientAutoConfiguration.class
})
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TeamService teamService;

    @MockBean
    private TeamDtoMapper mapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserJpaRepository userPersistencePort;

    @MockBean
    private AuthService authService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    private TeamResponse createTeamResponse(Long id, String name) {
        return new TeamResponse(id, name, List.of(
                new TeamPokemonResponse(1L, 25L, "Pikachu", "https://example.com/pikachu.png", 1)
        ));
    }

    private Team createTeam(Long id, String name) {
        return Team.builder()
                .id(id)
                .userId(1L)
                .name(name)
                .pokemon(List.of(
                        TeamPokemon.builder()
                                .id(1L).pokemonId(25L)
                                .pokemonName("Pikachu")
                                .pokemonImage("https://example.com/pikachu.png")
                                .slotPosition(1)
                                .build()
                ))
                .build();
    }

    @Test
    @DisplayName("GET /v1/teams debe retornar 200 con lista de equipos")
    void findMyTeams_returns200() throws Exception {
        Team team = createTeam(1L, "Dream Team");
        TeamResponse response = createTeamResponse(1L, "Dream Team");

        when(teamService.findMyTeams(1L)).thenReturn(List.of(team));
        when(mapper.toResponse(team)).thenReturn(response);

        mockMvc.perform(get("/v1/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Dream Team"))
                .andExpect(jsonPath("$[0].id").value(1));

        verify(teamService).findMyTeams(1L);
    }

    @Test
    @DisplayName("GET /v1/teams/{id} debe retornar 200 con el equipo")
    void testFindById_returns200() throws Exception {
        Team team = createTeam(1L, "Dream Team");
        TeamResponse response = createTeamResponse(1L, "Dream Team");

        when(teamService.findById(1L, 1L)).thenReturn(team);
        when(mapper.toResponse(team)).thenReturn(response);

        mockMvc.perform(get("/v1/teams/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dream Team"));

        verify(teamService).findById(1L, 1L);
    }

    @Test
    @DisplayName("POST /v1/teams debe retornar 201 con location header")
    void testCreate_returns201() throws Exception {
        TeamRequest request = new TeamRequest("Dream Team", List.of(25L));
        Team team = createTeam(null, "Dream Team");
        Team savedTeam = createTeam(1L, "Dream Team");
        TeamResponse response = createTeamResponse(1L, "Dream Team");

        when(mapper.toDomain(any(TeamRequest.class))).thenReturn(team);
        when(teamService.create(team, 1L)).thenReturn(savedTeam);
        when(mapper.toResponse(savedTeam)).thenReturn(response);

        mockMvc.perform(post("/v1/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.name").value("Dream Team"));

        verify(teamService).create(team, 1L);
    }

    @Test
    @DisplayName("PUT /v1/teams/{id} debe retornar 200 con equipo actualizado")
    void testUpdate_returns200() throws Exception {
        TeamRequest request = new TeamRequest("Updated Team", List.of(25L, 6L));
        Team teamDomain = createTeam(null, "Updated Team");
        Team updatedTeam = createTeam(1L, "Updated Team");
        TeamResponse response = createTeamResponse(1L, "Updated Team");

        when(mapper.toDomain(any(TeamRequest.class))).thenReturn(teamDomain);
        when(teamService.update(eq(1L), eq(teamDomain), eq(1L))).thenReturn(updatedTeam);
        when(mapper.toResponse(updatedTeam)).thenReturn(response);

        mockMvc.perform(put("/v1/teams/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Team"));

        verify(teamService).update(1L, teamDomain, 1L);
    }

    @Test
    @DisplayName("DELETE /v1/teams/{id} debe retornar 204")
    void testDelete_returns204() throws Exception {
        mockMvc.perform(delete("/v1/teams/1"))
                .andExpect(status().isNoContent());

        verify(teamService).delete(1L, 1L);
    }
}
