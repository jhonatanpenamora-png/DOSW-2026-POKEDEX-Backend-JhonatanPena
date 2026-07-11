package DOSW.Pokedex.mapper;

import DOSW.Pokedex.dto.response.TeamPokemonResponse;
import DOSW.Pokedex.dto.response.TeamResponse;
import DOSW.Pokedex.dto.request.TeamRequest;
import DOSW.Pokedex.model.Team;
import DOSW.Pokedex.model.TeamPokemon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamDtoMapper {

    @Mapping(target = "pokemon", ignore = true)
    TeamResponse toResponse(Team team);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "pokemon", ignore = true)
    Team toDomain(TeamRequest request);

    default List<TeamPokemonResponse> mapPokemon(List<TeamPokemon> pokemon) {
        if (pokemon == null) return List.of();
        return pokemon.stream()
                .map(tp -> new TeamPokemonResponse(
                        tp.getId(), tp.getPokemonId(),
                        tp.getPokemonName(), tp.getPokemonImage(),
                        tp.getSlotPosition()))
                .toList();
    }
}
