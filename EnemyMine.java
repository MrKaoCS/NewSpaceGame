import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class EnemyMine extends EnemyShip {

    public EnemyMine(int x, int y) {
        super(x, y);
    }

    @Override
    protected void initializeImage() {
        try {
            enemyImage = ImageIO.read(new File("SpaceMine.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDeath() {

        Set<EnemyProjectile> explosion = new HashSet<>();

        int startingX = x + width / 2;
        int startingY = y + height / 2;

        explosion.add(new EnemyProjectile(startingX, startingY, 20, 0));
        explosion.add(new EnemyProjectile(startingX, startingY, -20, 0));
        explosion.add(new EnemyProjectile(startingX, startingY, 0, 20));
        explosion.add(new EnemyProjectile(startingX, startingY, 0, -20));
        explosion.add(new EnemyProjectile(startingX, startingY, 15, 15));
        explosion.add(new EnemyProjectile(startingX, startingY, -15, 15));
        explosion.add(new EnemyProjectile(startingX, startingY, 15, -15));
        explosion.add(new EnemyProjectile(startingX, startingY, -15, -15));

        for (EnemyProjectile projectile : explosion) {
            SpaceGame.getGame().addGameObject(projectile);
        }
    }

    @Override
    public int getPointValue() {
        return 75;
    }
}
