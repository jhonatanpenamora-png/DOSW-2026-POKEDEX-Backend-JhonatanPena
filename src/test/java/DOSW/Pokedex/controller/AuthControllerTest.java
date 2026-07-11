package DOSW.Pokedex.controller;

import DOSW.Pokedex.dto.response.UserResponse;
import DOSW.Pokedex.mapper.UserDtoMapper;
import DOSW.Pokedex.model.User;
import DOSW.Pokedex.service.AuthService;
import DOSW.Pokedex.repository.UserJpaRepository;
import DOSW.Pokedex.security.JwtService;
import DOSW.Pokedex.security.UserDetailsServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = AuthController.class, excludeAutoConfiguration = {
        SecurityAutoConfiguration.class,
        SecurityFilterAutoConfiguration.class,
        OAuth2ClientAutoConfiguration.class
})
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private UserDtoMapper mapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserJpaRepository userPersistencePort;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    private User authenticatedUser;

    @BeforeEach
    void setUp() {
        authenticatedUser = User.builder()
                .id(1L)
                .username("ashketchum")
                .email("ash@pokemon.com")
                .avatarUrl("https://example.com/avatar.png")
                .role("USER")
                .provider("google")
                .enabled(true)
                .build();

        Authentication auth = mock(Authentication.class);
        when(auth.isAuthenticated()).thenReturn(true);
        when(auth.getName()).thenReturn("ash@pokemon.com");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        when(authService.findByEmail("ash@pokemon.com")).thenReturn(authenticatedUser);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("GET /v1/auth/me debe retornar 200 con datos del usuario autenticado")
    void testMe_returns200() throws Exception {
        UserResponse response = new UserResponse(
                1L, "ashita", "ash@pokemon.com",
                "https://example.com/avatar.png", "USER",
                "google", true
        );

        when(mapper.toResponse(authenticatedUser)).thenReturn(response);

        mockMvc.perform(get("/v1/auth/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("ash@pokemon.com"))
                .andExpect(jsonPath("$.username").value("ashita"))
                .andExpect(jsonPath("$.role").value("USER"))
                .andExpect(jsonPath("$.enabled").value(true));
    }

    @Test
    @DisplayName("GET /v1/auth/me debe retornar el UserResponse mapeado correctamente")
    void testMe_returnsMappedUserResponse() throws Exception {
        UserResponse expectedResponse = new UserResponse(
                1L, "ashita", "ash@pokemon.com",
                "https://example.com/avatar.png", "USER",
                "google", true
        );

        when(mapper.toResponse(authenticatedUser)).thenReturn(expectedResponse);

        mockMvc.perform(get("/v1/auth/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.provider").value("google"))
                .andExpect(jsonPath("$.avatarUrl").value("https://example.com/avatar.png"));
    }
}
