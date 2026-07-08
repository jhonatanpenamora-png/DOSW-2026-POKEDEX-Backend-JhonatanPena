package DOSW.Pokedex.core.service.impl;

import DOSW.Pokedex.core.exception.ResourceNotFoundException;
import DOSW.Pokedex.core.model.User;
import DOSW.Pokedex.core.service.interfaces.AdminService;
import DOSW.Pokedex.core.service.interfaces.UserPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final UserPersistencePort userPort;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return List.of();
    }

    @Override
    @Transactional
    public User updateUserRole(Long userId, String newRole) {
        User user = userPort.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        User updated = user.toBuilder().role(newRole).build();
        return userPort.save(updated);
    }

    @Override
    @Transactional
    public void toggleUserEnabled(Long userId) {
        User user = userPort.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        User updated = user.toBuilder().enabled(!user.getEnabled()).build();
        userPort.save(updated);
    }
}
