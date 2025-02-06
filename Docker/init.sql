CREATE EXTENSION IF NOT EXISTS vector;

CREATE TABLE songs (
                       id SERIAL PRIMARY KEY,
                       artist_name TEXT,
                       track_name TEXT,
                       release_date DATE,
                       genre TEXT,
                       features VECTOR(24)  -- 24-dimensional feature vector
);
