package DOSW.Pokedex.persistence.entity.relational;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "evolutions", indexes = {
    @Index(name = "idx_evolutions_pokemon_id", columnList = "pokemon_id"),
    @Index(name = "idx_evolutions_evolves_to", columnList = "evolves_to_id")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EvolutionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pokemon_id", nullable = false)
    private PokemonEntity pokemon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evolves_to_id", nullable = false)
    private PokemonEntity evolvesTo;

    @Column(name = "trigger_type", nullable = false, length = 30)
    private String triggerType;

    @Column(name = "min_level")
    private Integer minLevel;

    @Column(length = 100)
    private String item;
}
