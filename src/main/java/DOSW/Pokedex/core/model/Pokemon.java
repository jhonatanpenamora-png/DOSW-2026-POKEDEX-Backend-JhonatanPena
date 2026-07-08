package DOSW.Pokedex.core.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
public class Pokemon {
    Long id;
    Integer nationalNumber;
    String name;
    String description;
    String imageUrl;
    Integer height;
    Integer weight;
    Integer baseExperience;
    Integer generation;
    Boolean hasMega;
    String rarityLevel;
    String region;
    List<String> types;
    PokemonStats stats;
    List<String> abilities;
    List<Integer> evolutionChain;
    List<String> moves;
}
