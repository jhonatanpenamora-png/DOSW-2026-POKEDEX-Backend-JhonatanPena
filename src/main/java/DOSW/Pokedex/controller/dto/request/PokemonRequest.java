package DOSW.Pokedex.controller.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PokemonRequest(
    @NotNull(message = "El número nacional es obligatorio")
    @Min(value = 1, message = "El número debe ser mayor a 0")
    Integer nationalNumber,

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    String name,

    String description,
    String imageUrl,
    Integer height,
    Integer weight,
    Integer baseExperience,

    @NotNull @Min(1) Integer generation,
    Boolean hasMega,
    String rarityLevel,

    @NotNull Long regionId,

    @Size(min = 1, max = 2, message = "Debe tener entre 1 y 2 tipos")
    List<@NotNull Long> typeIds,

    PokemonStatsRequest stats,

    List<@NotNull Long> abilityIds
) {}
