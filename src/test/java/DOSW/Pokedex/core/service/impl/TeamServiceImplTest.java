package DOSW.Pokedex.core.service.impl;

import DOSW.Pokedex.TestFixtures;
import DOSW.Pokedex.core.exception.ResourceNotFoundException;
import DOSW.Pokedex.core.model.Team;
import DOSW.Pokedex.core.model.TeamPokemon;
import DOSW.Pokedex.core.service.interfaces.TeamPersistencePort;
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
class TeamServiceImplTest {

    @Mock
    private TeamPersistencePort teamPort;

    @InjectMocks
    private TeamServiceImpl service;

    private Team team;

    @BeforeEach
    void setUp() {
        team = Team.builder()
                .id(1L)
                .userId(1L)
                .name("Dream Team")
                .pokemon(List.of(
                        TeamPokemon.builder()
                                .id(1L).pokemonId(25L)
                                .pokemonName("Pikachu").slotPosition(1)
                                .build()
                ))
                .build();
    }

    @Test
    @DisplayName("findById debe retornar equipo cuando pertenece al usuario")
    void findById_whenOwned_returnsTeam() {
        when(teamPort.findById(1L)).thenReturn(Optional.of(team));

        Team result = service.findById(1L, 1L);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Dream Team");
    }

    @Test
    @DisplayName("findById debe lanzar excepción cuando NO pertenece al usuario")
    void findById_whenNotOwned_throwsException() {
        when(teamPort.findById(1L)).thenReturn(Optional.of(team));

        assertThatThrownBy(() -> service.findById(1L, 2L))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("create debe guardar equipo")
    void create_savesTeam() {
        when(teamPort.save(team)).thenReturn(team);

        Team result = service.create(team, 1L);

        assertThat(result).isNotNull();
        verify(teamPort).save(team);
    }
}
