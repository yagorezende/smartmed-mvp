package br.uff.lid.labgen.handlers.fabric;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ClientApp {

    public static boolean verifyEmail(String email) {
        try {
            URL url = new URL("http://localhost:9090/verifyEmail");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Build the form data body
            String formData = "email=" + URLEncoder.encode(email, StandardCharsets.UTF_8);

            // Send form data
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = formData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Read the response
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
            );
            String response = in.readLine();
            in.close();

            // Interpret "true" or "false"
            return Boolean.parseBoolean(response.trim());

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Consider handling errors more robustly
        }
    }

}
