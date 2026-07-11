package DOSW.Pokedex.repository;

import DOSW.Pokedex.model.TeamStatsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TeamStatsMongoRepository extends MongoRepository<TeamStatsDocument, String> {

    Optional<TeamStatsDocument> findByPokemonId(Long pokemonId);
}
