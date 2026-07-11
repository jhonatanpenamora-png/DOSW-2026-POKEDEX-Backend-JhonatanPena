package DOSW.Pokedex.repository;

import DOSW.Pokedex.model.ViewHistoryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ViewHistoryMongoRepository extends MongoRepository<ViewHistoryDocument, String> {

    List<ViewHistoryDocument> findByUserIdOrderByViewedAtDesc(Long userId);
}
