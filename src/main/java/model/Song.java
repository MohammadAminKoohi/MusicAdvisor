package model;

import java.time.LocalDate;
import java.util.Arrays;

public class Song {
    private Integer id;
    private String artistName;
    private String trackName;
    private String releaseDate;
    private String genre;
    private String lyrics;
    private Integer len;
    private double[] features;  // Numeric attributes as a vector
    private String topic;
    private Float age;

    public Song(Integer id, String artistName, String trackName, String releaseDate, String genre, String lyrics, Integer len, double[] features, String topic, Float age) {
        this.id = id;
        this.artistName = artistName;
        this.trackName = trackName;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.lyrics = lyrics;
        this.len = len;
        this.features = features;
        this.topic = topic;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public String getLyrics() {
        return lyrics;
    }

    public Integer getLen() {
        return len;
    }

    public String getTopic() {
        return topic;
    }

    public Float getAge() {
        return age;
    }

    public double[] getFeatures() { return features; }

    @Override
    public String toString() {
        return "Song{" + "artist='" + artistName + "', track='" + trackName + "', genre='" + genre + "'}";
    }
}
