import java.util.*;

public class HighscoreManager {
    private final Map<String, Integer> highscores = new HashMap<>();

    public void addHighscore(String username, int score) {
        highscores.put(username, Math.max(highscores.getOrDefault(username, 0), score));
    }

    public List<Map.Entry<String, Integer>> getTopHighscores(int limit) {
        return highscores.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(limit)
                .toList();
    }
}