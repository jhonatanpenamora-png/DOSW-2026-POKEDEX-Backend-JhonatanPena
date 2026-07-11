package DOSW.Pokedex.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceTest {

    private static final String SECRET = "dGVzdC1zZWNyZXQta2V5LWZvci1wdXJwb3Nlcy1vZi10ZXN0aW5nLWp3dA==";
    private static final long EXPIRATION_MS = 3600000L; // 1 hour

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", SECRET);
        ReflectionTestUtils.setField(jwtService, "expirationMs", EXPIRATION_MS);
    }

    private UserDetails createUserDetails(String username) {
        return User.withUsername(username)
                .password("dummy")
                .roles("USER")
                .build();
    }

    @Test
    @DisplayName("generateToken debe crear un token JWT válido")
    void generateToken_createsValidToken() {
        UserDetails userDetails = createUserDetails("test@example.com");

        String token = jwtService.generateToken(userDetails);

        assertThat(token).isNotBlank();

        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET));
        String subject = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

        assertThat(subject).isEqualTo("test@example.com");
    }

    @Test
    @DisplayName("extractUsername debe extraer el email correcto del token")
    void extractUsername_returnsCorrectEmail() {
        UserDetails userDetails = createUserDetails("pikachu@pokemon.com");
        String token = jwtService.generateToken(userDetails);

        String extracted = jwtService.extractUsername(token);

        assertThat(extracted).isEqualTo("pikachu@pokemon.com");
    }

    @Test
    @DisplayName("isTokenValid debe retornar true para token y usuario correctos")
    void isTokenValid_whenCorrectUser_returnsTrue() {
        UserDetails userDetails = createUserDetails("ash@pokemon.com");
        String token = jwtService.generateToken(userDetails);

        boolean valid = jwtService.isTokenValid(token, userDetails);

        assertThat(valid).isTrue();
    }

    @Test
    @DisplayName("isTokenValid debe retornar false para usuario diferente")
    void isTokenValid_whenWrongUser_returnsFalse() {
        UserDetails tokenUser = createUserDetails("ash@pokemon.com");
        UserDetails otherUser = createUserDetails("misty@pokemon.com");
        String token = jwtService.generateToken(tokenUser);

        boolean valid = jwtService.isTokenValid(token, otherUser);

        assertThat(valid).isFalse();
    }

    @Test
    @DisplayName("isTokenValid debe retornar false para token expirado")
    void isTokenValid_whenExpiredToken_returnsFalse() throws Exception {
        // Create a token that expires immediately (1ms expiration)
        ReflectionTestUtils.setField(jwtService, "expirationMs", -1L);

        UserDetails userDetails = createUserDetails("ash@pokemon.com");
        String token = jwtService.generateToken(userDetails);

        // Small wait to ensure token is expired
        Thread.sleep(10);

        boolean valid = jwtService.isTokenValid(token, userDetails);

        assertThat(valid).isFalse();
    }

    @Test
    @DisplayName("isTokenValid debe retornar false para token inválido")
    void isTokenValid_whenInvalidToken_returnsFalse() {
        UserDetails userDetails = createUserDetails("ash@pokemon.com");

        boolean valid = jwtService.isTokenValid("invalid-token-here", userDetails);

        assertThat(valid).isFalse();
    }
}