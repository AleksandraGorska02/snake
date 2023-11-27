package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 15;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;

    Snake pl1 = new Snake();
    Snake pl2 = new Snake();
    private String winner;

//set p1 start position

    static int appleX;
    static int appleY;

    public static boolean running = false;
    Timer timer;
    static Random random;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.darkGray);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        pl1.x[0] = 0;
        pl1.y[0] = 0;
        pl1.direction = 'R';
        pl2.x[0] = SCREEN_WIDTH - UNIT_SIZE;
        pl2.y[0] = SCREEN_HEIGHT - UNIT_SIZE;
        pl2.direction = 'L';
        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        if (running) {
          /*
          for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);

            }

            */
            g.setColor(Color.green);
            pl1.drawHeadSnake(g);
            g.setColor(Color.yellow);
            pl1.drawBodySnake(g);
            g.setColor(Color.blue);
            pl2.drawHeadSnake(g);
            g.setColor(Color.orange);
            pl2.drawBodySnake(g);
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

        } else {
            gameOver(g);
        }
    }

    public static void newApple() {

        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;


    }

    public void move() {

        pl1.moveSnake();
        pl2.moveSnake();

    }

    public void checkApple() {
        pl1.checkAppleSnake();
        pl2.checkAppleSnake();
    }

    public void checkColisions() {

        pl1.checkCollisionsSnake();
        if (!running) {
            winner = "Player 2";
        }
        pl2.checkCollisionsSnake();
        if (!running && !Objects.equals(winner, "Player 2")) {
            winner = "Player 1";
        }
        //check if head pl1 touches pl2
        for (int i = pl2.bodyParts; i > 0; i--) {
            if ((pl1.x[0] == pl2.x[i]) && (pl1.y[0] == pl2.y[i])) {
                running = false;
                winner = "Player 2";
                break;
            }
        }
        //check if head pl2 touches pl1
        for (int i = pl1.bodyParts; i > 0; i--) {
            if ((pl2.x[0] == pl1.x[i]) && (pl2.y[0] == pl1.y[i])) {
                running = false;
                winner = "Player 1";
                break;
            }
        }
        if (!running) {
            timer.stop();
        }

    }

    public void gameOver(Graphics g) {

        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (SCREEN_WIDTH - metrics1.stringWidth("GAME OVER")) / 2, SCREEN_HEIGHT / 2);
        g.drawString("Winner: " + winner, (SCREEN_WIDTH - metrics1.stringWidth("Winner: " + winner)) / 2, SCREEN_HEIGHT / 3);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkColisions();

        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (pl1.direction != 'R') {
                        pl1.direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (pl1.direction != 'L') {
                        pl1.direction = 'R';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (pl1.direction != 'U') {
                        pl1.direction = 'D';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (pl1.direction != 'D') {
                        pl1.direction = 'U';
                    }
                    break;

                case KeyEvent.VK_A:
                    if (pl2.direction != 'R') {
                        pl2.direction = 'L';
                    }
                    break;
                case KeyEvent.VK_D:
                    if (pl2.direction != 'L') {
                        pl2.direction = 'R';
                    }
                    break;
                case KeyEvent.VK_S:
                    if (pl2.direction != 'U') {
                        pl2.direction = 'D';
                    }
                    break;
                case KeyEvent.VK_W:
                    if (pl2.direction != 'D') {
                        pl2.direction = 'U';
                    }
                    break;

            }
        }

    }

}
