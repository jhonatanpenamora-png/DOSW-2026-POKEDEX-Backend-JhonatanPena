package DOSW.Pokedex.core.service.impl;

import DOSW.Pokedex.core.exception.DuplicateResourceException;
import DOSW.Pokedex.core.exception.ResourceNotFoundException;
import DOSW.Pokedex.core.model.Pokemon;
import DOSW.Pokedex.core.model.PokemonFilterCriteria;
import DOSW.Pokedex.core.service.interfaces.PokemonPersistencePort;
import DOSW.Pokedex.core.service.interfaces.PokemonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PokemonServiceImpl implements PokemonService {

    private final PokemonPersistencePort pokemonPort;

    @Override
    @Transactional(readOnly = true)
    public Page<Pokemon> findAll(Pageable pageable) {
        log.debug("Listando pokemon paginado: {}", pageable);
        return pokemonPort.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Pokemon findById(Long id) {
        log.debug("Buscando pokemon por id: {}", id);
        return pokemonPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pokemon", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public Pokemon findByNationalNumber(Integer number) {
        return pokemonPort.findByNationalNumber(number)
                .orElseThrow(() -> new ResourceNotFoundException("Pokemon", "nationalNumber", number));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pokemon> filterByCriteria(PokemonFilterCriteria criteria) {
        log.debug("Filtrando pokemon con criterios: {}", criteria);
        return pokemonPort.findByCriteria(criteria);
    }

    @Override
    @Transactional
    public Pokemon create(Pokemon pokemon) {
        log.info("Creando pokemon: {}", pokemon.getName());

        if (pokemonPort.existsByNationalNumber(pokemon.getNationalNumber())) {
            throw new DuplicateResourceException("Pokemon", "nationalNumber", pokemon.getNationalNumber());
        }

        return pokemonPort.save(pokemon);
    }

    @Override
    @Transactional
    public Pokemon update(Long id, Pokemon pokemon) {
        log.info("Actualizando pokemon id: {}", id);

        Pokemon existing = findById(id);

        if (!existing.getNationalNumber().equals(pokemon.getNationalNumber())
                && pokemonPort.existsByNationalNumber(pokemon.getNationalNumber())) {
            throw new DuplicateResourceException("Pokemon", "nationalNumber", pokemon.getNationalNumber());
        }

        return pokemonPort.save(pokemon.toBuilder().id(id).build());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Eliminando pokemon id: {}", id);
        findById(id);
        pokemonPort.deleteById(id);
    }
}
