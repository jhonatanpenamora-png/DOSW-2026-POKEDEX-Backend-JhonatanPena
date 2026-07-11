-- V2: Add surrogate id columns for tables that had composite PKs
-- Hibernate entities expect @Id @GeneratedValue on all tables

-- pokemon_ability: was (pokemon_id, ability_id) PK, add id column
ALTER TABLE pokemon_ability DROP CONSTRAINT pokemon_ability_pkey;
ALTER TABLE pokemon_ability ADD COLUMN id BIGSERIAL PRIMARY KEY;
ALTER TABLE pokemon_ability ADD CONSTRAINT uq_pokemon_ability UNIQUE (pokemon_id, ability_id);

-- pokemon_move: was (pokemon_id, move_id, learn_method) PK, add id column
ALTER TABLE pokemon_move DROP CONSTRAINT pokemon_move_pkey;
ALTER TABLE pokemon_move ADD COLUMN id BIGSERIAL PRIMARY KEY;
ALTER TABLE pokemon_move ADD CONSTRAINT uq_pokemon_move UNIQUE (pokemon_id, move_id, learn_method);
