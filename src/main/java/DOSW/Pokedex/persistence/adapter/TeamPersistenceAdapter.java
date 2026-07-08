package DOSW.Pokedex.persistence.adapter;

import DOSW.Pokedex.core.model.Team;
import DOSW.Pokedex.core.service.interfaces.TeamPersistencePort;
import DOSW.Pokedex.persistence.mapper.TeamPersistenceMapper;
import DOSW.Pokedex.persistence.repository.relational.TeamJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TeamPersistenceAdapter implements TeamPersistencePort {

    private final TeamJpaRepository repository;
    private final TeamPersistenceMapper mapper;

    @Override
    public List<Team> findByUserId(Long userId) {
        return repository.findByUserId(userId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Team> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Team save(Team team) {
        var entity = mapper.toEntity(team);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
