import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class EnemyShipWithProjectile extends EnemyShip {

    int projectileCountdown = 0;

    public EnemyShipWithProjectile(int x, int y) {
        super(x, y);

        resetProjectileCountdown();
    }

    @Override
    protected void initializeImage() {
        try {
            enemyImage = ImageIO.read(new File("EnemyShip2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        super.update();

        projectileCountdown--;

        if (projectileCountdown == 0) {
            EnemyProjectile newProjectile = new EnemyProjectile(x + - (EnemyProjectile.WIDTH / 2) + (width / 2), y + height);
            SpaceGame.getGame().addGameObject(newProjectile);

            resetProjectileCountdown();
        }
    }

    private void resetProjectileCountdown() {

        Random random = new Random();
        projectileCountdown = random.nextInt(20) + 20;
    }

    @Override
    public int getPointValue() {
        return 100;
    }
}
