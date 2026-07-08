package DOSW.Pokedex.controller.mapper;

import DOSW.Pokedex.controller.dto.response.TeamPokemonResponse;
import DOSW.Pokedex.controller.dto.response.TeamResponse;
import DOSW.Pokedex.controller.dto.request.TeamRequest;
import DOSW.Pokedex.controller.dto.response.UserResponse;
import DOSW.Pokedex.core.model.Team;
import DOSW.Pokedex.core.model.TeamPokemon;
import DOSW.Pokedex.core.model.User;
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
