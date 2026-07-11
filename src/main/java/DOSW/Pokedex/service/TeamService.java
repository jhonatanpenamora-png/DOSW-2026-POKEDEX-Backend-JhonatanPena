package DOSW.Pokedex.service;

import DOSW.Pokedex.exception.ResourceNotFoundException;
import DOSW.Pokedex.exception.UnauthorizedException;
import DOSW.Pokedex.model.Team;
import DOSW.Pokedex.model.TeamEntity;
import DOSW.Pokedex.model.TeamPokemon;
import DOSW.Pokedex.repository.TeamJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {

    private final TeamJpaRepository teamRepo;

    @Transactional(readOnly = true)
    public List<Team> findMyTeams(Long userId) {
        return teamRepo.findByUserId(userId).stream()
                .map(TeamService::toDomain)
                .toList();
    }

    @Transactional(readOnly = true)
    public Team findById(Long id, Long userId) {
        TeamEntity entity = teamRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team", "id", id));

        if (!entity.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("No puedes ver equipos de otros usuarios");
        }

        return toDomain(entity);
    }

    @Transactional
    public Team create(Team team, Long userId) {
        log.info("Creando equipo para usuario: {}", userId);
        TeamEntity entity = TeamEntity.builder()
                .name(team.getName())
                .build();
        TeamEntity saved = teamRepo.save(entity);
        return toDomain(saved);
    }

    @Transactional
    public Team update(Long id, Team team, Long userId) {
        TeamEntity existing = teamRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team", "id", id));

        if (!existing.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("No puedes modificar equipos de otros usuarios");
        }

        TeamEntity updated = TeamEntity.builder()
                .id(id)
                .name(team.getName())
                .build();
        TeamEntity saved = teamRepo.save(updated);
        return toDomain(saved);
    }

    @Transactional
    public void delete(Long id, Long userId) {
        TeamEntity existing = teamRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team", "id", id));

        if (!existing.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("No puedes eliminar equipos de otros usuarios");
        }

        teamRepo.deleteById(id);
    }

    private static Team toDomain(TeamEntity entity) {
        if (entity == null) return null;
        List<TeamPokemon> pokemonList = entity.getPokemon() != null ?
                entity.getPokemon().stream()
                        .map(tp -> TeamPokemon.builder()
                                .id(tp.getId())
                                .pokemonId(tp.getPokemon().getId())
                                .pokemonName(tp.getPokemon().getName())
                                .slotPosition(tp.getSlotPosition())
                                .build())
                        .toList()
                : List.of();

        return Team.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .name(entity.getName())
                .pokemon(pokemonList)
                .build();
    }
}
