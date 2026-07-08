package DOSW.Pokedex.controller.impl;

import DOSW.Pokedex.controller.api.FavoriteApi;
import DOSW.Pokedex.controller.dto.response.FavoriteResponse;
import DOSW.Pokedex.core.model.Favorite;
import DOSW.Pokedex.core.service.interfaces.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FavoriteController implements FavoriteApi {

    private final FavoriteService favoriteService;

    @Override
    public ResponseEntity<List<FavoriteResponse>> findMyFavorites() {
        Long userId = getCurrentUserId();
        List<FavoriteResponse> response = favoriteService.findMyFavorites(userId).stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<FavoriteResponse> addFavorite(Long pokemonId) {
        Long userId = getCurrentUserId();
        Favorite favorite = favoriteService.addFavorite(userId, pokemonId);
        return ResponseEntity.ok(toResponse(favorite));
    }

    @Override
    public ResponseEntity<Void> removeFavorite(Long pokemonId) {
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
