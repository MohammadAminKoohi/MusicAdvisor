package util;

import model.Song;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import repository.SongRepository;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



import model.Song;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import repository.SongRepository;
import java.io.FileReader;
import java.io.IOException;

public class CSVLoader {
    private static final String[] FEATURE_NAMES = {
            "dating", "violence", "world/life", "night/time", "shake the audience",
            "family/gospel", "romantic", "communication", "obscene", "music",
            "movement/places", "light/visual perceptions", "family/spiritual",
            "like/girls", "sadness", "feelings", "danceability", "loudness",
            "acousticness", "instrumentalness", "valence", "energy"
    };

    public static void loadSongs(String csvFilePath, SongRepository repo) {
        try (FileReader reader = new FileReader(csvFilePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                Integer id = Integer.parseInt(record.get("id"));
                String artist = record.get("artist_name");
                String track = record.get("track_name");
                String date = record.get("release_date");
                String genre = record.get("genre");
                String lyrics = record.get("lyrics");
                Integer len = Integer.parseInt(record.get("len"));

                // Extract features dynamically
                double[] features = new double[FEATURE_NAMES.length];
                for (int i = 0; i < FEATURE_NAMES.length; i++) {
                    features[i] = Double.parseDouble(record.get(FEATURE_NAMES[i]));
                }

                String topic = record.get("topic");
                Float age = Float.parseFloat(record.get("age"));

                repo.save(new Song(id, artist, track, date, genre, lyrics, len, features, topic, age));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
