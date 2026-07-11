package DOSW.Pokedex.repository;

import DOSW.Pokedex.model.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteJpaRepository extends JpaRepository<FavoriteEntity, Long> {

    List<FavoriteEntity> findByUserId(Long userId);

    boolean existsByUserIdAndPokemonId(Long userId, Long pokemonId);

    void deleteByUserIdAndPokemonId(Long userId, Long pokemonId);
}
