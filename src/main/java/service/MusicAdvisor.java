package service;

import model.Song;
import repository.SongRepository;

import java.util.List;

public class MusicAdvisor {
    private final AiService aiService;
    private final SongRepository songRepo;

    public MusicAdvisor(AiService aiService, SongRepository songRepo) {
        this.aiService = aiService;
        this.songRepo = songRepo;
    }

    public List<Song> recommendSongs(String userInput) {
        double[] queryVector = aiService.generateQueryVector(userInput);
        return songRepo.findSimilarSongs(queryVector, 10);
    }
}
