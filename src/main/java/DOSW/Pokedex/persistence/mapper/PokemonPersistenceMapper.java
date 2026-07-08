package DOSW.Pokedex.persistence.mapper;

import DOSW.Pokedex.core.model.Pokemon;
import DOSW.Pokedex.core.model.PokemonStats;
import DOSW.Pokedex.persistence.entity.relational.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PokemonPersistenceMapper {

    @Mapping(source = "region.name", target = "region")
    @Mapping(target = "types", expression = "java(mapTypes(entity))")
    @Mapping(target = "stats", expression = "java(mapStats(entity.getStats()))")
    @Mapping(target = "abilities", expression = "java(mapAbilities(entity.getAbilities()))")
    @Mapping(target = "evolutionChain", expression = "java(mapEvolutionIds(entity.getEvolutions()))")
    @Mapping(target = "moves", expression = "java(mapMoveNames(entity.getMoves()))")
    Pokemon toDomain(PokemonEntity entity);

    @Mapping(target = "region", ignore = true)
    @Mapping(target = "types", ignore = true)
    @Mapping(target = "stats", ignore = true)
    @Mapping(target = "abilities", ignore = true)
    @Mapping(target = "evolutions", ignore = true)
    @Mapping(target = "moves", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PokemonEntity toEntity(Pokemon pokemon);

    default List<String> mapTypes(PokemonEntity entity) {
        if (entity.getTypes() == null) return List.of();
        return entity.getTypes().stream().map(TypeEntity::getName).toList();
    }

    default PokemonStats mapStats(PokemonStatsEntity stats) {
        if (stats == null) return null;
        return PokemonStats.builder()
                .hp(stats.getHp())
                .attack(stats.getAttack())
                .defense(stats.getDefense())
                .specialAttack(stats.getSpecialAttack())
                .specialDefense(stats.getSpecialDefense())
                .speed(stats.getSpeed())
                .build();
    }

    default List<String> mapAbilities(List<PokemonAbilityEntity> abilities) {
        if (abilities == null) return List.of();
        return abilities.stream()
                .map(pa -> pa.getAbility().getName())
                .toList();
    }

    default List<Integer> mapEvolutionIds(List<EvolutionEntity> evolutions) {
        if (evolutions == null) return List.of();
        return evolutions.stream()
                .map(e -> e.getEvolvesTo().getNationalNumber())
                .toList();
    }

    default List<String> mapMoveNames(List<PokemonMoveEntity> moves) {
        if (moves == null) return List.of();
        return moves.stream()
                .map(pm -> pm.getMove().getName())
                .toList();
    }
}
