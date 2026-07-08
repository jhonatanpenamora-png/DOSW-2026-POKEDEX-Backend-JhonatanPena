package DOSW.Pokedex.persistence.repository.relational;

import DOSW.Pokedex.persistence.entity.relational.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteJpaRepository extends JpaRepository<FavoriteEntity, Long> {

    List<FavoriteEntity> findByUserId(Long userId);

    boolean existsByUserIdAndPokemonId(Long userId, Long pokemonId);

    void deleteByUserIdAndPokemonId(Long userId, Long pokemonId);
}
