package util;

import java.util.Arrays;

public class VectorUtils {

    // Converts a vector (double array) to a PostgreSQL vector string format
    public static String vectorToString(double[] vector) {
        return Arrays.toString(vector).replace("[", "").replace("]", "");
    }

    // Converts a PostgreSQL vector string format back to a double array
    public static double[] stringToVector(String vectorStr) {
        return Arrays.stream(vectorStr.split(","))
                .mapToDouble(Double::parseDouble)
                .toArray();
    }

    // Extracts numerical features from AI-generated query (Assume normalized values)
    public static double[] extractVector(String query) {
        // Example: AI returns comma-separated values like "0.5,0.2,0.8,..."
        return stringToVector(query);
    }
}
