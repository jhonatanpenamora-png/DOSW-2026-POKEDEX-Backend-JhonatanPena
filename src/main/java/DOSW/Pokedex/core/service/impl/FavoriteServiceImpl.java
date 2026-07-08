package DOSW.Pokedex.core.service.impl;

import DOSW.Pokedex.core.exception.DuplicateResourceException;
import DOSW.Pokedex.core.exception.ResourceNotFoundException;
import DOSW.Pokedex.core.model.Favorite;
import DOSW.Pokedex.core.service.interfaces.FavoritePersistencePort;
import DOSW.Pokedex.core.service.interfaces.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoritePersistencePort favoritePort;

    @Override
    @Transactional(readOnly = true)
    public List<Favorite> findMyFavorites(Long userId) {
        return favoritePort.findByUserId(userId);
    }

    @Override
    @Transactional
    public Favorite addFavorite(Long userId, Long pokemonId) {
        if (favoritePort.existsByUserIdAndPokemonId(userId, pokemonId)) {
            throw new DuplicateResourceException("Favorite", "pokemonId", pokemonId);
        }

        Favorite favorite = Favorite.builder()
                .userId(userId)
                .pokemonId(pokemonId)
                .build();

        return favoritePort.save(favorite);
    }

    @Override
    @Transactional
    public void removeFavorite(Long userId, Long pokemonId) {
        if (!favoritePort.existsByUserIdAndPokemonId(userId, pokemonId)) {
            throw new ResourceNotFoundException("Favorite", "pokemonId", pokemonId);
        }
        favoritePort.deleteByUserIdAndPokemonId(userId, pokemonId);
    }
}
