package repository;

import model.Song;
import util.VectorUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PgVectorRepository implements SongRepository {
    private final Connection connection;

    public PgVectorRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Song song) {
        String sql = "INSERT INTO songs (id, artist_name, track_name, release_date, genre, lyrics, len, features, topic, age) VALUES (?, ?, ?, ?, ?, ?, ?, ?::vector, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, song.getId());
            stmt.setString(2, song.getArtistName());
            stmt.setString(3, song.getTrackName());
            stmt.setString(4, song.getReleaseDate());
            stmt.setString(5, song.getGenre());
            stmt.setString(6, song.getLyrics());
            stmt.setInt(7, song.getLen());
            stmt.setString(8, VectorUtils.vectorToString(song.getFeatures()));
            stmt.setString(9, song.getTopic());
            stmt.setFloat(10, song.getAge());
            stmt.executeUpdate();

            System.out.println("Song saved: " + song.getTrackName());
        } catch (SQLException e) {
            throw new RuntimeException("Error saving song", e);
        }
    }


    @Override
    public List<String> findSimilarSongs(String query) {
        List<String> results = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String artist = rs.getString("artist_name");
                String track = rs.getString("track_name");
                String date = rs.getString("release_date");

                results.add(String.format("%d: %s - %s (%s)", id, artist, track, date));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding similar songs", e);
        }



        return results;
    }
}
