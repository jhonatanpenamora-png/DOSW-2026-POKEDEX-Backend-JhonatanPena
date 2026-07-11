package DOSW.Pokedex.controller;

import DOSW.Pokedex.dto.response.FavoriteResponse;
import DOSW.Pokedex.model.Favorite;
import DOSW.Pokedex.service.AuthService;
import DOSW.Pokedex.service.FavoriteService;
import DOSW.Pokedex.repository.UserJpaRepository;
import DOSW.Pokedex.security.JwtService;
import DOSW.Pokedex.security.UserDetailsServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = FavoriteController.class, excludeAutoConfiguration = {
        SecurityAutoConfiguration.class,
        SecurityFilterAutoConfiguration.class,
        OAuth2ClientAutoConfiguration.class
})
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FavoriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FavoriteService favoriteService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserJpaRepository userPersistencePort;

    @MockBean
    private AuthService authService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    private Favorite createFavorite(Long id, Long pokemonId, String name, LocalDateTime createdAt) {
        return Favorite.builder()
                .id(id)
                .userId(1L)
                .pokemonId(pokemonId)
                .pokemonName(name)
                .pokemonImage("https://example.com/" + name.toLowerCase() + ".png")
                .createdAt(createdAt)
                .build();
    }

    @Test
    @DisplayName("GET /v1/favorites debe retornar 200 con lista de favoritos")
    void testFindMyFavorites_returns200() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        Favorite favorite = createFavorite(1L, 25L, "Pikachu", now);

        when(favoriteService.findMyFavorites(1L)).thenReturn(List.of(favorite));

        mockMvc.perform(get("/v1/favorites"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].pokemonId").value(25))
                .andExpect(jsonPath("$[0].pokemonName").value("Pikachu"));

        verify(favoriteService).findMyFavorites(1L);
    }

    @Test
    @DisplayName("POST /v1/favorites/{pokemonId} debe retornar 200 con el favorito creado")
    void testAddFavorite_returns200() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        Favorite favorite = createFavorite(1L, 25L, "Pikachu", now);

        when(favoriteService.addFavorite(1L, 25L)).thenReturn(favorite);

        mockMvc.perform(post("/v1/favorites/25"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.pokemonId").value(25))
                .andExpect(jsonPath("$.pokemonName").value("Pikachu"));

        verify(favoriteService).addFavorite(1L, 25L);
    }

    @Test
    @DisplayName("DELETE /v1/favorites/{pokemonId} debe retornar 204")
    void testRemoveFavorite_returns204() throws Exception {
        mockMvc.perform(delete("/v1/favorites/25"))
                .andExpect(status().isNoContent());

        verify(favoriteService).removeFavorite(1L, 25L);
    }
}
