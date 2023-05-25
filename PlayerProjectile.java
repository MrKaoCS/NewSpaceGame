import javax.swing.*;
import java.awt.*;

public class PlayerProjectile extends GameObject {

    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;

    private int xSpeed;

    public PlayerProjectile(int startingX, int startingY) {
        this(startingX, startingY, 0);
    }

    public PlayerProjectile(int startingX, int startingY, int horizontalSpeed) {
        x = startingX;
        y = startingY;
        width = WIDTH;
        height = HEIGHT;
        xSpeed = horizontalSpeed;
    }

    @Override
    public void update() {
        y = y - 20;
        x = x + xSpeed;

        if (y <= 0 || x <= 0 || x >= SpaceGame.GAME_WIDTH) {
            SpaceGame.getGame().removeGameObject(this);
        }
    }

    @Override
    public void draw(Graphics g, JFrame gameWindow) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, width, height);
        g.setColor(Color.BLUE);
        g.fillOval(x + 1, y + 1, width - 2, height - 2);
    }
}
