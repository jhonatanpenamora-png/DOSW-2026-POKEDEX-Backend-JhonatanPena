package DOSW.Pokedex.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "view_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewHistoryDocument {

    @Id
    private String id;

    @Indexed
    @Field("user_id")
    private Long userId;

    @Field("pokemon_id")
    private Long pokemonId;

    @Field("pokemon_name")
    private String pokemonName;

    @Field("pokemon_image")
    private String pokemonImage;

    @Indexed
    @Field("viewed_at")
    private LocalDateTime viewedAt;
}
