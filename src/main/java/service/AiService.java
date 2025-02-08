package service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class AiService {
    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";
    private static final String API_KEY = "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";  // Replace with your key
    private static final String REFERER = "<YOUR_SITE_URL>";       // Optional: Your site URL
    private static final String TITLE = "<YOUR_SITE_NAME>";        // Optional: Your site title

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public String generateQuery(String userInput) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "meta-llama/llama-3.2-90b-vision-instruct:free");

            JSONArray messages = new JSONArray();
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", userInput + "\n Please create a SINGLE SQL query based on USER_REQUEST for a PostgreSQL database with the pgvector extension where rows represent songs. Each song column has the following properties: id (which is unique and should be used to identify the song), artist_name, track_name, release_date, genre, lyrics, len, features (a 22-dimensional vector field), topic, and age. The features field contains the following 22 dimensions: 'dating', 'violence', 'world/life', 'night/time', 'shake the audience', 'family/gospel', 'romantic', 'communication', 'obscene', 'music', 'movement/places', 'light/visual perceptions', 'family/spiritual', 'like/girls', 'sadness', 'feelings', 'danceability', 'loudness', 'acousticness', 'instrumentalness', 'valence', 'energy'.\n" +
                    "first you need to assign values to each of the feature vectors and value should be from 0 to 1. then The query should use the vector field features to find the 10 closest songs to the user's input with calculating vector distance. The query should not require an exact match for fields like genre, lyrics, or topic, but should instead search for closest results. The query should leverage vector similarity search with pgvector to find the closest matches based on the user's request. IMPORTANT: The query should return the song's id, artist_name, track_name, and release_date. No additional explanations are needed for query, just the SQL query in a single message not containing any markdown or latex formats.\n" +
                    "your query should take a format like this:\n SELECT id, artist_name, track_name, release_date \n" +
                    "FROM songs \n" +
                    "ORDER BY features <-> ARRAY[\n" +
                    "    0.5, 0.1, 0.2, 0.1, 0.1, 0.1, 0.5, 0.3, 0.0, 0.2, \n" +
                    "    0.1, 0.2, 0.1, 0.2, 0.8, 0.5, 0.5, 0.2, 0.5, 0.2, \n" +
                    "    0.2, 0.1\n" +
                    "]::vector \n" +
                    "LIMIT 10;\n that you fill the Array based on how much that topic is related to what user wants.");
            messages.put(message);

            requestBody.put("messages", messages);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Authorization", "Bearer " + API_KEY)
                    .header("HTTP-Referer", REFERER)
                    .header("X-Title", TITLE)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();
            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );
            if (response.statusCode() != 200) {
                System.err.println("Error: API response code " + response.statusCode());
                return null;
            }

            String responseBody = response.body();
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONArray choices = jsonObject.getJSONArray("choices");
            if (choices.length() > 0) {
                message = choices.getJSONObject(0).getJSONObject("message");
                String content = message.getString("content");
//                System.out.println("Extracted Content:\n" + content);
                return content;
            } else {
                System.out.println("No choices found in response.");
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;  // Return null if API call fails
    }
}