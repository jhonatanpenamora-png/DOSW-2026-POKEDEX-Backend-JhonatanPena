package DOSW.Pokedex.persistence.adapter;

import DOSW.Pokedex.core.model.Pokemon;
import DOSW.Pokedex.core.model.PokemonFilterCriteria;
import DOSW.Pokedex.core.service.interfaces.PokemonPersistencePort;
import DOSW.Pokedex.persistence.entity.relational.PokemonEntity;
import DOSW.Pokedex.persistence.mapper.PokemonPersistenceMapper;
import DOSW.Pokedex.persistence.repository.relational.PokemonJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PokemonPersistenceAdapter implements PokemonPersistencePort {

    private final PokemonJpaRepository repository;
    private final PokemonPersistenceMapper mapper;
    private final EntityManager entityManager;

    @Override
    public Optional<Pokemon> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Pokemon> findByNationalNumber(Integer number) {
        return repository.findByNationalNumber(number)
                .map(mapper::toDomain);
    }

    @Override
    public Page<Pokemon> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toDomain);
    }

    @Override
    public List<Pokemon> findByCriteria(PokemonFilterCriteria criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PokemonEntity> query = cb.createQuery(PokemonEntity.class);
        Root<PokemonEntity> root = query.from(PokemonEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getName() != null && !criteria.getName().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("name")),
                    "%" + criteria.getName().toLowerCase() + "%"));
        }

        if (criteria.getNationalNumber() != null) {
            predicates.add(cb.equal(root.get("nationalNumber"), criteria.getNationalNumber()));
        }

        if (criteria.getRegion() != null && !criteria.getRegion().isBlank()) {
            predicates.add(cb.equal(root.get("region").get("name"), criteria.getRegion()));
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

        query.where(predicates.toArray(new Predicate[0]));

        if (criteria.getSortBy() != null) {
            Path<?> sortPath = root.get(criteria.getSortBy());
            if ("desc".equalsIgnoreCase(criteria.getSortDirection())) {
                query.orderBy(cb.desc(sortPath));
            } else {
                query.orderBy(cb.asc(sortPath));
            }
        }

        TypedQuery<PokemonEntity> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByNationalNumber(Integer number) {
        return repository.existsByNationalNumber(number);
    }

    @Override
    public Pokemon save(Pokemon pokemon) {
        PokemonEntity entity = mapper.toEntity(pokemon);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
