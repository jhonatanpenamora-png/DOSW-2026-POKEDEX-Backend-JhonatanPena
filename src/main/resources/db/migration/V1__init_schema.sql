CREATE TABLE IF NOT EXISTS regions (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS types (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS abilities (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    generation INTEGER
);

CREATE TABLE IF NOT EXISTS moves (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    type VARCHAR(50) NOT NULL,
    power INTEGER,
    accuracy INTEGER,
    pp INTEGER,
    damage_class VARCHAR(20),
    description TEXT
);

CREATE TABLE IF NOT EXISTS pokemon (
    id BIGSERIAL PRIMARY KEY,
    national_number INTEGER NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    image_url VARCHAR(500),
    height INTEGER,
    weight INTEGER,
    base_experience INTEGER,
    generation INTEGER NOT NULL,
    has_mega BOOLEAN NOT NULL DEFAULT FALSE,
    rarity_level VARCHAR(30) DEFAULT 'COMUN',
    region_id BIGINT REFERENCES regions(id),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_pokemon_national_number ON pokemon(national_number);
CREATE INDEX idx_pokemon_name ON pokemon(name);
CREATE INDEX idx_pokemon_generation ON pokemon(generation);
CREATE INDEX idx_pokemon_region_id ON pokemon(region_id);
CREATE INDEX idx_pokemon_rarity ON pokemon(rarity_level);

CREATE TABLE IF NOT EXISTS pokemon_type (
    pokemon_id BIGINT NOT NULL REFERENCES pokemon(id) ON DELETE CASCADE,
    type_id BIGINT NOT NULL REFERENCES types(id) ON DELETE CASCADE,
    slot INTEGER NOT NULL DEFAULT 1,
    PRIMARY KEY (pokemon_id, type_id)
);

CREATE TABLE IF NOT EXISTS pokemon_stats (
    id BIGSERIAL PRIMARY KEY,
    pokemon_id BIGINT NOT NULL UNIQUE REFERENCES pokemon(id) ON DELETE CASCADE,
    hp INTEGER NOT NULL DEFAULT 0,
    attack INTEGER NOT NULL DEFAULT 0,
    defense INTEGER NOT NULL DEFAULT 0,
    special_attack INTEGER NOT NULL DEFAULT 0,
    special_defense INTEGER NOT NULL DEFAULT 0,
    speed INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS pokemon_ability (
    pokemon_id BIGINT NOT NULL REFERENCES pokemon(id) ON DELETE CASCADE,
    ability_id BIGINT NOT NULL REFERENCES abilities(id) ON DELETE CASCADE,
    is_hidden BOOLEAN NOT NULL DEFAULT FALSE,
    slot INTEGER NOT NULL DEFAULT 1,
    PRIMARY KEY (pokemon_id, ability_id)
);

CREATE TABLE IF NOT EXISTS evolutions (
    id BIGSERIAL PRIMARY KEY,
    pokemon_id BIGINT NOT NULL REFERENCES pokemon(id) ON DELETE CASCADE,
    evolves_to_id BIGINT NOT NULL REFERENCES pokemon(id) ON DELETE CASCADE,
    trigger_type VARCHAR(30) NOT NULL,
    min_level INTEGER,
    item VARCHAR(100)
);

CREATE INDEX idx_evolutions_pokemon_id ON evolutions(pokemon_id);
CREATE INDEX idx_evolutions_evolves_to ON evolutions(evolves_to_id);

CREATE TABLE IF NOT EXISTS pokemon_move (
    pokemon_id BIGINT NOT NULL REFERENCES pokemon(id) ON DELETE CASCADE,
    move_id BIGINT NOT NULL REFERENCES moves(id) ON DELETE CASCADE,
    learn_method VARCHAR(30) NOT NULL DEFAULT 'LEVEL_UP',
    learn_level INTEGER,
    PRIMARY KEY (pokemon_id, move_id, learn_method)
);

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255),
    avatar_url VARCHAR(500),
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    provider VARCHAR(20),
    provider_id VARCHAR(255),
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_users_provider ON users(provider, provider_id);

CREATE TABLE IF NOT EXISTS teams (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_teams_user_id ON teams(user_id);

CREATE TABLE IF NOT EXISTS team_pokemon (
    id BIGSERIAL PRIMARY KEY,
    team_id BIGINT NOT NULL REFERENCES teams(id) ON DELETE CASCADE,
    pokemon_id BIGINT NOT NULL REFERENCES pokemon(id) ON DELETE CASCADE,
    slot_position INTEGER NOT NULL CHECK (slot_position BETWEEN 1 AND 6)
);

CREATE UNIQUE INDEX idx_team_pokemon_slot ON team_pokemon(team_id, slot_position);
CREATE INDEX idx_team_pokemon_pokemon_id ON team_pokemon(pokemon_id);

CREATE TABLE IF NOT EXISTS favorites (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    pokemon_id BIGINT NOT NULL REFERENCES pokemon(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE (user_id, pokemon_id)
);

CREATE INDEX idx_favorites_user_id ON favorites(user_id);

CREATE TABLE IF NOT EXISTS audit_log (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    action VARCHAR(50) NOT NULL,
    entity_type VARCHAR(50) NOT NULL,
    entity_id BIGINT,
    details TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_audit_log_user_id ON audit_log(user_id);
CREATE INDEX idx_audit_log_entity ON audit_log(entity_type, entity_id);
CREATE INDEX idx_audit_log_created_at ON audit_log(created_at);
