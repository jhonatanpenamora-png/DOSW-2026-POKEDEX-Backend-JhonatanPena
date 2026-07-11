package DOSW.Pokedex.service;

import DOSW.Pokedex.exception.DuplicateResourceException;
import DOSW.Pokedex.exception.ResourceNotFoundException;
import DOSW.Pokedex.model.*;
import DOSW.Pokedex.repository.PokemonJpaRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PokemonService {

    private final PokemonJpaRepository pokemonRepo;

    @Transactional(readOnly = true)
    public Page<Pokemon> findAll(Pageable pageable) {
        log.debug("Listando pokemon paginado: {}", pageable);
        return pokemonRepo.findAll(pageable).map(PokemonService::toDomain);
    }

    @Transactional(readOnly = true)
    public Pokemon findById(Long id) {
        log.debug("Buscando pokemon por id: {}", id);
        return pokemonRepo.findById(id)
                .map(PokemonService::toDomain)
                .orElseThrow(() -> new ResourceNotFoundException("Pokemon", "id", id));
    }

    @Transactional(readOnly = true)
    public Pokemon findByNationalNumber(Integer number) {
        return pokemonRepo.findByNationalNumber(number)
                .map(PokemonService::toDomain)
                .orElseThrow(() -> new ResourceNotFoundException("Pokemon", "nationalNumber", number));
    }

    @Transactional(readOnly = true)
    public List<Pokemon> filterByCriteria(PokemonFilterCriteria criteria) {
        log.debug("Filtrando pokemon con criterios: {}", criteria);
        Specification<PokemonEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (criteria.getName() != null) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + criteria.getName().toLowerCase() + "%"));
            }
            if (criteria.getNationalNumber() != null) {
                predicates.add(cb.equal(root.get("nationalNumber"), criteria.getNationalNumber()));
            }
            if (criteria.getGeneration() != null) {
                predicates.add(cb.equal(root.get("generation"), criteria.getGeneration()));
            }
            if (criteria.getHasMega() != null) {
                predicates.add(cb.equal(root.get("hasMega"), criteria.getHasMega()));
            }
            if (criteria.getRarityLevel() != null) {
                predicates.add(cb.equal(root.get("rarityLevel"), criteria.getRarityLevel()));
            }
            if (criteria.getRegion() != null) {
                predicates.add(cb.equal(root.join("region").get("name"), criteria.getRegion()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return pokemonRepo.findAll(spec).stream().map(PokemonService::toDomain).toList();
    }

    @Transactional
    public Pokemon create(Pokemon pokemon) {
        log.info("Creando pokemon: {}", pokemon.getName());

        if (pokemonRepo.existsByNationalNumber(pokemon.getNationalNumber())) {
            throw new DuplicateResourceException("Pokemon", "nationalNumber", pokemon.getNationalNumber());
        }

        PokemonEntity entity = toEntity(pokemon);
        PokemonEntity saved = pokemonRepo.save(entity);
        return toDomain(saved);
    }

    @Transactional
    public Pokemon update(Long id, Pokemon pokemon) {
        log.info("Actualizando pokemon id: {}", id);

        PokemonEntity existing = pokemonRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pokemon", "id", id));

        if (!existing.getNationalNumber().equals(pokemon.getNationalNumber())
                && pokemonRepo.existsByNationalNumber(pokemon.getNationalNumber())) {
            throw new DuplicateResourceException("Pokemon", "nationalNumber", pokemon.getNationalNumber());
        }

        PokemonEntity entity = toEntityWithId(pokemon, id);
        PokemonEntity saved = pokemonRepo.save(entity);
        return toDomain(saved);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Eliminando pokemon id: {}", id);
        if (!pokemonRepo.existsById(id)) {
            throw new ResourceNotFoundException("Pokemon", "id", id);
        }
        pokemonRepo.deleteById(id);
    }

    private static Pokemon toDomain(PokemonEntity entity) {
        if (entity == null) return null;
        Pokemon.PokemonBuilder builder = Pokemon.builder()
                .id(entity.getId())
                .nationalNumber(entity.getNationalNumber())
                .name(entity.getName())
                .description(entity.getDescription())
                .imageUrl(entity.getImageUrl())
                .height(entity.getHeight())
                .weight(entity.getWeight())
                .baseExperience(entity.getBaseExperience())
                .generation(entity.getGeneration())
                .hasMega(entity.getHasMega())
                .rarityLevel(entity.getRarityLevel())
                .region(entity.getRegion() != null ? entity.getRegion().getName() : null)
                .types(entity.getTypes() != null ?
                        entity.getTypes().stream().map(TypeEntity::getName).toList() : null);

        if (entity.getStats() != null) {
            PokemonStatsEntity s = entity.getStats();
            builder.stats(PokemonStats.builder()
                    .hp(s.getHp()).attack(s.getAttack()).defense(s.getDefense())
                    .specialAttack(s.getSpecialAttack()).specialDefense(s.getSpecialDefense())
                    .speed(s.getSpeed()).build());
        }

        if (entity.getAbilities() != null) {
            builder.abilities(entity.getAbilities().stream()
                    .map(pa -> pa.getAbility().getName()).toList());
        }

        if (entity.getEvolutions() != null) {
            builder.evolutionChain(entity.getEvolutions().stream()
                    .map(e -> e.getEvolvesTo().getNationalNumber()).toList());
        }

        if (entity.getMoves() != null) {
            builder.moves(entity.getMoves().stream()
                    .map(pm -> pm.getMove().getName()).toList());
        }

        return builder.build();
    }

    private PokemonEntity toEntity(Pokemon domain) {
        if (domain == null) return null;
        return PokemonEntity.builder()
                .nationalNumber(domain.getNationalNumber())
                .name(domain.getName())
                .description(domain.getDescription())
                .imageUrl(domain.getImageUrl())
                .height(domain.getHeight())
                .weight(domain.getWeight())
                .baseExperience(domain.getBaseExperience())
                .generation(domain.getGeneration())
                .hasMega(domain.getHasMega() != null ? domain.getHasMega() : false)
                .rarityLevel(domain.getRarityLevel() != null ? domain.getRarityLevel() : "COMUN")
                .build();
    }

    private PokemonEntity toEntityWithId(Pokemon domain, Long id) {
        PokemonEntity entity = toEntity(domain);
        // Use reflection-free approach: build with id via builder
        return PokemonEntity.builder()
                .id(id)
                .nationalNumber(entity.getNationalNumber())
                .name(entity.getName())
                .description(entity.getDescription())
                .imageUrl(entity.getImageUrl())
                .height(entity.getHeight())
                .weight(entity.getWeight())
                .baseExperience(entity.getBaseExperience())
                .generation(entity.getGeneration())
                .hasMega(entity.getHasMega())
                .rarityLevel(entity.getRarityLevel())
                .build();
    }
}
