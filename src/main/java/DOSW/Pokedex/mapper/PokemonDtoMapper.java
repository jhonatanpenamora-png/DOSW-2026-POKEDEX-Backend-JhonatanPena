package DOSW.Pokedex.mapper;

import DOSW.Pokedex.dto.request.PokemonFilterRequest;
import DOSW.Pokedex.dto.request.PokemonRequest;
import DOSW.Pokedex.dto.request.PokemonStatsRequest;
import DOSW.Pokedex.dto.response.PokemonResponse;
import DOSW.Pokedex.dto.response.PokemonStatsResponse;
import DOSW.Pokedex.model.Pokemon;
import DOSW.Pokedex.model.PokemonFilterCriteria;
import DOSW.Pokedex.model.PokemonStats;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PokemonDtoMapper {

    @Mapping(source = "stats.specialAttack", target = "stats.specialAttack")
    PokemonResponse toResponse(Pokemon pokemon);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hasMega", defaultValue = "false")
    @Mapping(target = "rarityLevel", defaultValue = "COMUN")
    @Mapping(target = "stats", source = "stats")
    @Mapping(target = "region", ignore = true)
    @Mapping(target = "types", ignore = true)
    @Mapping(target = "abilities", ignore = true)
    @Mapping(target = "evolutionChain", ignore = true)
    @Mapping(target = "moves", ignore = true)
    Pokemon toDomain(PokemonRequest request);

    @Mapping(target = "sortBy", ignore = true)
    @Mapping(target = "sortDirection", ignore = true)
    PokemonFilterCriteria toCriteria(PokemonFilterRequest request);

    default PokemonStats mapStats(PokemonStatsRequest stats) {
        if (stats == null) return null;
        return PokemonStats.builder()
                .hp(stats.hp())
                .attack(stats.attack())
                .defense(stats.defense())
                .specialAttack(stats.specialAttack())
                .specialDefense(stats.specialDefense())
                .speed(stats.speed())
                .build();
    }

    default PokemonStatsResponse mapStats(PokemonStats stats) {
        if (stats == null) return null;
        return new PokemonStatsResponse(
                stats.getHp(), stats.getAttack(), stats.getDefense(),
                stats.getSpecialAttack(), stats.getSpecialDefense(),
                stats.getSpeed(), stats.getTotal()
        );
    }
}
