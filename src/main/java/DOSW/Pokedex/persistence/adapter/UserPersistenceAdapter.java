package DOSW.Pokedex.persistence.adapter;

import DOSW.Pokedex.core.model.User;
import DOSW.Pokedex.core.service.interfaces.UserPersistencePort;
import DOSW.Pokedex.persistence.mapper.UserPersistenceMapper;
import DOSW.Pokedex.persistence.repository.relational.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

    private final UserJpaRepository repository;
    private final UserPersistenceMapper mapper;

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByProviderId(String providerId) {
        return repository.findByProviderId(providerId)
                .map(mapper::toDomain);
    }

    @Override
    public User save(User user) {
        var entity = mapper.toEntity(user);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
