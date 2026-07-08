package DOSW.Pokedex.controller.dto.response;

public record TokenResponse(
    String token,
    String type,
    String email,
    String role,
    Long expiresIn
) {}
