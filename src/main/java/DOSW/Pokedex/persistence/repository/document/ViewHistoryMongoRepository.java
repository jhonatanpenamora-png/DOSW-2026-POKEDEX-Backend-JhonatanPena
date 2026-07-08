package DOSW.Pokedex.persistence.repository.document;

import DOSW.Pokedex.persistence.entity.document.ViewHistoryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ViewHistoryMongoRepository extends MongoRepository<ViewHistoryDocument, String> {

    List<ViewHistoryDocument> findByUserIdOrderByViewedAtDesc(Long userId);
}
