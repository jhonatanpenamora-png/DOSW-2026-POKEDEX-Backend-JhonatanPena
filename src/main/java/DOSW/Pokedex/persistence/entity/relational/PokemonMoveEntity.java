package DOSW.Pokedex.persistence.entity.relational;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pokemon_move", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"pokemon_id", "move_id", "learn_method"})
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PokemonMoveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pokemon_id", nullable = false)
    private PokemonEntity pokemon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "move_id", nullable = false)
    private MoveEntity move;

    @Column(name = "learn_method", nullable = false, length = 30)
    private String learnMethod;

    @Column(name = "learn_level")
    private Integer learnLevel;
}
