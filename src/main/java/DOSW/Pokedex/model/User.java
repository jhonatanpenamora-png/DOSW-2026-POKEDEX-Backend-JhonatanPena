package DOSW.Pokedex.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class User {
    Long id;
    String username;
    String email;
    String password;
    String avatarUrl;
    String role;
    String provider;
    String providerId;
    Boolean enabled;
}
