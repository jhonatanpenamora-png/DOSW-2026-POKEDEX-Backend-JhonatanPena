package DOSW.Pokedex.repository;

import DOSW.Pokedex.model.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeJpaRepository extends JpaRepository<TypeEntity, Long> {

    Optional<TypeEntity> findByName(String name);
}
