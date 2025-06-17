import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MainMenuPanel extends JPanel {
    public MainMenuPanel(String username, Runnable onStartGame, Map<String, Integer> highscores) {
        setLayout(new BorderLayout());
        JLabel userLabel = new JLabel("Benutzer: " + username);
        add(userLabel, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(2, 1));
        center.add(new HighscorePanel(highscores));
        JButton playButton = new JButton("Spielen");
        playButton.addActionListener(e -> onStartGame.run());
        center.add(playButton);
        add(center, BorderLayout.CENTER);
    }
}