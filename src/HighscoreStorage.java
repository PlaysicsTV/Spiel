import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HighscoreStorage {
    private static final String FILE_NAME = "highscores.ser";

    public static void save(Map<String, Integer> highscores) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(highscores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Integer> load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (Map<String, Integer>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new HashMap<>();
        }
    }
}