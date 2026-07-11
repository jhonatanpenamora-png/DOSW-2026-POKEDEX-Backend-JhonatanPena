package DOSW.Pokedex.service;

import DOSW.Pokedex.AbstractIntegrationTest;
import DOSW.Pokedex.dto.request.PokemonRequest;
import DOSW.Pokedex.dto.request.PokemonStatsRequest;
import DOSW.Pokedex.exception.DuplicateResourceException;
import DOSW.Pokedex.exception.ResourceNotFoundException;
import DOSW.Pokedex.mapper.PokemonDtoMapper;
import DOSW.Pokedex.model.Pokemon;
import DOSW.Pokedex.model.PokemonFilterCriteria;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Sql(scripts = "/test-data-pokemon.sql", executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
@DisplayName("PokemonService — Integration Tests (PostgreSQL real)")
class PokemonServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private PokemonDtoMapper pokemonMapper;

    @Test
    @DisplayName("findById returns Pokemon with full data for existing id")
    void findById_existingId_returnsFullPokemon() {
        Pokemon result = pokemonService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getNationalNumber()).isEqualTo(25);
        assertThat(result.getName()).isEqualTo("Pikachu");
        assertThat(result.getRegion()).isEqualTo("Kanto");
        assertThat(result.getTypes()).containsExactly("Electric");
        assertThat(result.getAbilities()).contains("Static", "Lightning Rod");
        assertThat(result.getMoves()).contains("Thunder Shock", "Quick Attack");
        assertThat(result.getEvolutionChain()).contains(26);
        assertThat(result.getStats()).isNotNull();
        assertThat(result.getStats().getHp()).isEqualTo(35);
        assertThat(result.getStats().getSpeed()).isEqualTo(90);
    }

    @Test
    @DisplayName("findById throws ResourceNotFoundException for non-existent id")
    void findById_nonExistentId_throwsNotFound() {
        assertThatThrownBy(() -> pokemonService.findById(9999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Pokemon");
    }

    @Test
    @DisplayName("findAll returns paginated results sorted by nationalNumber")
    void findAll_returnsPagedAndSorted() {
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by("nationalNumber").ascending());
        Page<Pokemon> page = pokemonService.findAll(pageRequest);

        assertThat(page.getContent()).hasSize(2);
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(3);
        assertThat(page.getTotalPages()).isGreaterThanOrEqualTo(2);
        // First page should start with lowest nationalNumber
        assertThat(page.getContent().get(0).getNationalNumber()).isLessThanOrEqualTo(
                page.getContent().get(1).getNationalNumber());
    }

    @Test
    @DisplayName("findByNationalNumber returns Pokemon for existing number")
    void findByNationalNumber_existingNumber_returnsPokemon() {
        Pokemon result = pokemonService.findByNationalNumber(25);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Pikachu");
    }

    @Test
    @DisplayName("findByNationalNumber throws for non-existent number")
    void findByNationalNumber_nonExistent_throwsNotFound() {
        assertThatThrownBy(() -> pokemonService.findByNationalNumber(9999))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("create persists a new Pokemon and returns it with generated id")
    void create_validPokemon_persistsAndReturns() {
        PokemonRequest request = new PokemonRequest(
                150, "Mewtwo", "A legendary Pokemon created by genetic engineering.",
                null, 20, 1220, 340, 1, false, "LEGENDARIO",
                null, null,
                new PokemonStatsRequest(106, 110, 90, 154, 90, 130), null);

        Pokemon domain = pokemonMapper.toDomain(request);
        Pokemon created = pokemonService.create(domain);

        assertThat(created.getId()).isNotNull();
        assertThat(created.getNationalNumber()).isEqualTo(150);
        assertThat(created.getName()).isEqualTo("Mewtwo");

        // Verify it persists
        Pokemon fetched = pokemonService.findById(created.getId());
        assertThat(fetched.getName()).isEqualTo("Mewtwo");
    }

    @Test
    @DisplayName("create with duplicate nationalNumber throws DuplicateResourceException")
    void create_duplicateNationalNumber_throwsDuplicate() {
        PokemonRequest request = new PokemonRequest(
                25, "Another Pikachu", "Duplicate", null,
                4, 60, 112, 1, false, "COMUN",
                null, null, null, null);

        Pokemon domain = pokemonMapper.toDomain(request);

        assertThatThrownBy(() -> pokemonService.create(domain))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("nationalNumber");
    }

    @Test
    @DisplayName("update modifies existing Pokemon fields")
    void update_existingId_updatesFields() {
        // Create a pokemon first
        PokemonRequest request = new PokemonRequest(
                888, "TestMon", "Original desc", null,
                10, 100, 100, 1, false, "COMUN",
                null, null,
                new PokemonStatsRequest(50, 50, 50, 50, 50, 50), null);

        Pokemon created = pokemonService.create(pokemonMapper.toDomain(request));

        // Update
        Pokemon updated = created.toBuilder()
                .name("TestMon Updated")
                .description("Updated description")
                .build();

        Pokemon result = pokemonService.update(created.getId(), updated);

        assertThat(result.getName()).isEqualTo("TestMon Updated");
        assertThat(result.getDescription()).isEqualTo("Updated description");

        // Verify persistence
        Pokemon fetched = pokemonService.findById(created.getId());
        assertThat(fetched.getName()).isEqualTo("TestMon Updated");
    }

    @Test
    @DisplayName("delete removes Pokemon and subsequent findById throws")
    void delete_existingId_removesPokemon() {
        // Create a pokemon to delete
        PokemonRequest request = new PokemonRequest(
                999, "DeleteMon", "To be deleted", null,
                5, 50, 50, 1, false, "COMUN",
                null, null, null, null);

        Pokemon created = pokemonService.create(pokemonMapper.toDomain(request));
        Long id = created.getId();

        pokemonService.delete(id);

        assertThatThrownBy(() -> pokemonService.findById(id))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("filterByCriteria returns matching Pokemon by generation")
    void filterByCriteria_generationFilter_returnsMatching() {
        PokemonFilterCriteria criteria = PokemonFilterCriteria.builder()
                .generation(1)
                .build();

        List<Pokemon> results = pokemonService.filterByCriteria(criteria);

        assertThat(results).isNotEmpty();
        assertThat(results).allMatch(p -> p.getGeneration() == 1);
    }

    @Test
    @DisplayName("filterByCriteria returns matching Pokemon by region")
    void filterByCriteria_regionFilter_returnsMatching() {
        PokemonFilterCriteria criteria = PokemonFilterCriteria.builder()
                .region("Kanto")
                .build();

        List<Pokemon> results = pokemonService.filterByCriteria(criteria);

        assertThat(results).isNotEmpty();
        assertThat(results).allMatch(p -> "Kanto".equals(p.getRegion()));
    }
}
