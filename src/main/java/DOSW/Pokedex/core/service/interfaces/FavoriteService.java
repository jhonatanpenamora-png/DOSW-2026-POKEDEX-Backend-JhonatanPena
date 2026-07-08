package DOSW.Pokedex.core.service.interfaces;

import DOSW.Pokedex.core.model.Favorite;

import java.util.List;

public interface FavoriteService {
    List<Favorite> findMyFavorites(Long userId);
    Favorite addFavorite(Long userId, Long pokemonId);
    void removeFavorite(Long userId, Long pokemonId);
}
