package DOSW.Pokedex.service;

import DOSW.Pokedex.TestFixtures;
import DOSW.Pokedex.exception.DuplicateResourceException;
import DOSW.Pokedex.exception.ResourceNotFoundException;
import DOSW.Pokedex.model.Pokemon;
import DOSW.Pokedex.model.PokemonEntity;
import DOSW.Pokedex.model.RegionEntity;
import DOSW.Pokedex.model.TypeEntity;
import DOSW.Pokedex.repository.PokemonJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PokemonServiceTest {

    @Mock
    private PokemonJpaRepository pokemonRepo;

    @InjectMocks
    private PokemonService service;

    private Pokemon pikachu;
    private PokemonEntity pikachuEntity;

    @BeforeEach
    void setUp() {
        pikachu = TestFixtures.createPikachu();
        pikachuEntity = PokemonEntity.builder()
                .id(1L)
                .nationalNumber(25)
                .name("Pikachu")
                .description("When it is angry, it immediately discharges the energy stored in the pouches in its cheeks.")
                .imageUrl("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png")
                .height(4)
                .weight(60)
                .baseExperience(112)
                .generation(1)
                .hasMega(false)
                .rarityLevel("COMUN")
                .region(RegionEntity.builder().name("Kanto").build())
                .types(List.of(TypeEntity.builder().name("Electric").build()))
                .build();
    }

    @Test
    @DisplayName("findById debe retornar Pokemon cuando existe")
    void findById_whenExists_returnsPokemon() {
        when(pokemonRepo.findById(1L)).thenReturn(Optional.of(pikachuEntity));

        Pokemon result = service.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Pikachu");
        assertThat(result.getNationalNumber()).isEqualTo(25);
        assertThat(result.getTypes()).contains("Electric");
        verify(pokemonRepo).findById(1L);
    }

    @Test
    @DisplayName("findById debe lanzar ResourceNotFoundException cuando no existe")
    void findById_whenNotFound_throwsException() {
        when(pokemonRepo.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Pokemon")
                .hasMessageContaining("99");

        verify(pokemonRepo).findById(99L);
    }

    @Test
    @DisplayName("create debe guardar y retornar Pokemon cuando no hay duplicado")
    void create_whenNotDuplicate_savesAndReturns() {
        when(pokemonRepo.existsByNationalNumber(25)).thenReturn(false);
        when(pokemonRepo.save(any(PokemonEntity.class))).thenReturn(pikachuEntity);

        Pokemon result = service.create(pikachu);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Pikachu");
        verify(pokemonRepo).existsByNationalNumber(25);
        verify(pokemonRepo).save(any(PokemonEntity.class));
    }

    @Test
    @DisplayName("create debe lanzar DuplicateResourceException cuando el número ya existe")
    void create_whenDuplicate_throwsException() {
        when(pokemonRepo.existsByNationalNumber(25)).thenReturn(true);

        assertThatThrownBy(() -> service.create(pikachu))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("25");

        verify(pokemonRepo).existsByNationalNumber(25);
        verify(pokemonRepo, never()).save(any());
    }

    @Test
    @DisplayName("delete debe lanzar excepción cuando el Pokemon no existe")
    void delete_whenNotFound_throwsException() {
        when(pokemonRepo.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(99L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(pokemonRepo, never()).deleteById(any());
    }

    @Test
    @DisplayName("delete debe eliminar cuando el Pokemon existe")
    void delete_whenExists_deletes() {
        when(pokemonRepo.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(pokemonRepo).deleteById(1L);
    }

    @Test
    @DisplayName("update debe lanzar excepción cuando el Pokemon no existe")
    void update_whenNotFound_throwsException() {
        when(pokemonRepo.findById(99L)).thenReturn(Optional.empty());

        Pokemon update = TestFixtures.createPikachu();

        assertThatThrownBy(() -> service.update(99L, update))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(pokemonRepo, never()).save(any());
    }
}
