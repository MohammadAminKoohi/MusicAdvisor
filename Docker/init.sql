CREATE EXTENSION IF NOT EXISTS vector;

CREATE TABLE songs (
                       id SERIAL PRIMARY KEY,
                       artist_name TEXT,
                       track_name TEXT,
                       release_date TEXt,
                       genre TEXT,
                       lyrics TEXT,
                       len INT,
                       features VECTOR(22)  ,
                       topic TEXT ,
                       age FLOAT
);
