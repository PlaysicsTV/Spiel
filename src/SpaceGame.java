import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class SpaceGame extends JPanel implements ActionListener, KeyListener {
    private static final int TILE_SIZE = 40;
    private static final int ROWS = 10;
    private static final int COLS = 15;
    private static final int INITIAL_TIMER_DELAY = 200;
    private static final int MIN_TIMER_DELAY = 60;
    private int currentTimerDelay = INITIAL_TIMER_DELAY;

    private int spaceshipY = ROWS / 2;
    private int spaceshipX = 2; // Feste X-Position für das Raumschiff
    private int score = 0;
    private Timer timer;
    private int[][] map = new int[ROWS][COLS]; // 0 = leer, 1 = Hindernis

    private String username;
    private Map<String, Integer> highscores;

    private Runnable onGameOver;

    public SpaceGame(String username, Map<String, Integer> highscores, Runnable onGameOver) {
        this.username = username;
        this.highscores = highscores;
        setFocusable(true);
        addKeyListener(this);
        setPreferredSize(new Dimension(COLS * TILE_SIZE, ROWS * TILE_SIZE));
        initMap();
        timer = new Timer(16, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateGame();
            }
        });
        timer.start();
        this.onGameOver = onGameOver;
    }

    private void initMap() {
        for (int x = 0; x < COLS; x++) {
            for (int y = 0; y < ROWS; y++) {
                map[y][x] = Math.random() < 0.1 ? 1 : 0;
            }
        }
    }

    private void updateGame() {
        score++; // Score bei jedem Tick erhöhen
        moveMapLeft();
        if (spaceshipX == 0) {
            gameOver();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Karte zeichnen
        for (int x = 0; x < COLS; x++) {
            for (int y = 0; y < ROWS; y++) {
                if (map[y][x] == 1) {
                    g.setColor(Color.GRAY);
                    g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }
        // Raumschiff zeichnen
        g.setColor(Color.RED);
        g.fillRect(spaceshipX * TILE_SIZE, spaceshipY * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        // Score anzeigen
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 10, 20);
    }

    private void setGameSpeed(int delay) {
        timer.stop();
        timer = new Timer(delay, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateGame();
            }
        });
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateGame();
    }

    private void moveMapLeft() {
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS - 1; x++) {
                map[y][x] = map[y][x + 1];
            }
            map[y][COLS - 1] = Math.random() < 0.1 ? 1 : 0;
        }
        // Kollision prüfen: Wurde das Raumschiff von einem Hindernis getroffen?
        if (map[spaceshipY][spaceshipX] == 1) {
            gameOver();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if ((key == KeyEvent.VK_W || key == KeyEvent.VK_UP) && spaceshipY > 0) {
            spaceshipY--;
        } else if ((key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) && spaceshipY < ROWS - 1) {
            spaceshipY++;
        } else if ((key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) && spaceshipX > 0) {
            spaceshipX--;
        } else if ((key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) && spaceshipX < COLS - 1) {
            spaceshipX++;
            if (score % 5 == 0 && currentTimerDelay > MIN_TIMER_DELAY) {
                currentTimerDelay -= 20;
                setGameSpeed(currentTimerDelay);
            }
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    private void gameOver() {
        timer.stop();
        highscores.put(username, Math.max(highscores.getOrDefault(username, 0), score));
        HighscoreStorage.save(highscores);

        JOptionPane.showMessageDialog(this,
                "Game Over!\nDein Score: " + score + "\nHighscore: " + highscores.get(username),
                "Game Over", JOptionPane.INFORMATION_MESSAGE);
        if (onGameOver != null) {
            onGameOver.run();
        }
    }
}