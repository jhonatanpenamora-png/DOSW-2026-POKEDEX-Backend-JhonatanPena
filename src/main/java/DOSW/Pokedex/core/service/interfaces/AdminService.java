package DOSW.Pokedex.core.service.interfaces;

import DOSW.Pokedex.core.model.User;

import java.util.List;

public interface AdminService {
    List<User> findAllUsers();
    User updateUserRole(Long userId, String newRole);
    void toggleUserEnabled(Long userId);
}
