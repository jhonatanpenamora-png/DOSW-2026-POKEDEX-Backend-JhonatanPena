package DOSW.Pokedex.controller.impl;

import DOSW.Pokedex.controller.api.TeamApi;
import DOSW.Pokedex.controller.dto.request.TeamRequest;
import DOSW.Pokedex.controller.dto.response.TeamResponse;
import DOSW.Pokedex.controller.mapper.TeamDtoMapper;
import DOSW.Pokedex.core.model.Team;
import DOSW.Pokedex.core.service.interfaces.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeamController implements TeamApi {

    private final TeamService teamService;
    private final TeamDtoMapper mapper;

    @Override
    public ResponseEntity<List<TeamResponse>> findMyTeams() {
        Long userId = getCurrentUserId();
        return ResponseEntity.ok(teamService.findMyTeams(userId).stream()
                .map(mapper::toResponse)
                .toList());
    }

    @Override
    public ResponseEntity<TeamResponse> findById(Long id) {
        Long userId = getCurrentUserId();
        return ResponseEntity.ok(mapper.toResponse(teamService.findById(id, userId)));
    }

    @Override
    public ResponseEntity<TeamResponse> create(TeamRequest request) {
        Long userId = getCurrentUserId();
        Team team = mapper.toDomain(request);
        Team created = teamService.create(team, userId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(mapper.toResponse(created));
    }

    @Override
    public ResponseEntity<TeamResponse> update(Long id, TeamRequest request) {
        Long userId = getCurrentUserId();
        Team team = mapper.toDomain(request);
        Team updated = teamService.update(id, team, userId);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        Long userId = getCurrentUserId();
        teamService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }

    private Long getCurrentUserId() {
        return 1L;
    }
}
