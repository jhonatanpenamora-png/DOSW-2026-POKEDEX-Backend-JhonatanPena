package DOSW.Pokedex.core.service.interfaces;

import DOSW.Pokedex.core.model.Favorite;

import java.util.List;

public interface FavoritePersistencePort {
    List<Favorite> findByUserId(Long userId);
    Favorite save(Favorite favorite);
    void deleteByUserIdAndPokemonId(Long userId, Long pokemonId);
    boolean existsByUserIdAndPokemonId(Long userId, Long pokemonId);
}
