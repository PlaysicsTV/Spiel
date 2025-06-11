import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SpaceGame extends JPanel {

    int x = 5;
    int y = 5;
    final int SIZE = 10;

    public SpaceGame() {
        setPreferredSize(new Dimension(300, 300));
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> y = Math.max(0, y - 1);
                    case KeyEvent.VK_S -> y = Math.min(SIZE - 1, y + 1);
                    case KeyEvent.VK_A -> x = Math.max(0, x - 1);
                    case KeyEvent.VK_D -> x = Math.min(SIZE - 1, x + 1);
                }
                repaint();
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellSize = getWidth() / SIZE;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                g.drawRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }
        g.drawString("🚀", x * cellSize + 10, y * cellSize + 20);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Raumschiff-Spiel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new SpaceGame());
        frame.pack();
        frame.setVisible(true);
    }
}
