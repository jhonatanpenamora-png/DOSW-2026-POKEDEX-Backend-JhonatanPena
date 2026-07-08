package DOSW.Pokedex.persistence.repository.relational;

import DOSW.Pokedex.persistence.entity.relational.MoveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MoveJpaRepository extends JpaRepository<MoveEntity, Long> {

    Optional<MoveEntity> findByName(String name);
}
