CREATE TABLE artists (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    artist_type VARCHAR(10) NOT NULL CHECK (artist_type IN ('SOLO', 'BAND'))
);

CREATE TABLE albums (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    release_date DATE NOT NULL,
    cover_image_key VARCHAR(500)
);

CREATE TABLE album_artists (
    id BIGSERIAL PRIMARY KEY,
    album_id BIGINT NOT NULL REFERENCES albums(id) ON DELETE CASCADE,
    artist_id BIGINT NOT NULL REFERENCES artists(id) ON DELETE CASCADE,
    is_main_singer BOOLEAN DEFAULT FALSE,
    UNIQUE(album_id, artist_id)
);

CREATE TABLE regionals (
    id INTEGER PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);