package DOSW.Pokedex.repository;

import DOSW.Pokedex.model.AbilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AbilityJpaRepository extends JpaRepository<AbilityEntity, Long> {

    Optional<AbilityEntity> findByName(String name);
}
