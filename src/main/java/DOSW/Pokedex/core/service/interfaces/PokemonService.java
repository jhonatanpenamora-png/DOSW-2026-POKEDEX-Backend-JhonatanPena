package DOSW.Pokedex.core.service.interfaces;

import DOSW.Pokedex.core.model.Pokemon;
import DOSW.Pokedex.core.model.PokemonFilterCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PokemonService {
    Page<Pokemon> findAll(Pageable pageable);
    Pokemon findById(Long id);
    Pokemon findByNationalNumber(Integer number);
    List<Pokemon> filterByCriteria(PokemonFilterCriteria criteria);
    Pokemon create(Pokemon pokemon);
    Pokemon update(Long id, Pokemon pokemon);
    void delete(Long id);
}
