package DOSW.Pokedex.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
public class Team {
    Long id;
    Long userId;
    String name;
    List<TeamPokemon> pokemon;
}
