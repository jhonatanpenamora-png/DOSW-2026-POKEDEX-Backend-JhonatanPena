package DOSW.Pokedex.core.service.interfaces;

import DOSW.Pokedex.core.model.User;

public interface AuthService {
    User processOAuth2User(String email, String name, String avatarUrl, String providerId);
    User findById(Long id);
    User findByEmail(String email);
}
