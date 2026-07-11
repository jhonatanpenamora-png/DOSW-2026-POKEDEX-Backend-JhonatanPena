package DOSW.Pokedex.dto.response;

public record TeamPokemonResponse(
    Long id,
    Long pokemonId,
    String pokemonName,
    String pokemonImage,
    Integer slotPosition
) {}
