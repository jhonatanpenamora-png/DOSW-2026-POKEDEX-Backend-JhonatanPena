package DOSW.Pokedex.core.service.interfaces;

import DOSW.Pokedex.core.model.Team;

import java.util.List;
import java.util.Optional;

public interface TeamPersistencePort {
    List<Team> findByUserId(Long userId);
    Optional<Team> findById(Long id);
    Team save(Team team);
    void deleteById(Long id);
}
