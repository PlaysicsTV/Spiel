import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class HighscorePanel extends JPanel {
    private final JButton highscoreButton = new JButton("Highscore anzeigen");
    private final Map<String, Integer> highscores;

    public HighscorePanel(Map<String, Integer> highscores) {
        this.highscores = highscores;
        setLayout(new FlowLayout());
        add(highscoreButton);

        highscoreButton.addActionListener(e -> showHighscores());
    }

    private void showHighscores() {
        // Sortiere nach Score absteigend, max. 5 Einträge, jeder Name nur einmal
        List<Map.Entry<String, Integer>> top5 = highscores.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(5)
                .toList();

        StringBuilder sb = new StringBuilder("<html><h2>Top 5 Highscores</h2>");
        for (var entry : top5) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("<br>");
        }
        sb.append("</html>");

        JOptionPane.showMessageDialog(this, sb.toString(), "Highscores", JOptionPane.INFORMATION_MESSAGE);
    }
}