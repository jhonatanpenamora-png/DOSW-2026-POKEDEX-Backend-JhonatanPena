package DOSW.Pokedex.persistence.repository.relational;

import DOSW.Pokedex.persistence.entity.relational.AbilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AbilityJpaRepository extends JpaRepository<AbilityEntity, Long> {

    Optional<AbilityEntity> findByName(String name);
}
