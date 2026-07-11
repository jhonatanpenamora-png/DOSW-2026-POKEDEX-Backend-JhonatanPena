package DOSW.Pokedex.dto.response;

import java.util.List;

public record TeamResponse(
    Long id,
    String name,
    List<TeamPokemonResponse> pokemon
) {}
