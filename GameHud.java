import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameHud extends GameObject {

    private BufferedImage playerImage;
    private BufferedImage gameOverImage;

    private int playerLives;
    private int score;

    private boolean gameOver;

    private int currentLevel;

    public GameHud() {
        // The Hud overlays information about the game. It doesn't really have a location or dimensions
        x = 0;
        y = 0;
        width = 0;
        height = 0;

        score = 0;
        playerLives = 3;

        gameOver = false;

        currentLevel = 0;

        try {
            playerImage = ImageIO.read(new File("PlayerShip.png"));
            gameOverImage = ImageIO.read(new File("GameOver.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void decreaseLives() {
        playerLives--;
    }

    public int getLivesCount() {
        return playerLives;
    }

    public void setCurrentLevel(int level) {
        currentLevel = level;
    }

    public void gameOver() {
        gameOver = true;
        playerLives = 0;
    }

    public void addPoints(int points) {
        score += points;
    }

    public void update() {
        // Similarly, the hud doesn't really need to be updated
    }

    public void draw(Graphics g, JFrame gameWindow) {

        g.setColor(Color.WHITE);

        g.drawString("Score: " + score, 20, 20);
        g.drawString("Lives: ", 20, 45);

        g.drawString("Level: " + currentLevel, SpaceGame.GAME_WIDTH - 75, 20);

        for (int i = 0; i < playerLives; i++) {
            g.drawImage(playerImage, 60 + i * 30, 30, 20, 20, gameWindow);
        }

        if (gameOver) {
            int gameOverX = (SpaceGame.GAME_WIDTH - 500) / 2;
            int gameOverY = ((SpaceGame.GAME_HEIGHT - 167) / 2) - 30;
            g.drawImage(gameOverImage, gameOverX, gameOverY, 500, 167, gameWindow);
        }
    }
}
