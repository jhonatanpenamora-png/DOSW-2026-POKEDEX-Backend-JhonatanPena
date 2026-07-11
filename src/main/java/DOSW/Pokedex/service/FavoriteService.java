package DOSW.Pokedex.service;

import DOSW.Pokedex.exception.DuplicateResourceException;
import DOSW.Pokedex.exception.ResourceNotFoundException;
import DOSW.Pokedex.model.Favorite;
import DOSW.Pokedex.model.FavoriteEntity;
import DOSW.Pokedex.repository.FavoriteJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteService {

    private final FavoriteJpaRepository favoriteRepo;

    @Transactional(readOnly = true)
    public List<Favorite> findMyFavorites(Long userId) {
        return favoriteRepo.findByUserId(userId).stream()
                .map(FavoriteService::toDomain)
                .toList();
    }

    @Transactional
    public Favorite addFavorite(Long userId, Long pokemonId) {
        if (favoriteRepo.existsByUserIdAndPokemonId(userId, pokemonId)) {
            throw new DuplicateResourceException("Favorite", "pokemonId", pokemonId);
        }

        FavoriteEntity entity = FavoriteEntity.builder()
                .build();
        FavoriteEntity saved = favoriteRepo.save(entity);
        return toDomain(saved);
    }

    @Transactional
    public void removeFavorite(Long userId, Long pokemonId) {
        if (!favoriteRepo.existsByUserIdAndPokemonId(userId, pokemonId)) {
            throw new ResourceNotFoundException("Favorite", "pokemonId", pokemonId);
        }
        favoriteRepo.deleteByUserIdAndPokemonId(userId, pokemonId);
    }

    private static Favorite toDomain(FavoriteEntity entity) {
        if (entity == null) return null;
        return Favorite.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .pokemonId(entity.getPokemon().getId())
                .pokemonName(entity.getPokemon().getName())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
