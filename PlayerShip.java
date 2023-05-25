import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class PlayerShip extends GameObject implements KeyListener {

	private int spawnX;
	private int spawnY;

	private boolean rightKeyPressed = false;
	private boolean leftKeyPressed = false;
	private boolean fireProjectile = false;

	private BufferedImage playerImage;

	private boolean isDead = false;
	private boolean upgradedWeapon = false;

	public PlayerShip(int startingX, int startingY) {
		// ADD CODE HERE TO INITIALIZE THE PLAYER SHIP
		// you will need to set the 4 attributes from GameObject: x, y, width, height
		x = startingX;
		y = startingY;
		width = 50;
		height = 50;

		spawnX = startingX;
		spawnY = startingY;

		try {
			playerImage = ImageIO.read(new File("PlayerShip.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void respawn() {
		x = spawnX;
		y = spawnY;
		isDead = false;
	}

	public void update() {
		// ADD CODE HERE TO UPDATE THE PLAYER SHIP
		// this will be executed once per iteration of the game loop

		if (isDead) {
			return;
		}

		if (rightKeyPressed && x < SpaceGame.getGame().GAME_WIDTH - width - 20) {
			x = x + 10;
		}

		if (leftKeyPressed && x > 10) {
			x = x - 10;
		}

		if (fireProjectile) {
			PlayerProjectile projectile = new PlayerProjectile(x - (PlayerProjectile.WIDTH / 2) + (width / 2), y);
			SpaceGame.getGame().addGameObject(projectile);

			if (upgradedWeapon) {
				PlayerProjectile projectile2 = new PlayerProjectile(x - (PlayerProjectile.WIDTH / 2) + (width / 2), y, -8);
				SpaceGame.getGame().addGameObject(projectile2);

				PlayerProjectile projectile3 = new PlayerProjectile(x - (PlayerProjectile.WIDTH / 2) + (width / 2), y, 8);
				SpaceGame.getGame().addGameObject(projectile3);
			}

			fireProjectile = false;
		}
	}

	public void draw(Graphics g, JFrame gameWindow) {
		// ADD CODE HERE TO DRAW THE PLAYER SHIP IN THE GAME WINDOW
		// this will be executed once per iteration of the game loop
		g.setColor(Color.darkGray);
		g.drawImage(playerImage, x, y, width, height, gameWindow);
	}

	public void onDeath() {
		SpaceGame.getGame().addGameObject(new DeathParticles(x + width / 2, y + width / 2));
		isDead = true;
		fireProjectile = false;
	}

	public void upgradeWeapon() {
		upgradedWeapon = true;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// This method will remain empty
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// This method will be useful when writing the code allowing the user to control the PlayerShip
		// This method is called when a key is pressed on the keyboard
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rightKeyPressed = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			leftKeyPressed = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			fireProjectile = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// This method will be useful when writing the code allowing the user to control the PlayerShip
		// This method is called when a key is released on the keyboard
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rightKeyPressed = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			leftKeyPressed = false;
		}
	}
}
