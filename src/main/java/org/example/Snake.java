package org.example;

import java.awt.*;

import static org.example.GamePanel.GAME_UNITS;
import static org.example.GamePanel.UNIT_SIZE;

public class Snake {
    int bodyParts = 6;
    int appleEaten;
    public char direction = 'R';
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];


    public void drawHeadSnake(Graphics g) {
        for (int i = 0; i < this.bodyParts; i++) {
            if (i == 0) {

                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }
    }
    public void drawBodySnake(Graphics g) {
        for (int i = 0; i < this.bodyParts; i++) {
            if (i == 0) {
continue;
            } else {

                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }
    }

    public void moveSnake() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkAppleSnake() {
        if ((x[0] == GamePanel.appleX) && (y[0] == GamePanel.appleY)) {
            bodyParts++;
            appleEaten++;
            GamePanel.newApple();
        }

    }

    public void checkCollisionsSnake() {
        //checks if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                GamePanel.running = false;
                break;
            }
        }
        //check if head touches left border
        if (x[0] < 0) {
            GamePanel.running = false;
        }
        //check if head touches right border
        if (x[0] > GamePanel.SCREEN_WIDTH) {
            GamePanel.running = false;
        }
        //check if head touches top border
        if (y[0] < 0) {
            GamePanel.running = false;
        }
        //check if head touches bottom border
        if (y[0] > GamePanel.SCREEN_HEIGHT) {
            GamePanel.running = false;
        }

    }


}
