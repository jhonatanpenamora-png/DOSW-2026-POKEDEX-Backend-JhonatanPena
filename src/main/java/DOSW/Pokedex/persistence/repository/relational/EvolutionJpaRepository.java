package DOSW.Pokedex.persistence.repository.relational;

import DOSW.Pokedex.persistence.entity.relational.EvolutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvolutionJpaRepository extends JpaRepository<EvolutionEntity, Long> {

    List<EvolutionEntity> findByPokemonId(Long pokemonId);

    List<EvolutionEntity> findByEvolvesToId(Long pokemonId);
}
