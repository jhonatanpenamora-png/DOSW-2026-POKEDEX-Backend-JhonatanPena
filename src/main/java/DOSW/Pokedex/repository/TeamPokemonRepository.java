package DOSW.Pokedex.repository;

import DOSW.Pokedex.model.TeamPokemonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamPokemonRepository extends JpaRepository<TeamPokemonEntity, Long> {
}
