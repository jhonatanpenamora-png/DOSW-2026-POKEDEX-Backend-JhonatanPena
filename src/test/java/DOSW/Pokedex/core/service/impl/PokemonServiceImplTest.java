package DOSW.Pokedex.core.service.impl;

import DOSW.Pokedex.TestFixtures;
import DOSW.Pokedex.core.exception.DuplicateResourceException;
import DOSW.Pokedex.core.exception.ResourceNotFoundException;
import DOSW.Pokedex.core.model.Pokemon;
import DOSW.Pokedex.core.service.interfaces.PokemonPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PokemonServiceImplTest {

    @Mock
    private PokemonPersistencePort pokemonPort;

    @InjectMocks
    private PokemonServiceImpl service;

    private Pokemon pikachu;

    @BeforeEach
    void setUp() {
        pikachu = TestFixtures.createPikachu();
    }

    @Test
    @DisplayName("findById debe retornar Pokemon cuando existe")
    void findById_whenExists_returnsPokemon() {
        when(pokemonPort.findById(1L)).thenReturn(Optional.of(pikachu));

        Pokemon result = service.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Pikachu");
        assertThat(result.getNationalNumber()).isEqualTo(25);
        assertThat(result.getTypes()).contains("Electric");
        verify(pokemonPort).findById(1L);
    }

    @Test
    @DisplayName("findById debe lanzar ResourceNotFoundException cuando no existe")
    void findById_whenNotFound_throwsException() {
        when(pokemonPort.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Pokemon")
                .hasMessageContaining("99");

        verify(pokemonPort).findById(99L);
    }

    @Test
    @DisplayName("create debe guardar y retornar Pokemon cuando no hay duplicado")
    void create_whenNotDuplicate_savesAndReturns() {
        when(pokemonPort.existsByNationalNumber(25)).thenReturn(false);
        when(pokemonPort.save(pikachu)).thenReturn(pikachu);

        Pokemon result = service.create(pikachu);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Pikachu");
        verify(pokemonPort).existsByNationalNumber(25);
        verify(pokemonPort).save(pikachu);
    }

    @Test
    @DisplayName("create debe lanzar DuplicateResourceException cuando el número ya existe")
    void create_whenDuplicate_throwsException() {
        when(pokemonPort.existsByNationalNumber(25)).thenReturn(true);

        assertThatThrownBy(() -> service.create(pikachu))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("25");

        verify(pokemonPort).existsByNationalNumber(25);
        verify(pokemonPort, never()).save(any());
    }

    @Test
    @DisplayName("delete debe lanzar excepción cuando el Pokemon no existe")
    void delete_whenNotFound_throwsException() {
        when(pokemonPort.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(pokemonPort, never()).deleteById(any());
    }

    @Test
    @DisplayName("delete debe eliminar cuando el Pokemon existe")
    void delete_whenExists_deletes() {
        when(pokemonPort.findById(1L)).thenReturn(Optional.of(pikachu));

        service.delete(1L);

        verify(pokemonPort).deleteById(1L);
    }

    @Test
    @DisplayName("update debe lanzar excepción cuando el Pokemon no existe")
    void update_whenNotFound_throwsException() {
        when(pokemonPort.findById(99L)).thenReturn(Optional.empty());

        Pokemon update = TestFixtures.createPikachu();

        assertThatThrownBy(() -> service.update(99L, update))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(pokemonPort, never()).save(any());
    }
}
