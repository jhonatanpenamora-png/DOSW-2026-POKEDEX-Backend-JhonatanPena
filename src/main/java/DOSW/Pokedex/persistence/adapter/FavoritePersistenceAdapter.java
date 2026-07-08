package DOSW.Pokedex.persistence.adapter;

import DOSW.Pokedex.core.model.Favorite;
import DOSW.Pokedex.core.service.interfaces.FavoritePersistencePort;
import DOSW.Pokedex.persistence.entity.relational.FavoriteEntity;
import DOSW.Pokedex.persistence.entity.relational.PokemonEntity;
import DOSW.Pokedex.persistence.entity.relational.UserEntity;
import DOSW.Pokedex.persistence.repository.relational.FavoriteJpaRepository;
import DOSW.Pokedex.persistence.repository.relational.PokemonJpaRepository;
import DOSW.Pokedex.persistence.repository.relational.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FavoritePersistenceAdapter implements FavoritePersistencePort {

    private final FavoriteJpaRepository repository;
    private final UserJpaRepository userRepository;
    private final PokemonJpaRepository pokemonRepository;

    @Override
    public List<Favorite> findByUserId(Long userId) {
        return repository.findByUserId(userId).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Favorite save(Favorite favorite) {
        UserEntity user = userRepository.getReferenceById(favorite.getUserId());
        PokemonEntity pokemon = pokemonRepository.getReferenceById(favorite.getPokemonId());

        FavoriteEntity entity = FavoriteEntity.builder()
                .user(user)
                .pokemon(pokemon)
                .build();

        return toDomain(repository.save(entity));
    }

    @Override
    public void deleteByUserIdAndPokemonId(Long userId, Long pokemonId) {
        repository.deleteByUserIdAndPokemonId(userId, pokemonId);
    }

    @Override
    public boolean existsByUserIdAndPokemonId(Long userId, Long pokemonId) {
        return repository.existsByUserIdAndPokemonId(userId, pokemonId);
    }

    private Favorite toDomain(FavoriteEntity entity) {
        return Favorite.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .pokemonId(entity.getPokemon().getId())
                .pokemonName(entity.getPokemon().getName())
                .pokemonImage(entity.getPokemon().getImageUrl())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
