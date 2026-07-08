package DOSW.Pokedex.core.service.interfaces;

import DOSW.Pokedex.core.model.Team;

import java.util.List;

public interface TeamService {
    List<Team> findMyTeams(Long userId);
    Team findById(Long id, Long userId);
    Team create(Team team, Long userId);
    Team update(Long id, Team team, Long userId);
    void delete(Long id, Long userId);
}
