package repository;

import model.Song;
import java.util.List;

public interface SongRepository {
    void save(Song song);
    List<String> findSimilarSongs(String query);
}
