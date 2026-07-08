package DOSW.Pokedex.core.service.interfaces;

import DOSW.Pokedex.core.model.User;

import java.util.Optional;

public interface UserPersistencePort {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByProviderId(String providerId);
    User save(User user);
    boolean existsByEmail(String email);
}
