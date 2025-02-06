package model;

import java.time.LocalDate;
import java.util.Arrays;

public class Song {
    private String artistName;
    private String trackName;
    private LocalDate releaseDate;
    private String genre;
    private double[] features;  // Numeric attributes as a vector

    public Song(String artistName, String trackName, LocalDate releaseDate, String genre, double[] features) {
        this.artistName = artistName;
        this.trackName = trackName;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.features = features;
    }

    public String getArtistName() { return artistName; }
    public String getTrackName() { return trackName; }
    public LocalDate getReleaseDate() { return releaseDate; }
    public String getGenre() { return genre; }
    public double[] getFeatures() { return features; }

    @Override
    public String toString() {
        return "Song{" + "artist='" + artistName + "', track='" + trackName + "', genre='" + genre + "'}";
    }
}
