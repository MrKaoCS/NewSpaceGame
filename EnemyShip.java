import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EnemyShip extends GameObject {

    protected static final int X_OFFSET_STEP_SIZE = 5;
    protected static final int Y_OFFSET_STEP_SIZE = 10;

    protected static final int MAX_X_OFFSET = 250;

    protected int startingX;
    protected int startingY;

    protected boolean goingRight;
    protected BufferedImage enemyImage;

    public EnemyShip(int x, int y) {

        this.x = x;
        this.startingX = x;

        this.y = y;
        this.startingY = y;

        goingRight = true;

        width = 50;
        height = 50;

        initializeImage();
    }

    protected void initializeImage() {
        try {
            enemyImage = ImageIO.read(new File("EnemyShip1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        int xOffset = x - startingX;

        if (xOffset >= MAX_X_OFFSET) {
            goingRight = false;
            y += Y_OFFSET_STEP_SIZE;
        } else if (xOffset <= 0) {
            goingRight = true;
            y += Y_OFFSET_STEP_SIZE;
        }

        if (goingRight) {
            x += X_OFFSET_STEP_SIZE;
        } else {
            x -= X_OFFSET_STEP_SIZE;
        }

        if (y > SpaceGame.GAME_HEIGHT) {
            SpaceGame.getGame().removeGameObject(this);
        }
    }

    public void draw(Graphics g, JFrame gameWindow) {

        g.drawImage(enemyImage, x, y, width, height, gameWindow);
    }

    @Override
    public void onDeath() {
        SpaceGame.getGame().addGameObject(new DeathParticles(x + width / 2, y + width / 2));
    }

    public int getPointValue() {
        return 50;
    }
}
