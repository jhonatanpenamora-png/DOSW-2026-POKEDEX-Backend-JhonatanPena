package DOSW.Pokedex.dto.response;

import java.time.LocalDateTime;

public record FavoriteResponse(
    Long id,
    Long pokemonId,
    String pokemonName,
    String pokemonImage,
    LocalDateTime createdAt
) {}
