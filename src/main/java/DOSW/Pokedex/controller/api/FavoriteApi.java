package DOSW.Pokedex.controller.api;

import DOSW.Pokedex.controller.dto.response.FavoriteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Favorites", description = "Gestión de Pokémon favoritos")
@RequestMapping("/v1/favorites")
@SecurityRequirement(name = "Bearer Authentication")
public interface FavoriteApi {

    @Operation(summary = "Listar mis favoritos")
    @GetMapping
    ResponseEntity<List<FavoriteResponse>> findMyFavorites();

    @Operation(summary = "Agregar Pokémon a favoritos")
    @PostMapping("/{pokemonId}")
    ResponseEntity<FavoriteResponse> addFavorite(@PathVariable Long pokemonId);

    @Operation(summary = "Eliminar Pokémon de favoritos")
    @DeleteMapping("/{pokemonId}")
    ResponseEntity<Void> removeFavorite(@PathVariable Long pokemonId);
}
