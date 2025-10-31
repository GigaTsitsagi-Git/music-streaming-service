DROP TABLE IF EXISTS session_played_songs;
DROP TABLE IF EXISTS streaming_sessions;
DROP TABLE IF EXISTS playlist_items;
DROP TABLE IF EXISTS playlists;
DROP TABLE IF EXISTS song_genres;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS songs;
DROP TABLE IF EXISTS albums;
DROP TABLE IF EXISTS artists;
DROP TABLE IF EXISTS user_subscriptions;
DROP TABLE IF EXISTS subscription_plans;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_date DATE NOT NULL,
    created_datetime TIMESTAMP NOT NULL
);

CREATE TABLE artists (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(200) NOT NULL UNIQUE
);

CREATE TABLE albums (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    artist_id BIGINT NOT NULL,
    CONSTRAINT fk_albums_artist FOREIGN KEY (artist_id) REFERENCES artists(id) ON DELETE RESTRICT,
    CONSTRAINT uq_album UNIQUE (title, artist_id)
);

CREATE TABLE songs (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    artist_id BIGINT NOT NULL,
    album_id BIGINT NOT NULL,
    CONSTRAINT fk_songs_artist FOREIGN KEY (artist_id) REFERENCES artists(id) ON DELETE RESTRICT,
    CONSTRAINT fk_songs_album FOREIGN KEY (album_id) REFERENCES albums(id) ON DELETE CASCADE,
    CONSTRAINT uq_song UNIQUE (title, artist_id, album_id)
);

CREATE TABLE genres (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE song_genres (
    song_id BIGINT NOT NULL,
    genre_id BIGINT NOT NULL,
    PRIMARY KEY (song_id, genre_id),
    CONSTRAINT fk_song_genres_song FOREIGN KEY (song_id) REFERENCES songs(id) ON DELETE CASCADE,
    CONSTRAINT fk_song_genres_genre FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE
);

CREATE TABLE playlists (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    owner_user_id BIGINT NOT NULL,
    CONSTRAINT fk_playlists_owner FOREIGN KEY (owner_user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT uq_playlist_owner_name UNIQUE (owner_user_id, name)
);

CREATE TABLE playlist_items (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    playlist_id BIGINT NOT NULL,
    song_id BIGINT NOT NULL,
    position INT NOT NULL,
    CONSTRAINT fk_playlist_items_playlist FOREIGN KEY (playlist_id) REFERENCES playlists(id) ON DELETE CASCADE,
    CONSTRAINT fk_playlist_items_song FOREIGN KEY (song_id) REFERENCES songs(id) ON DELETE RESTRICT,
    CONSTRAINT uq_playlist_position UNIQUE (playlist_id, position)
);

CREATE TABLE subscription_plans (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    price DECIMAL(10,2) NOT NULL,
    CONSTRAINT chk_plan_price CHECK (price >= 0)
);

CREATE TABLE user_subscriptions (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    plan_id BIGINT NOT NULL,
    started_at TIMESTAMP NOT NULL,
    ended_at TIMESTAMP NULL,
    CONSTRAINT fk_user_subscriptions_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_subscriptions_plan FOREIGN KEY (plan_id) REFERENCES subscription_plans(id) ON DELETE RESTRICT,
    CONSTRAINT uq_active_subscription UNIQUE (user_id, plan_id, started_at)
);

CREATE TABLE streaming_sessions (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    current_song_id BIGINT NULL,
    started_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_streaming_sessions_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_streaming_sessions_current_song FOREIGN KEY (current_song_id) REFERENCES songs(id) ON DELETE SET NULL
);

CREATE TABLE session_played_songs (
    session_id BIGINT NOT NULL,
    position INT NOT NULL,
    song_id BIGINT NOT NULL,
    PRIMARY KEY (session_id, position),
    CONSTRAINT fk_session_history_session FOREIGN KEY (session_id) REFERENCES streaming_sessions(id) ON DELETE CASCADE,
    CONSTRAINT fk_session_history_song FOREIGN KEY (song_id) REFERENCES songs(id) ON DELETE RESTRICT
);




