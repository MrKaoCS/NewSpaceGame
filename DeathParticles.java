import javax.swing.*;
import java.awt.*;

public class DeathParticles extends GameObject {

    protected int animationFrame = 0;

    public DeathParticles(int startingX, int startingY) {
        x = startingX;
        y = startingY;
        // There is no collision on this object, so the width and the height are irrelevant
        width = 0;
        height = 0;
    }

    public void update() {
        animationFrame++;

        if (animationFrame == 20) {
            SpaceGame.getGame().removeGameObject(this);
        }
    }

    public void draw(Graphics g, JFrame gameWindow) {
        g.setColor(Color.WHITE);
        g.fillOval(x - animationFrame, y - animationFrame, animationFrame, animationFrame);
        //g.fillOval(x , y - animationFrame, animationFrame, animationFrame);
        g.fillOval(x + animationFrame, y - animationFrame, animationFrame, animationFrame);
        //g.fillOval(x + animationFrame, y, animationFrame, animationFrame);
        g.fillOval(x + animationFrame, y + animationFrame, animationFrame, animationFrame);
        //g.fillOval(x , y + animationFrame, animationFrame, animationFrame);
        g.fillOval(x - animationFrame, y + animationFrame, animationFrame, animationFrame);
        //g.fillOval(x - animationFrame, y, animationFrame, animationFrame);
    }
}
