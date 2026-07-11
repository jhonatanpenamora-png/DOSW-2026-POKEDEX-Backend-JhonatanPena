package DOSW.Pokedex.service;

import DOSW.Pokedex.TestFixtures;
import DOSW.Pokedex.exception.ResourceNotFoundException;
import DOSW.Pokedex.model.*;
import DOSW.Pokedex.repository.TeamJpaRepository;
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
class TeamServiceTest {

    @Mock
    private TeamJpaRepository teamRepo;

    @InjectMocks
    private TeamService service;

    private Team team;
    private TeamEntity teamEntity;

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

        UserEntity user = UserEntity.builder().id(1L).build();
        PokemonEntity pikachuEntity = PokemonEntity.builder().id(25L).name("Pikachu").build();

        teamEntity = TeamEntity.builder()
                .id(1L)
                .user(user)
                .name("Dream Team")
                .pokemon(List.of(
                        TeamPokemonEntity.builder()
                                .id(1L)
                                .pokemon(pikachuEntity)
                                .slotPosition(1)
                                .build()
                ))
                .build();
    }

    @Test
    @DisplayName("findById debe retornar equipo cuando pertenece al usuario")
    void findById_whenOwned_returnsTeam() {
        when(teamRepo.findById(1L)).thenReturn(Optional.of(teamEntity));

        Team result = service.findById(1L, 1L);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Dream Team");
    }

    @Test
    @DisplayName("findById debe lanzar excepción cuando NO pertenece al usuario")
    void findById_whenNotOwned_throwsException() {
        when(teamRepo.findById(1L)).thenReturn(Optional.of(teamEntity));

        assertThatThrownBy(() -> service.findById(1L, 2L))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("create debe guardar equipo")
    void create_savesTeam() {
        when(teamRepo.save(any(TeamEntity.class))).thenReturn(teamEntity);

        Team result = service.create(team, 1L);

        assertThat(result).isNotNull();
        verify(teamRepo).save(any(TeamEntity.class));
    }
}
