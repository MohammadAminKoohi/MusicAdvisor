package main;

import repository.PgVectorRepository;
import service.AIService;
import service.MusicAdvisor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/musicdb", "user", "password");
        PgVectorRepository repo = new PgVectorRepository(conn);
        AIService ai = new AIService();
        MusicAdvisor advisor = new MusicAdvisor(ai, repo);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Describe the kind of music you like:");
        String userInput = scanner.nextLine();

        List<Song> recommendations = advisor.recommendSongs(userInput);
        recommendations.forEach(System.out::println);
    }
}
