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
    public List<Song> findSimilarSongs(double[] queryVector, int limit) {
        String sql = "SELECT artist_name, track_name, genre FROM songs ORDER BY features <-> ?::vector LIMIT ?";
        List<Song> results = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, VectorUtils.vectorToString(queryVector));
            stmt.setInt(2, limit);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(new Song(Integer.parseInt(rs.getString("id")), rs.getString("artist_name"), rs.getString("track_name"), rs.getString("release_date"), rs.getString("genre"), rs.getString("lyrics"), rs.getInt("len"), VectorUtils.parseVector(rs.getString("features")), rs.getString("topic"), Float.parseFloat(rs.getString("age"))));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding similar songs", e);
        }

        return results;
    }
}
