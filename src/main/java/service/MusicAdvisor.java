package service;

import model.Song;
import repository.SongRepository;
import util.VectorUtils;

import java.util.List;

public class MusicAdvisor {
    private final AiService aiService;
    private final SongRepository songRepo;

    public MusicAdvisor(AiService aiService, SongRepository songRepo) {
        this.aiService = aiService;
        this.songRepo = songRepo;
    }

    public List<String> recommendSongs(String userInput) {
        String query = aiService.generateQuery(userInput);
        return songRepo.findSimilarSongs(query);
    }
}
