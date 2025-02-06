package service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class AiService {
    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";
    private static final String API_KEY = "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";  // Replace with your key
    private static final String REFERER = "<YOUR_SITE_URL>";       // Optional: Your site URL
    private static final String TITLE = "<YOUR_SITE_NAME>";        // Optional: Your site title

    /**
     * Sends the user input to OpenRouter AI and retrieves a structured query for the database.
     * @param userInput The text input from the user.
     * @return A query string to use in the database.
     */
    public String generateQuery(String userInput) {
        try {
            // Create the JSON request body
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "deepseek/deepseek-r1:free");

            // Create the "messages" array
            JSONArray messages = new JSONArray();
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", "Convert this user request into a structured query: " + userInput);
            messages.put(message);

            requestBody.put("messages", messages);

            // Open HTTP connection
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
            connection.setRequestProperty("HTTP-Referer", REFERER);
            connection.setRequestProperty("X-Title", TITLE);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Send request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Read response
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                System.err.println("Error: API response code " + responseCode);
                return null;
            }

            Scanner scanner = new Scanner(connection.getInputStream(), "utf-8");
            String response = scanner.useDelimiter("\\A").next();
            System.out.println("Response: " + response);
            System.out.println("--------------------------");
            scanner.close();

            // Parse JSON response
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray choices = jsonResponse.getJSONArray("choices");
            if (choices.length() > 0) {
                return choices.getJSONObject(0).getJSONObject("message").getString("content");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;  // Return null if API call fails
    }
}
