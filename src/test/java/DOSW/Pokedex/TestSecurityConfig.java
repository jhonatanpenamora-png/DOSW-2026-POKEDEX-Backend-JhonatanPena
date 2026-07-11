package DOSW.Pokedex;

import DOSW.Pokedex.security.JwtAuthFilter;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Test security configuration that disables OAuth2 and permits all requests.
 * <p>
 * Active only under the {@code test} profile with {@code @Order(0)} so it
 * takes precedence over the main {@code SecurityConfig} (default Order 100).
 * Integration tests with {@code TestRestTemplate} flow through without
 * authentication — needed for sustentación demos where OAuth2 client
 * credentials are not available.
 * </p>
 */
@TestConfiguration
@Profile("test")
@Order(0)
public class TestSecurityConfig {

    @Bean
    SecurityFilterChain testFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll());
        return http.build();
    }

    /**
     * Disables the global {@link JwtAuthFilter} during tests so it doesn't
     * intercept requests with 500 errors (it needs an active JWT session).
     */
    @Bean
    FilterRegistrationBean<JwtAuthFilter> disableJwtAuthFilter(JwtAuthFilter jwtAuthFilter) {
        FilterRegistrationBean<JwtAuthFilter> registration = new FilterRegistrationBean<>(jwtAuthFilter);
        registration.setEnabled(false);
        return registration;
    }
}
