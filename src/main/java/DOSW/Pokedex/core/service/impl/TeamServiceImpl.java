package DOSW.Pokedex.core.service.impl;

import DOSW.Pokedex.core.exception.ResourceNotFoundException;
import DOSW.Pokedex.core.exception.UnauthorizedException;
import DOSW.Pokedex.core.model.Team;
import DOSW.Pokedex.core.service.interfaces.TeamPersistencePort;
import DOSW.Pokedex.core.service.interfaces.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamServiceImpl implements TeamService {

    private final TeamPersistencePort teamPort;

    @Override
    @Transactional(readOnly = true)
    public List<Team> findMyTeams(Long userId) {
        return teamPort.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Team findById(Long id, Long userId) {
        Team team = teamPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team", "id", id));

        if (!team.getUserId().equals(userId)) {
            throw new UnauthorizedException("No puedes ver equipos de otros usuarios");
        }

        return team;
    }

    @Override
    @Transactional
    public Team create(Team team, Long userId) {
        log.info("Creando equipo para usuario: {}", userId);
        return teamPort.save(team);
    }

    @Override
    @Transactional
    public Team update(Long id, Team team, Long userId) {
        Team existing = findById(id, userId);
        return teamPort.save(team.toBuilder().id(id).build());
    }

    @Override
    @Transactional
    public void delete(Long id, Long userId) {
        findById(id, userId);
        teamPort.deleteById(id);
    }
}
