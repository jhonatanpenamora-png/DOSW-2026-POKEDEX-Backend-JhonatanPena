package DOSW.Pokedex.repository;

import DOSW.Pokedex.model.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamJpaRepository extends JpaRepository<TeamEntity, Long> {

    List<TeamEntity> findByUserId(Long userId);
}
