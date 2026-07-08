package DOSW.Pokedex.persistence.repository.document;

import DOSW.Pokedex.persistence.entity.document.TeamStatsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TeamStatsMongoRepository extends MongoRepository<TeamStatsDocument, String> {

    Optional<TeamStatsDocument> findByPokemonId(Long pokemonId);
}
