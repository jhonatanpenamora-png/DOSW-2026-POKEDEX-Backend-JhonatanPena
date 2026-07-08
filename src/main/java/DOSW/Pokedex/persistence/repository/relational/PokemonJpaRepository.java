package DOSW.Pokedex.persistence.repository.relational;

import DOSW.Pokedex.persistence.entity.relational.PokemonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PokemonJpaRepository extends JpaRepository<PokemonEntity, Long>, JpaSpecificationExecutor<PokemonEntity> {

    boolean existsByNationalNumber(Integer nationalNumber);

    Optional<PokemonEntity> findByNationalNumber(Integer nationalNumber);
}
