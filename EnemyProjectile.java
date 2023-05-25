import javax.swing.*;
import java.awt.*;

public class EnemyProjectile extends GameObject {

    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;

    private int xSpeed;
    private int ySpeed;

    public EnemyProjectile(int x, int y) {
        this(x, y, 0, 5);
    }

    public EnemyProjectile(int x, int y, int xSpeed, int ySpeed) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;

        width = WIDTH;
        height = HEIGHT;
    }

    public void update() {
        x += xSpeed;
        y += ySpeed;

        boolean remove = false;
        if (y > SpaceGame.getGame().GAME_HEIGHT || y < 0) {
            remove = true;
        }
        if (x > SpaceGame.getGame().GAME_WIDTH || x < 0) {
            remove = true;
        }

        if (remove) {
            SpaceGame.getGame().removeGameObject(this);
        }
    }

    public void draw(Graphics g, JFrame gameWindow) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, width, height);
        g.setColor(Color.RED);
        g.fillOval(x + 1, y + 1, width - 2, height - 2);
    }
}
