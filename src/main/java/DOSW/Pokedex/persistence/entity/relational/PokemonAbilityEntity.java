package DOSW.Pokedex.persistence.entity.relational;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pokemon_ability", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"pokemon_id", "ability_id"})
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PokemonAbilityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pokemon_id", nullable = false)
    private PokemonEntity pokemon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ability_id", nullable = false)
    private AbilityEntity ability;

    @Column(name = "is_hidden", nullable = false)
    @Builder.Default
    private Boolean isHidden = false;

    @Column(nullable = false)
    @Builder.Default
    private Integer slot = 1;
}
