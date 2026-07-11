package DOSW.Pokedex.repository;

import DOSW.Pokedex.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByProviderId(String providerId);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
