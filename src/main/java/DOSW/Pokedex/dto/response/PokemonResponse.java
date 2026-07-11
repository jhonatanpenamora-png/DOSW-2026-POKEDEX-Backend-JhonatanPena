package DOSW.Pokedex.dto.response;

import java.util.List;

public record PokemonResponse(
    Long id,
    Integer nationalNumber,
    String name,
    String description,
    String imageUrl,
    Integer height,
    Integer weight,
    Integer baseExperience,
    Integer generation,
    Boolean hasMega,
    String rarityLevel,
    String region,
    List<String> types,
    PokemonStatsResponse stats,
    List<String> abilities,
    List<Integer> evolutionChain,
    List<String> moves
) {}
