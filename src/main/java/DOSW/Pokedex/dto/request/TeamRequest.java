package DOSW.Pokedex.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record TeamRequest(
    @NotBlank @Size(max = 100) String name,

    @Size(min = 1, max = 6, message = "El equipo debe tener entre 1 y 6 pokemon")
    List<@NotNull Long> pokemonIds
) {}
