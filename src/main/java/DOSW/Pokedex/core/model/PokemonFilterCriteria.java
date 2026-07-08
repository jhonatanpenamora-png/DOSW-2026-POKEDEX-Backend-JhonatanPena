package DOSW.Pokedex.core.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class PokemonFilterCriteria {
    String name;
    Integer nationalNumber;
    List<String> types;
    String region;
    Integer generation;
    Boolean hasMega;
    String rarityLevel;
    String ability;
    String move;
    Integer minStat;
    Integer maxStat;
    String statName;
    String sortBy;
    String sortDirection;
}
