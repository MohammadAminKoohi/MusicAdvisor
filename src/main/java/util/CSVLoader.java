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

public class CSVLoader {
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

                double[] features = new double[22];
                for (int i = 0; i < 22; i++) {
                    features[i] = Double.parseDouble(record.get("feature" + (i + 1)));
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
