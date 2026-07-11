package DOSW.Pokedex.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class Favorite {
    Long id;
    Long userId;
    Long pokemonId;
    String pokemonName;
    String pokemonImage;
    LocalDateTime createdAt;
}
