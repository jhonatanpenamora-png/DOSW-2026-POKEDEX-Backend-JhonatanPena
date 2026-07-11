package DOSW.Pokedex.repository;

import DOSW.Pokedex.model.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionJpaRepository extends JpaRepository<RegionEntity, Long> {

    Optional<RegionEntity> findByName(String name);
}
