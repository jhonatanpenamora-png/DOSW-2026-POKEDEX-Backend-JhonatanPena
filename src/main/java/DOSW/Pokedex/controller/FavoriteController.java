package DOSW.Pokedex.controller;

import DOSW.Pokedex.dto.response.FavoriteResponse;
import DOSW.Pokedex.model.Favorite;
import DOSW.Pokedex.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Favorites", description = "Gestión de Pokémon favoritos")
@RequestMapping("/v1/favorites")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Operation(summary = "Listar mis favoritos")
    @GetMapping
    public ResponseEntity<List<FavoriteResponse>> findMyFavorites() {
        Long userId = getCurrentUserId();
        List<FavoriteResponse> response = favoriteService.findMyFavorites(userId).stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Agregar Pokémon a favoritos")
    @PostMapping("/{pokemonId}")
    public ResponseEntity<FavoriteResponse> addFavorite(@PathVariable Long pokemonId) {
        Long userId = getCurrentUserId();
        Favorite favorite = favoriteService.addFavorite(userId, pokemonId);
        return ResponseEntity.ok(toResponse(favorite));
    }

    @Operation(summary = "Eliminar Pokémon de favoritos")
    @DeleteMapping("/{pokemonId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long pokemonId) {
        Long userId = getCurrentUserId();
        favoriteService.removeFavorite(userId, pokemonId);
        return ResponseEntity.noContent().build();
    }

    private Long getCurrentUserId() {
        return 1L;
    }

    private FavoriteResponse toResponse(Favorite favorite) {
        return new FavoriteResponse(
                favorite.getId(),
                favorite.getPokemonId(),
                favorite.getPokemonName(),
                favorite.getPokemonImage(),
                favorite.getCreatedAt()
        );
    }
}
