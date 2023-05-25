import javax.swing.*;
import java.awt.*;

public abstract class GameObject {

    // The x and y coordinate of the GameObject and the getters to access them
    protected int x;
    protected int y;

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    // The width and the height of the GameObject and the getters to access them
    protected int width;
    protected int height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // This indicates that every class that extend GameObject will need to define an update() method
    public abstract void update();

    // This indicates that every class that extend GameObject will need to define an draw() method
    public abstract void draw(Graphics g, JFrame gameWindow);

    // Code that can be used to check if two GameObjects collide with each other
    public boolean collidesWith(GameObject secondObject) {

        Rectangle firstObjectRect = new Rectangle(x, y, getWidth(), getHeight());
        Rectangle secondObjectRect = new Rectangle(secondObject.x, secondObject.y, secondObject.getWidth(), secondObject.getHeight());

        return firstObjectRect.intersects(secondObjectRect);
    }

    public void onDeath() {

    }
}
