package DOSW.Pokedex.persistence.entity.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "team_stats")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamStatsDocument {

    @Id
    private String id;

    @Field("pokemon_id")
    private Long pokemonId;

    @Field("pokemon_name")
    private String pokemonName;

    @Field("team_count")
    @Builder.Default
    private Long teamCount = 0L;

    @Field("last_updated")
    private LocalDateTime lastUpdated;
}
