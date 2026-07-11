package DOSW.Pokedex.controller;

import DOSW.Pokedex.dto.request.PokemonFilterRequest;
import DOSW.Pokedex.dto.request.PokemonRequest;
import DOSW.Pokedex.dto.response.PokemonResponse;
import DOSW.Pokedex.mapper.PokemonDtoMapper;
import DOSW.Pokedex.model.Pokemon;
import DOSW.Pokedex.model.PokemonFilterCriteria;
import DOSW.Pokedex.service.PokemonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Pokemon", description = "Gestión del catálogo de Pokémon")
@RequestMapping("/v1/pokemon")
@RestController
@RequiredArgsConstructor
public class PokemonController {

    private final PokemonService pokemonService;
    private final PokemonDtoMapper mapper;

    @Operation(summary = "Listar todos los Pokémon", description = "Retorna lista paginada de Pokémon")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida"),
        @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    @GetMapping
    public ResponseEntity<Page<PokemonResponse>> findAll(
            @PageableDefault(size = 20, sort = "nationalNumber") Pageable pageable) {
        Page<PokemonResponse> response = pokemonService.findAll(pageable)
                .map(mapper::toResponse);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener Pokémon por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pokémon encontrado"),
        @ApiResponse(responseCode = "404", description = "Pokémon no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PokemonResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(pokemonService.findById(id)));
    }

    @Operation(summary = "Buscar y filtrar Pokémon", description = "Filtra por tipo, región, generación, rareza, etc.")
    @GetMapping("/filter")
    public ResponseEntity<Page<PokemonResponse>> filter(
            @Parameter(description = "Criterios de filtrado")
            @ModelAttribute @Valid PokemonFilterRequest filter,
            Pageable pageable) {
        PokemonFilterCriteria criteria = mapper.toCriteria(filter);
        Page<PokemonResponse> response = pokemonService.findAll(pageable)
                .map(mapper::toResponse);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Crear Pokémon", description = "Solo ADMIN")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PokemonResponse> create(@Valid @RequestBody PokemonRequest request) {
        Pokemon pokemon = mapper.toDomain(request);
        Pokemon created = pokemonService.create(pokemon);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(mapper.toResponse(created));
    }

    @Operation(summary = "Actualizar Pokémon", description = "Solo ADMIN")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PokemonResponse> update(@PathVariable Long id, @Valid @RequestBody PokemonRequest request) {
        Pokemon pokemon = mapper.toDomain(request);
        Pokemon updated = pokemonService.update(id, pokemon);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @Operation(summary = "Eliminar Pokémon", description = "Solo ADMIN")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pokemonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
