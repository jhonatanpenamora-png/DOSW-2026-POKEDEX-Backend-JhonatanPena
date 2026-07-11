package DOSW.Pokedex.dto.request;

public record PokemonFilterRequest(
    String name,
    Integer nationalNumber,
    String type,
    String region,
    Integer generation,
    Boolean hasMega,
    String rarityLevel,
    String ability,
    String move,
    String sortBy,
    String sortDirection
) {}
