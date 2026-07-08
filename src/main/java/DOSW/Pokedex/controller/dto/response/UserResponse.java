package DOSW.Pokedex.controller.dto.response;

public record UserResponse(
    Long id,
    String username,
    String email,
    String avatarUrl,
    String role,
    String provider,
    Boolean enabled
) {}
