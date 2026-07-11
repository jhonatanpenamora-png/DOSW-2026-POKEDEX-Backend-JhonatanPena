package DOSW.Pokedex.dto.request;

import jakarta.validation.constraints.Min;

public record PokemonStatsRequest(
    @Min(0) Integer hp,
    @Min(0) Integer attack,
    @Min(0) Integer defense,
    @Min(0) Integer specialAttack,
    @Min(0) Integer specialDefense,
    @Min(0) Integer speed
) {}
