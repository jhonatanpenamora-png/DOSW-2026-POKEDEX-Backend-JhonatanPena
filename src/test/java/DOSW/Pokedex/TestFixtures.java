package DOSW.Pokedex;

import DOSW.Pokedex.model.Pokemon;
import DOSW.Pokedex.model.PokemonStats;

import java.util.List;

public class TestFixtures {

    public static Pokemon createPikachu() {
        return Pokemon.builder()
                .id(1L)
                .nationalNumber(25)
                .name("Pikachu")
                .description("When it is angry, it immediately discharges the energy stored in the pouches in its cheeks.")
                .imageUrl("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png")
                .height(4)
                .weight(60)
                .baseExperience(112)
                .generation(1)
                .hasMega(false)
                .rarityLevel("COMUN")
                .region("Kanto")
                .types(List.of("Electric"))
                .stats(createPikachuStats())
                .abilities(List.of("Static"))
                .evolutionChain(List.of(172, 26))
                .moves(List.of("Thunder Shock", "Quick Attack"))
                .build();
    }

    public static PokemonStats createPikachuStats() {
        return PokemonStats.builder()
                .hp(35)
                .attack(55)
                .defense(40)
                .specialAttack(50)
                .specialDefense(50)
                .speed(90)
                .build();
    }

    public static Pokemon createCharizard() {
        return Pokemon.builder()
                .id(6L)
                .nationalNumber(6)
                .name("Charizard")
                .generation(1)
                .hasMega(true)
                .rarityLevel("COMUN")
                .region("Kanto")
                .types(List.of("Fire", "Flying"))
                .stats(PokemonStats.builder()
                        .hp(78).attack(84).defense(78)
                        .specialAttack(109).specialDefense(85).speed(100)
                        .build())
                .abilities(List.of("Blaze"))
                .build();
    }
}
