package service;

import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class AiService {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "your_api_key"; // Replace with actual API key

    public String generateSearchQuery(String userInput) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
            conn.setDoOutput(true);

            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "gpt-4");
            requestBody.put("messages", new JSONObject[]{
                    new JSONObject().put("role", "system").put("content", "Convert user input into a structured query."),
                    new JSONObject().put("role", "user").put("content", userInput)
            });

            try (OutputStream os = conn.getOutputStream()) {
                os.write(requestBody.toString().getBytes(StandardCharsets.UTF_8));
            }

            InputStream responseStream = conn.getInputStream();
            Scanner scanner = new Scanner(responseStream, StandardCharsets.UTF_8);
            String response = scanner.useDelimiter("\\A").next();
            scanner.close();

            JSONObject jsonResponse = new JSONObject(response);
            return jsonResponse.getJSONArray("choices").getJSONObject(0).getString("content");

        } catch (Exception e) {
            throw new RuntimeException("Error calling AI API", e);
        }
    }
}
