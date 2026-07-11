package DOSW.Pokedex;

import DOSW.Pokedex.repository.PokemonViewMongoRepository;
import DOSW.Pokedex.repository.TeamStatsMongoRepository;
import DOSW.Pokedex.repository.ViewHistoryMongoRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

/**
 * Base class for integration tests that use H2 in PostgreSQL compatibility mode.
 * <p>
 * Testcontainers was initially chosen but Docker Desktop (v4.81.0, API 1.55) is
 * incompatible with docker-java bundled in Testcontainers 1.20.1.
 * </p>
 * <p>
 * H2 with {@code MODE=PostgreSQL} provides sufficient coverage — the same JPA
 * queries work against PostgreSQL in production.
 * </p>
 * <p>
 * Uses {@code WebEnvironment.NONE} (no embedded server) so {@link org.springframework.transaction.annotation.Transactional @Transactional}
 * on subclasses works correctly. Controller tests that need {@code TestRestTemplate}
 * should override via their own {@code @SpringBootTest(webEnvironment = RANDOM_PORT)}.
 * </p>
 * <p>
 * <strong>MongoDB:</strong> Three {@code MongoRepository} interfaces are mocked
 * via {@link MockBean @MockBean} so the context starts without a real MongoDB.
 * </p>
 */
@SpringBootTest
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public abstract class AbstractIntegrationTest {

    @MockBean
    private PokemonViewMongoRepository pokemonViewMongoRepository;

    @MockBean
    private TeamStatsMongoRepository teamStatsMongoRepository;

    @MockBean
    private ViewHistoryMongoRepository viewHistoryMongoRepository;
}
