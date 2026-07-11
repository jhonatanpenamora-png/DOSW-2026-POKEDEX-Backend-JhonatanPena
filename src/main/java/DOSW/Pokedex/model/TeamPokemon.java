package DOSW.Pokedex.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TeamPokemon {
    Long id;
    Long pokemonId;
    String pokemonName;
    String pokemonImage;
    Integer slotPosition;
}
