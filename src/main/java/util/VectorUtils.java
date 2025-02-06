package util;

import java.util.Arrays;

public class VectorUtils {

    // Converts a vector (double array) to a PostgreSQL vector string format
    public static String vectorToString(double[] vector) {
        return Arrays.toString(vector);
    }

    // Converts a PostgreSQL vector string format back to a double array
    public static double[] stringToVector(String vectorStr) {
        if (vectorStr == null || vectorStr.isEmpty()) {
            return new double[0];
        }
        return Arrays.stream(vectorStr.split(","))
                .mapToDouble(Double::parseDouble)
                .toArray();
    }

    public static double[] parseVector(String vectorString) {
        // Remove the surrounding brackets
        vectorString = vectorString.replaceAll("[\\[\\]]", "");

        // Split by commas
        String[] values = vectorString.split(",");

        // Convert to double array
        double[] vector = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            vector[i] = Double.parseDouble(values[i].trim());
        }

        return vector;
    }

    // Extracts numerical features from AI-generated query (Assume normalized values)
    public static double[] extractVector(String query) {
        // Example: AI returns comma-separated values like "0.5,0.2,0.8,..."
        return stringToVector(query);
    }
}
