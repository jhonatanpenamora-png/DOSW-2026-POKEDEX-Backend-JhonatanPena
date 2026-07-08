package DOSW.Pokedex.persistence.repository.relational;

import DOSW.Pokedex.persistence.entity.relational.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionJpaRepository extends JpaRepository<RegionEntity, Long> {

    Optional<RegionEntity> findByName(String name);
}
