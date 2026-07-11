package DOSW.Pokedex.repository;

import DOSW.Pokedex.model.MoveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MoveJpaRepository extends JpaRepository<MoveEntity, Long> {

    Optional<MoveEntity> findByName(String name);
}
