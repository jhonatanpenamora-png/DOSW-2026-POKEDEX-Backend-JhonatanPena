package DOSW.Pokedex.core.service.interfaces;

import DOSW.Pokedex.core.model.Pokemon;
import DOSW.Pokedex.core.model.PokemonFilterCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PokemonPersistencePort {
    Optional<Pokemon> findById(Long id);
    Optional<Pokemon> findByNationalNumber(Integer number);
    Page<Pokemon> findAll(Pageable pageable);
    List<Pokemon> findByCriteria(PokemonFilterCriteria criteria);
    boolean existsByNationalNumber(Integer number);
    Pokemon save(Pokemon pokemon);
    void deleteById(Long id);
}
