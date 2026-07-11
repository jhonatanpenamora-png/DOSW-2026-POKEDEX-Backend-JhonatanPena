package DOSW.Pokedex.controller;

import DOSW.Pokedex.AbstractIntegrationTest;
import DOSW.Pokedex.dto.response.PokemonResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link PokemonController} using real HTTP via
 * {@link TestRestTemplate}.
 * <p>
 * We must override {@code webEnvironment = RANDOM_PORT} because
 * {@link AbstractIntegrationTest} defaults to {@code NONE} (needed so
 * {@code @Transactional} works in other test classes).
 * </p>
 * <p>
 * <strong>URL note:</strong> {@code TestRestTemplate} automatically prepends
 * the context path ({@code /api}) when using {@code RANDOM_PORT}, so test
 * URLs omit the context path (e.g. {@code /v1/pokemon}).
 * </p>
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/test-data-pokemon.sql", executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
@DisplayName("PokemonController — Integration Tests (HTTP real)")
class PokemonControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("GET /api/v1/pokemon returns 200 with paginated response")
    void getAllPokemon_returnsPage() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/v1/pokemon?page=0&size=10", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains("content");
        assertThat(response.getBody()).contains("totalElements");
    }

    @Test
    @DisplayName("GET /api/v1/pokemon/{id} returns 200 with Pokemon JSON")
    void getPokemonById_existingId_returnsPokemon() {
        ResponseEntity<PokemonResponse> response = restTemplate.getForEntity(
                "/v1/pokemon/1", PokemonResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().name()).isEqualTo("Pikachu");
        assertThat(response.getBody().nationalNumber()).isEqualTo(25);
        assertThat(response.getBody().region()).isEqualTo("Kanto");
        assertThat(response.getBody().types()).contains("Electric");
    }

    @Test
    @DisplayName("GET /api/v1/pokemon/{id} returns 404 for non-existent id")
    void getPokemonById_nonExistentId_returns404() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/v1/pokemon/99999", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("GET /api/v1/pokemon?page=0&size=1 returns single element")
    void getAllPokemon_pageSizeOne_returnsOneElement() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/v1/pokemon?page=0&size=1", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("\"size\":1");
        assertThat(response.getBody()).contains("\"numberOfElements\":1");
    }
}
