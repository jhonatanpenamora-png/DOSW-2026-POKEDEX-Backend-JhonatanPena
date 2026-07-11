-- Regiones (entity: id, name)
INSERT INTO regions (id, name) VALUES (1, 'Kanto');
INSERT INTO regions (id, name) VALUES (2, 'Johto');

-- Tipos (entity: id, name)
INSERT INTO types (id, name) VALUES (1, 'Electric');
INSERT INTO types (id, name) VALUES (2, 'Fire');
INSERT INTO types (id, name) VALUES (3, 'Flying');
INSERT INTO types (id, name) VALUES (4, 'Water');
INSERT INTO types (id, name) VALUES (5, 'Grass');

-- Habilidades (entity: id, name, description, generation)
INSERT INTO abilities (id, name, description, generation) VALUES (1, 'Static', 'Contact may paralyze', 1);
INSERT INTO abilities (id, name, description, generation) VALUES (2, 'Lightning Rod', 'Draws electric moves', 1);
INSERT INTO abilities (id, name, description, generation) VALUES (3, 'Blaze', 'Powers up Fire moves', 1);

-- Movimientos (entity: id, name, type, power, accuracy, pp, damage_class, description)
INSERT INTO moves (id, name, type, power, accuracy, pp, damage_class, description)
VALUES (1, 'Thunder Shock', 'Electric', 40, 100, 30, 'SPECIAL', 'A shock attack');
INSERT INTO moves (id, name, type, power, accuracy, pp, damage_class, description)
VALUES (2, 'Quick Attack', 'Normal', 40, 100, 30, 'PHYSICAL', 'Lets the user move first');
INSERT INTO moves (id, name, type, power, accuracy, pp, damage_class, description)
VALUES (3, 'Flamethrower', 'Fire', 90, 100, 15, 'SPECIAL', 'Breathes fire');
INSERT INTO moves (id, name, type, power, accuracy, pp, damage_class, description)
VALUES (4, 'Ember', 'Fire', 40, 100, 25, 'SPECIAL', 'A weak fire attack');

-- Pokémon (entity: id, national_number, name, description, image_url, height, weight, base_experience, generation, has_mega, rarity_level, region_id)
INSERT INTO pokemon (id, national_number, name, description, image_url, height, weight, base_experience, generation, has_mega, rarity_level, region_id)
VALUES (1, 25, 'Pikachu', 'When it is angry, it discharges energy from its cheeks.',
        'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png',
        4, 60, 112, 1, false, 'COMUN', 1);
INSERT INTO pokemon (id, national_number, name, description, image_url, height, weight, base_experience, generation, has_mega, rarity_level, region_id)
VALUES (2, 6, 'Charizard', 'It spits fire that is hot enough to melt boulders.',
        'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png',
        17, 905, 240, 1, true, 'COMUN', 1);
INSERT INTO pokemon (id, national_number, name, description, image_url, height, weight, base_experience, generation, has_mega, rarity_level, region_id)
VALUES (3, 7, 'Squirtle', 'It shelters itself in its shell, then counterattacks.',
        'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/7.png',
        5, 90, 63, 1, false, 'COMUN', 1);
INSERT INTO pokemon (id, national_number, name, description, image_url, height, weight, base_experience, generation, has_mega, rarity_level, region_id)
VALUES (4, 172, 'Pichu', 'It is not yet skilled at storing electricity.',
        null, 3, 20, 41, 2, false, 'COMUN', 2);
INSERT INTO pokemon (id, national_number, name, description, image_url, height, weight, base_experience, generation, has_mega, rarity_level, region_id)
VALUES (5, 26, 'Raichu', 'Its tail discharges electricity into the ground.',
        null, 8, 300, 218, 1, false, 'COMUN', 1);

-- Estadísticas (entity: id, hp, attack, defense, special_attack, special_defense, speed, pokemon_id)
INSERT INTO pokemon_stats (id, hp, attack, defense, special_attack, special_defense, speed, pokemon_id)
VALUES (1, 35, 55, 40, 50, 50, 90, 1);
INSERT INTO pokemon_stats (id, hp, attack, defense, special_attack, special_defense, speed, pokemon_id)
VALUES (2, 78, 84, 78, 109, 85, 100, 2);
INSERT INTO pokemon_stats (id, hp, attack, defense, special_attack, special_defense, speed, pokemon_id)
VALUES (3, 44, 48, 65, 50, 64, 43, 3);
INSERT INTO pokemon_stats (id, hp, attack, defense, special_attack, special_defense, speed, pokemon_id)
VALUES (4, 20, 40, 15, 35, 35, 60, 4);
INSERT INTO pokemon_stats (id, hp, attack, defense, special_attack, special_defense, speed, pokemon_id)
VALUES (5, 60, 90, 55, 90, 80, 110, 5);

-- Relaciones N:M (join tables) — all pokemon already exist at this point
INSERT INTO pokemon_type (pokemon_id, type_id) VALUES (1, 1); -- Pikachu → Electric
INSERT INTO pokemon_type (pokemon_id, type_id) VALUES (2, 2); -- Charizard → Fire
INSERT INTO pokemon_type (pokemon_id, type_id) VALUES (2, 3); -- Charizard → Flying
INSERT INTO pokemon_type (pokemon_id, type_id) VALUES (3, 4); -- Squirtle → Water
INSERT INTO pokemon_type (pokemon_id, type_id) VALUES (4, 1); -- Pichu → Electric
INSERT INTO pokemon_type (pokemon_id, type_id) VALUES (5, 1); -- Raichu → Electric

INSERT INTO pokemon_ability (id, pokemon_id, ability_id, is_hidden, slot)
VALUES (1, 1, 1, false, 1); -- Pikachu → Static
INSERT INTO pokemon_ability (id, pokemon_id, ability_id, is_hidden, slot)
VALUES (2, 1, 2, true, 2); -- Pikachu → Lightning Rod
INSERT INTO pokemon_ability (id, pokemon_id, ability_id, is_hidden, slot)
VALUES (3, 2, 3, false, 1); -- Charizard → Blaze

INSERT INTO pokemon_move (id, pokemon_id, move_id, learn_method, learn_level)
VALUES (1, 1, 1, 'level-up', 1); -- Pikachu → Thunder Shock
INSERT INTO pokemon_move (id, pokemon_id, move_id, learn_method, learn_level)
VALUES (2, 1, 2, 'level-up', 1); -- Pikachu → Quick Attack
INSERT INTO pokemon_move (id, pokemon_id, move_id, learn_method, learn_level)
VALUES (3, 2, 3, 'level-up', 1); -- Charizard → Flamethrower
INSERT INTO pokemon_move (id, pokemon_id, move_id, learn_method, learn_level)
VALUES (4, 2, 4, 'level-up', 1); -- Charizard → Ember

-- Evoluciones (entity: id, pokemon_id, evolves_to_id, trigger_type, min_level, item)
INSERT INTO evolutions (id, pokemon_id, evolves_to_id, trigger_type, min_level)
VALUES (1, 4, 1, 'level-up', 1); -- Pichu → Pikachu
INSERT INTO evolutions (id, pokemon_id, evolves_to_id, trigger_type, min_level)
VALUES (2, 1, 5, 'level-up', 1); -- Pikachu → Raichu (simplified, actually thunder-stone)

-- Usuarios de prueba (entity: id, username, email, password, avatar_url, role, provider, provider_id, enabled)
INSERT INTO users (id, username, email, password, avatar_url, role, provider, provider_id, enabled)
VALUES (1, 'trainer1', 'trainer1@gmail.com', null, null, 'USER', 'google', 'google-123', true);
INSERT INTO users (id, username, email, password, avatar_url, role, provider, provider_id, enabled)
VALUES (2, 'admin1', 'admin1@gmail.com', null, null, 'ADMIN', 'google', 'google-456', true);

-- Reset identity counters after explicit ID inserts
ALTER TABLE regions ALTER COLUMN id RESTART WITH 3;
ALTER TABLE types ALTER COLUMN id RESTART WITH 6;
ALTER TABLE abilities ALTER COLUMN id RESTART WITH 4;
ALTER TABLE moves ALTER COLUMN id RESTART WITH 5;
ALTER TABLE pokemon ALTER COLUMN id RESTART WITH 6;
ALTER TABLE pokemon_stats ALTER COLUMN id RESTART WITH 6;
ALTER TABLE pokemon_ability ALTER COLUMN id RESTART WITH 4;
ALTER TABLE pokemon_move ALTER COLUMN id RESTART WITH 5;
ALTER TABLE evolutions ALTER COLUMN id RESTART WITH 3;
ALTER TABLE users ALTER COLUMN id RESTART WITH 3;
