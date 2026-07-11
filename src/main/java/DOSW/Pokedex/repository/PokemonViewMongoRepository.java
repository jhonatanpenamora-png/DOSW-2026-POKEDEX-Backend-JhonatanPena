package DOSW.Pokedex.repository;

import DOSW.Pokedex.model.PokemonViewDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PokemonViewMongoRepository extends MongoRepository<PokemonViewDocument, String> {

    Optional<PokemonViewDocument> findByPokemonId(Long pokemonId);
}
