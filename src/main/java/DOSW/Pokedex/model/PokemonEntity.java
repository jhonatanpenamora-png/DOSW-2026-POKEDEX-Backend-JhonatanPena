package DOSW.Pokedex.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pokemon", indexes = {
    @Index(name = "idx_pokemon_national_number", columnList = "national_number")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PokemonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "national_number", nullable = false, unique = true)
    private Integer nationalNumber;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    private Integer height;

    private Integer weight;

    @Column(name = "base_experience")
    private Integer baseExperience;

    @Column(nullable = false)
    private Integer generation;

    @Column(name = "has_mega", nullable = false)
    @Builder.Default
    private Boolean hasMega = false;

    @Column(name = "rarity_level", length = 30)
    @Builder.Default
    private String rarityLevel = "COMUN";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private RegionEntity region;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "pokemon_type",
        joinColumns = @JoinColumn(name = "pokemon_id"),
        inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    @Builder.Default
    private List<TypeEntity> types = new ArrayList<>();

    @OneToOne(mappedBy = "pokemon", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private PokemonStatsEntity stats;

    @OneToMany(mappedBy = "pokemon", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PokemonAbilityEntity> abilities = new ArrayList<>();

    @OneToMany(mappedBy = "pokemon", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<EvolutionEntity> evolutions = new ArrayList<>();

    @OneToMany(mappedBy = "pokemon", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<PokemonMoveEntity> moves = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
