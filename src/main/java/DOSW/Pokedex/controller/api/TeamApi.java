package DOSW.Pokedex.controller.api;

import DOSW.Pokedex.controller.dto.request.TeamRequest;
import DOSW.Pokedex.controller.dto.response.TeamResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Teams", description = "Gestión de equipos Pokémon")
@RequestMapping("/v1/teams")
@SecurityRequirement(name = "Bearer Authentication")
public interface TeamApi {

    @Operation(summary = "Listar mis equipos")
    @GetMapping
    ResponseEntity<List<TeamResponse>> findMyTeams();

    @Operation(summary = "Obtener equipo por ID")
    @GetMapping("/{id}")
    ResponseEntity<TeamResponse> findById(@PathVariable Long id);

    @Operation(summary = "Crear equipo")
    @PostMapping
    ResponseEntity<TeamResponse> create(@Valid @RequestBody TeamRequest request);

    @Operation(summary = "Actualizar equipo")
    @PutMapping("/{id}")
    ResponseEntity<TeamResponse> update(@PathVariable Long id, @Valid @RequestBody TeamRequest request);

    @Operation(summary = "Eliminar equipo")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
