package util;

import model.Song;
import repository.SongRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;

public class CSVLoader {
    public static void loadSongs(String csvFilePath, SongRepository repo) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String artist = data[0];
                String track = data[1];
                String date = data[2];
                String genre = data[3];

                double[] features = new double[data.length - 8];
                for (int i = 6; i < 28; i++) {
                    features[i-6] = Double.parseDouble(data[i]);
                }
                repo.save(new Song(artist, track,date, genre, features));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
