package DOSW.Pokedex.controller;

import DOSW.Pokedex.dto.request.TeamRequest;
import DOSW.Pokedex.dto.response.TeamResponse;
import DOSW.Pokedex.mapper.TeamDtoMapper;
import DOSW.Pokedex.model.Team;
import DOSW.Pokedex.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Teams", description = "Gestión de equipos Pokémon")
@RequestMapping("/v1/teams")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final TeamDtoMapper mapper;

    @Operation(summary = "Listar mis equipos")
    @GetMapping
    public ResponseEntity<List<TeamResponse>> findMyTeams() {
        Long userId = getCurrentUserId();
        return ResponseEntity.ok(teamService.findMyTeams(userId).stream()
                .map(mapper::toResponse)
                .toList());
    }

    @Operation(summary = "Obtener equipo por ID")
    @GetMapping("/{id}")
    public ResponseEntity<TeamResponse> findById(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        return ResponseEntity.ok(mapper.toResponse(teamService.findById(id, userId)));
    }

    @Operation(summary = "Crear equipo")
    @PostMapping
    public ResponseEntity<TeamResponse> create(@Valid @RequestBody TeamRequest request) {
        Long userId = getCurrentUserId();
        Team team = mapper.toDomain(request);
        Team created = teamService.create(team, userId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(mapper.toResponse(created));
    }

    @Operation(summary = "Actualizar equipo")
    @PutMapping("/{id}")
    public ResponseEntity<TeamResponse> update(@PathVariable Long id, @Valid @RequestBody TeamRequest request) {
        Long userId = getCurrentUserId();
        Team team = mapper.toDomain(request);
        Team updated = teamService.update(id, team, userId);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @Operation(summary = "Eliminar equipo")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        teamService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }

    private Long getCurrentUserId() {
        return 1L;
    }
}
