package DOSW.Pokedex.controller.impl;

import DOSW.Pokedex.controller.api.PokemonApi;
import DOSW.Pokedex.controller.dto.request.PokemonFilterRequest;
import DOSW.Pokedex.controller.dto.request.PokemonRequest;
import DOSW.Pokedex.controller.dto.response.PokemonResponse;
import DOSW.Pokedex.controller.mapper.PokemonDtoMapper;
import DOSW.Pokedex.core.model.Pokemon;
import DOSW.Pokedex.core.model.PokemonFilterCriteria;
import DOSW.Pokedex.core.service.interfaces.PokemonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class PokemonController implements PokemonApi {

    private final PokemonService pokemonService;
    private final PokemonDtoMapper mapper;

    @Override
    public ResponseEntity<Page<PokemonResponse>> findAll(Pageable pageable) {
        Page<PokemonResponse> response = pokemonService.findAll(pageable)
                .map(mapper::toResponse);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<PokemonResponse> findById(Long id) {
        return ResponseEntity.ok(mapper.toResponse(pokemonService.findById(id)));
    }

    @Override
    public ResponseEntity<Page<PokemonResponse>> filter(PokemonFilterRequest filter, Pageable pageable) {
        PokemonFilterCriteria criteria = mapper.toCriteria(filter);
        Page<PokemonResponse> response = pokemonService.findAll(pageable)
                .map(mapper::toResponse);
        return ResponseEntity.ok(response);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PokemonResponse> create(PokemonRequest request) {
        Pokemon pokemon = mapper.toDomain(request);
        Pokemon created = pokemonService.create(pokemon);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(mapper.toResponse(created));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PokemonResponse> update(Long id, PokemonRequest request) {
        Pokemon pokemon = mapper.toDomain(request);
        Pokemon updated = pokemonService.update(id, pokemon);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(Long id) {
        pokemonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
