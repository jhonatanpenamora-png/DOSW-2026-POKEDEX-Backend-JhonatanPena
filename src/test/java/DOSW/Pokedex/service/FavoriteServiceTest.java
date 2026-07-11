package DOSW.Pokedex.service;

import DOSW.Pokedex.exception.DuplicateResourceException;
import DOSW.Pokedex.exception.ResourceNotFoundException;
import DOSW.Pokedex.model.*;
import DOSW.Pokedex.repository.FavoriteJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceTest {

    @Mock
    private FavoriteJpaRepository favoriteRepo;

    @InjectMocks
    private FavoriteService service;

    private FavoriteEntity pikachuFavoriteEntity;

    @BeforeEach
    void setUp() {
        UserEntity user = UserEntity.builder().id(1L).build();
        PokemonEntity pikachu = PokemonEntity.builder().id(25L).name("Pikachu").build();

        pikachuFavoriteEntity = FavoriteEntity.builder()
                .id(1L)
                .user(user)
                .pokemon(pikachu)
                .build();
    }

    @Test
    @DisplayName("findMyFavorites debe retornar lista de favoritos del usuario")
    void findMyFavorites_returnsList() {
        when(favoriteRepo.findByUserId(1L)).thenReturn(List.of(pikachuFavoriteEntity));

        List<Favorite> result = service.findMyFavorites(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPokemonName()).isEqualTo("Pikachu");
        assertThat(result.get(0).getUserId()).isEqualTo(1L);

        verify(favoriteRepo).findByUserId(1L);
    }

    @Test
    @DisplayName("addFavorite debe guardar y retornar el favorito cuando no existe duplicado")
    void addFavorite_whenNotDuplicate_savesAndReturns() {
        when(favoriteRepo.existsByUserIdAndPokemonId(1L, 25L)).thenReturn(false);
        when(favoriteRepo.save(any(FavoriteEntity.class))).thenReturn(pikachuFavoriteEntity);

        Favorite result = service.addFavorite(1L, 25L);

        assertThat(result).isNotNull();
        assertThat(result.getPokemonId()).isEqualTo(25L);
        assertThat(result.getUserId()).isEqualTo(1L);

        verify(favoriteRepo).existsByUserIdAndPokemonId(1L, 25L);
        verify(favoriteRepo).save(any(FavoriteEntity.class));
    }

    @Test
    @DisplayName("addFavorite debe lanzar DuplicateResourceException cuando ya existe")
    void addFavorite_whenDuplicate_throwsException() {
        when(favoriteRepo.existsByUserIdAndPokemonId(1L, 25L)).thenReturn(true);

        assertThatThrownBy(() -> service.addFavorite(1L, 25L))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("Favorite")
                .hasMessageContaining("25");

        verify(favoriteRepo).existsByUserIdAndPokemonId(1L, 25L);
        verify(favoriteRepo, never()).save(any());
    }

    @Test
    @DisplayName("removeFavorite debe eliminar cuando existe el favorito")
    void removeFavorite_whenExists_deletes() {
        when(favoriteRepo.existsByUserIdAndPokemonId(1L, 25L)).thenReturn(true);

        service.removeFavorite(1L, 25L);

        verify(favoriteRepo).existsByUserIdAndPokemonId(1L, 25L);
        verify(favoriteRepo).deleteByUserIdAndPokemonId(1L, 25L);
    }

    @Test
    @DisplayName("removeFavorite debe lanzar ResourceNotFoundException cuando no existe")
    void removeFavorite_whenNotFound_throwsException() {
        when(favoriteRepo.existsByUserIdAndPokemonId(1L, 99L)).thenReturn(false);

        assertThatThrownBy(() -> service.removeFavorite(1L, 99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Favorite")
                .hasMessageContaining("99");

        verify(favoriteRepo).existsByUserIdAndPokemonId(1L, 99L);
        verify(favoriteRepo, never()).deleteByUserIdAndPokemonId(any(), any());
    }
}
