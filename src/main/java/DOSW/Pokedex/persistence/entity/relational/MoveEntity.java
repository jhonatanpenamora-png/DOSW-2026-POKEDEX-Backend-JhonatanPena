package DOSW.Pokedex.persistence.entity.relational;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "moves")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MoveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
    private String type;

    private Integer power;

    private Integer accuracy;

    private Integer pp;

    @Column(name = "damage_class", length = 20)
    private String damageClass;

    @Column(columnDefinition = "TEXT")
    private String description;
}
