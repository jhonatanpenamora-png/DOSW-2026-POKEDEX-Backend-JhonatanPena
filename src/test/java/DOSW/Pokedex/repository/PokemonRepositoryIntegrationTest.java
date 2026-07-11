package DOSW.Pokedex.repository;

import DOSW.Pokedex.AbstractIntegrationTest;
import DOSW.Pokedex.model.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class PokemonRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private PokemonJpaRepository pokemonRepo;

    @Autowired
    private TypeJpaRepository typeRepo;

    @Autowired
    private RegionJpaRepository regionRepo;

    private RegionEntity region;
    private TypeEntity electricType;
    private TypeEntity fireType;
    private PokemonEntity pikachu;
    private PokemonEntity charizard;

    @BeforeEach
    void setUp() {
        region = regionRepo.save(RegionEntity.builder().name("Kanto").build());

        electricType = typeRepo.save(TypeEntity.builder().name("Electric").build());
        fireType = typeRepo.save(TypeEntity.builder().name("Fire").build());

        pikachu = pokemonRepo.save(PokemonEntity.builder()
                .nationalNumber(25)
                .name("Pikachu")
                .description("A test pokemon")
                .generation(1)
                .hasMega(false)
                .rarityLevel("COMUN")
                .region(region)
                .types(List.of(electricType))
                .build());

        charizard = pokemonRepo.save(PokemonEntity.builder()
                .nationalNumber(6)
                .name("Charizard")
                .description("A fire pokemon")
                .generation(1)
                .hasMega(true)
                .rarityLevel("COMUN")
                .region(region)
                .types(List.of(fireType))
                .build());
    }

    @Test
    @DisplayName("findById debe retornar PokemonEntity con tipos, región y relaciones cargadas")
    void findById_returnsEntityWithFullGraph() {
        Optional<PokemonEntity> found = pokemonRepo.findById(pikachu.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Pikachu");
        assertThat(found.get().getNationalNumber()).isEqualTo(25);
        assertThat(found.get().getRegion()).isNotNull();
        assertThat(found.get().getRegion().getName()).isEqualTo("Kanto");
        assertThat(found.get().getTypes())
                .isNotEmpty()
                .extracting(TypeEntity::getName)
                .contains("Electric");
        assertThat(found.get().getGeneration()).isEqualTo(1);
        assertThat(found.get().getHasMega()).isFalse();
    }

    @Test
    @DisplayName("findByNationalNumber debe retornar el Pokemon correcto")
    void findByNationalNumber_returnsCorrectPokemon() {
        Optional<PokemonEntity> found = pokemonRepo.findByNationalNumber(25);

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Pikachu");
    }

    @Test
    @DisplayName("findByNationalNumber debe retornar empty para número inexistente")
    void findByNationalNumber_whenNotFound_returnsEmpty() {
        Optional<PokemonEntity> found = pokemonRepo.findByNationalNumber(999);

        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("findAll con Specification debe filtrar por tipo")
    void findAll_withSpecification_filtersByType() {
        Specification<PokemonEntity> spec = (root, query, cb) -> {
            Join<PokemonEntity, TypeEntity> typeJoin = root.join("types", JoinType.INNER);
            return cb.equal(typeJoin.get("name"), "Electric");
        };

        List<PokemonEntity> result = pokemonRepo.findAll(spec);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo("Pikachu");
    }

    @Test
    @DisplayName("findAll con Pageable debe retornar resultados paginados")
    void findAll_withPageable_returnsPagedResults() {
        Pageable pageable = PageRequest.of(0, 1, Sort.by("nationalNumber").ascending());
        Page<PokemonEntity> page = pokemonRepo.findAll(pageable);

        assertThat(page).isNotNull();
        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getTotalElements()).isEqualTo(2);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.getContent().getFirst().getNationalNumber()).isEqualTo(6);
    }

    @Test
    @DisplayName("findAll con Pageable segunda página")
    void findAll_withPageable_secondPage() {
        Pageable pageable = PageRequest.of(1, 1, Sort.by("nationalNumber").ascending());
        Page<PokemonEntity> page = pokemonRepo.findAll(pageable);

        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getContent().getFirst().getNationalNumber()).isEqualTo(25);
    }
}
