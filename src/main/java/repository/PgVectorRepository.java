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
        String sql = "INSERT INTO songs (artist_name, track_name, release_date, genre, features) VALUES (?, ?, ?, ?, ?::vector)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, song.getArtistName());
            stmt.setString(2, song.getTrackName());
            stmt.setDate(3, Date.valueOf(song.getReleaseDate()));
            stmt.setString(4, song.getGenre());
            stmt.setString(5, VectorUtils.vectorToString(song.getFeatures()));
            stmt.executeUpdate();
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
                results.add(new Song(rs.getString("artist_name"), rs.getString("track_name"), null, rs.getString("genre"), null));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding similar songs", e);
        }

        return results;
    }
}
