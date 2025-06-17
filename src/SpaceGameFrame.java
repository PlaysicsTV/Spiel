import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class SpaceGameFrame extends JFrame {
    private String username;
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);
    private MainMenuPanel mainMenuPanel;
    private SpaceGame spaceGame;
    private Map<String, Integer> highscores = HighscoreStorage.load();

    public SpaceGameFrame() {
        setTitle("Raumschiff-Spiel");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        UsernamePanel usernamePanel = new UsernamePanel(this::onUsernameEntered);
        mainPanel.add(usernamePanel, "username");
        add(mainPanel);

        pack(); // Fenstergröße an Inhalt anpassen
        setLocationRelativeTo(null); // Fenster zentrieren
        setVisible(true);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                HighscoreStorage.save(highscores);
            }
        });
    }

    private void onUsernameEntered(String name) {
        this.username = name;
        mainMenuPanel = new MainMenuPanel(username, this::startGame, highscores);
        mainPanel.add(mainMenuPanel, "menu");
        cardLayout.show(mainPanel, "menu");
        pack(); // Größe nach Panel-Wechsel anpassen
    }

    private void startGame() {
        spaceGame = new SpaceGame(username, highscores, this::showMenu);
        mainPanel.add(spaceGame, "game");
        cardLayout.show(mainPanel, "game");
        spaceGame.requestFocusInWindow();
        pack(); // Größe nach Panel-Wechsel anpassen
    }

    private void showMenu() {
        cardLayout.show(mainPanel, "menu");
        pack(); // Größe nach Panel-Wechsel anpassen
    }
}