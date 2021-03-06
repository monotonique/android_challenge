package news.agoda.com.sample.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOUtils {

    /**
     * Read from input stream.
     *
     * @param in
     * @return
     */
    public static String readStream(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in));) {
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(in);
        }
        return sb.toString();
    }

    /**
     * Close the input stream without throwing exception.
     *
     * @param input
     */
    public static void closeQuietly(InputStream input) {
        try {
            if (input != null) {
                input.close();
            }
        } catch (Throwable ignored) {
        }
    }

    private IOUtils() {}

}
