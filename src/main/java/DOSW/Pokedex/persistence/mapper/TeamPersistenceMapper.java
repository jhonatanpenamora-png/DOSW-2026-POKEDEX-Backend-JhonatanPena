package DOSW.Pokedex.persistence.mapper;

import DOSW.Pokedex.core.model.Team;
import DOSW.Pokedex.core.model.TeamPokemon;
import DOSW.Pokedex.persistence.entity.relational.TeamEntity;
import DOSW.Pokedex.persistence.entity.relational.TeamPokemonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamPersistenceMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "pokemon", expression = "java(mapTeamPokemon(entity))")
    Team toDomain(TeamEntity entity);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "pokemon", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    TeamEntity toEntity(Team team);

    default List<TeamPokemon> mapTeamPokemon(TeamEntity entity) {
        if (entity.getPokemon() == null) return List.of();
        return entity.getPokemon().stream()
                .map(tp -> TeamPokemon.builder()
                        .id(tp.getId())
                        .pokemonId(tp.getPokemon().getId())
                        .pokemonName(tp.getPokemon().getName())
                        .slotPosition(tp.getSlotPosition())
                        .build())
                .toList();
    }
}
