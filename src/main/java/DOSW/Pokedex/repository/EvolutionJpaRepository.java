package DOSW.Pokedex.repository;

import DOSW.Pokedex.model.EvolutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvolutionJpaRepository extends JpaRepository<EvolutionEntity, Long> {

    List<EvolutionEntity> findByPokemonId(Long pokemonId);

    List<EvolutionEntity> findByEvolvesToId(Long pokemonId);
}
