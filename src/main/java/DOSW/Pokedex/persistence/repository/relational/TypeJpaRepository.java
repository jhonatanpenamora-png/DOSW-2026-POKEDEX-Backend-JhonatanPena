package DOSW.Pokedex.persistence.repository.relational;

import DOSW.Pokedex.persistence.entity.relational.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeJpaRepository extends JpaRepository<TypeEntity, Long> {

    Optional<TypeEntity> findByName(String name);
}
