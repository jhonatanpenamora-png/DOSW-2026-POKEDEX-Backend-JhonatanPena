package DOSW.Pokedex.controller;

import DOSW.Pokedex.TestFixtures;
import DOSW.Pokedex.dto.response.PokemonResponse;
import DOSW.Pokedex.mapper.PokemonDtoMapper;
import DOSW.Pokedex.model.Pokemon;
import DOSW.Pokedex.service.AuthService;
import DOSW.Pokedex.service.PokemonService;
import DOSW.Pokedex.repository.UserJpaRepository;
import DOSW.Pokedex.security.JwtService;
import DOSW.Pokedex.security.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = PokemonController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class, OAuth2ClientAutoConfiguration.class })
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PokemonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PokemonService pokemonService;

    @MockBean
    private PokemonDtoMapper mapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserJpaRepository userPersistencePort;

    @MockBean
    private AuthService authService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Test
    @DisplayName("GET /v1/pokemon/{id} debe retornar 200 cuando existe")
    void findById_returns200() throws Exception {
        Pokemon pikachu = TestFixtures.createPikachu();
        PokemonResponse response = new PokemonResponse(
                1L, 25, "Pikachu", null, null, null, null, null,
                1, false, null, "Kanto",
                List.of("Electric"), null, null, null, null
        );

        when(pokemonService.findById(1L)).thenReturn(pikachu);
        when(mapper.toResponse(pikachu)).thenReturn(response);

        mockMvc.perform(get("/v1/pokemon/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pikachu"))
                .andExpect(jsonPath("$.nationalNumber").value(25));
    }

    @Test
    @DisplayName("GET /v1/pokemon debe retornar lista paginada")
    void findAll_returnsPage() throws Exception {
        Pokemon pikachu = TestFixtures.createPikachu();
        PokemonResponse response = new PokemonResponse(
                1L, 25, "Pikachu", null, null, null, null, null,
                1, false, null, null,
                List.of("Electric"), null, null, null, null
        );

        when(pokemonService.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(pikachu)));
        when(mapper.toResponse(pikachu)).thenReturn(response);

        mockMvc.perform(get("/v1/pokemon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Pikachu"));
    }
}
