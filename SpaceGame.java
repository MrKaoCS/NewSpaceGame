import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SpaceGame extends JPanel {

	// These values can be modified if you would like to change the size of the game window
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 800;

	// Do not erase or modify these attributes, they are important for the overall game structure
	private static SpaceGame game;
	private JFrame gameWindow;
	private List<GameObject> objectsToAdd;
	private List<GameObject> objectsToRemove;

	// This is a variable that allows us to quickly find the PlayerShip object if we need it
	private PlayerShip player;

	// This is the List that contains all the active GameObjects that should
	// be updated and drawn in the game loop
	private List<GameObject> activeGameObjects;

	// Feel free to add new attributes if you need them
	// (for example, if you would like to keep track of the player's score
	private GameHud hud;
	private LevelManager levelManager;
	private int currentLevel;

	private boolean playerIsWaitingToRespawn = false;
	private int respawnCountdown = 35;

	private boolean waitingToGenerateLevel;
	private int levelGenerationCountdown;


	public static SpaceGame getGame() {
		return game;
	}

	public static void main(String[] args) throws InterruptedException {

		// This code is used to create the main Java object for the game and start running the game
		game = new SpaceGame();
		game.run();
	}

	public SpaceGame() {
		// When the SpaceGame object is created, initialize the GameObjects
		initializeGameObjects();
		// And create the window in which the game will take place
		initializeGameWindow();
	}

	private void run() throws InterruptedException {

		// This is the main game loop and where we will repeatedly update the game
		while (true) {

			// This is just to set a small delay between each of the game's "frame"
			// Otherwise it will go too fast
			Thread.sleep(20);

			// DO NOT REMOVE THIS, this allows us to update the List of active game objects
			// It adds any new objects that were created in the last iteration of the game loop
			addGameObjects();

			// Write your code here to update the game every iteration of the loop

			// ADD YOUR CODE HERE

			int numberOfActiveEnemies = 0;

			for (GameObject gameObject : activeGameObjects) {
				gameObject.update();

				if (gameObject instanceof EnemyShip) {
					numberOfActiveEnemies++;
				}
			}

			if (waitingToGenerateLevel) {
				levelGenerationCountdown--;

				if (levelGenerationCountdown == 0) {
					initalizeNextLevel();
				}
			} else if (numberOfActiveEnemies == 0) {
				startNextLevelCountdown();
			}

			if (playerIsWaitingToRespawn) {
				respawnCountdown--;

				if (respawnCountdown == 0) {
					playerIsWaitingToRespawn = false;
					respawnPlayer();
				}
			}

			checkForCollisions();

			// DO NOT REMOVE THIS, this allows us to update the List of active game objects
			// It removes any objects that needs to be removed from the game window based on
			// this iteration of the game loop
			deleteGameObjects();

			repaint();
		}
	}

	private void checkForCollisions() {

		for (int i = 0; i < activeGameObjects.size(); i++) {

			for (int j = 0; j < activeGameObjects.size(); j++) {

				GameObject firstObject = activeGameObjects.get(i);
				GameObject secondObject = activeGameObjects.get(j);

				if (firstObject == secondObject) {
					continue;
				}

				if (firstObject.collidesWith(secondObject)) {

					if (firstObject instanceof PlayerProjectile && secondObject instanceof EnemyShip) {
						removeGameObject(firstObject);
						removeGameObject(secondObject);

						firstObject.onDeath();
						secondObject.onDeath();

						EnemyShip enemy = (EnemyShip) secondObject;
						hud.addPoints(enemy.getPointValue());
					}
					if (firstObject instanceof EnemyProjectile && secondObject instanceof PlayerShip) {
						removeGameObject(firstObject);
						removeGameObject(secondObject);

						firstObject.onDeath();
						secondObject.onDeath();

						onPlayerDeath(false);
					}
					if (firstObject instanceof PlayerShip && secondObject instanceof EnemyShip) {
						removeGameObject(firstObject);
						firstObject.onDeath();

						onPlayerDeath(true);
					}
				}
			}
		}
	}

	private void onPlayerDeath(boolean forceGameOver) {

		hud.decreaseLives();
		if (forceGameOver || hud.getLivesCount() == 0) {
			hud.gameOver();
		} else {
			playerIsWaitingToRespawn = true;
			respawnCountdown = 35;
		}
	}

	private void respawnPlayer() {
		player.respawn();
		addGameObject(player);
	}

	@Override
	public void paint(Graphics g) {
		// DO NOT DELETE THIS LINE
		// This performs the basic operation of drawing the game window
		super.paint(g);


		// Add code here to draw your game (i.e., background, GameObjects, etc.)

		// ADD CODE HERE
		g.setColor(Color.BLACK);
		g.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);

		for (GameObject object: activeGameObjects) {
			object.draw(g, gameWindow);
		}

		hud.draw(g, gameWindow);
	}

	private void initializeGameObjects() {

		// Initialize the 3 lists that are used to manage game objects
		activeGameObjects = new ArrayList<>();
		objectsToAdd = new ArrayList<>();
		objectsToRemove = new ArrayList<>();

		// Create the player's ship and add it to the game
		// The player's starting position in the middle of the game window (on the x axis) and 125 pixels above the bottom of the window
		player = new PlayerShip(GAME_WIDTH / 2, GAME_HEIGHT - 125);
		addGameObject(player);

		// This is where you will add any other code that is useful to prepare the game before it starts
		// For example, adding the enemy ships to the game

		// ADD YOUR CODE HERE

		hud = new GameHud();
		currentLevel = 0;

		waitingToGenerateLevel = true;
		levelGenerationCountdown = 10;
	}

	private void startNextLevelCountdown() {
		waitingToGenerateLevel = true;
		levelGenerationCountdown = 30;
	}

	private void initalizeNextLevel() {
		currentLevel++;
		hud.setCurrentLevel(currentLevel);
		LevelManager.generateLevel(currentLevel);
		waitingToGenerateLevel = false;

		if (currentLevel == 5) {
			player.upgradeWeapon();
		}
	}

	private void initializeGameWindow() {

		// YOU WILL MOST LIKELY NOT NEED TO MODIFY THIS METHOD KEEP IT AS IS
		// This code creates the game window, feel free to ignore it
		gameWindow = new JFrame("Game window");
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gameWindow.setSize(GAME_WIDTH, GAME_HEIGHT);
		gameWindow.setResizable(false);
		gameWindow.setLocationRelativeTo(null);

		this.setSize(GAME_WIDTH, GAME_HEIGHT);

		// This code, makes it possible to use the keyboard to control the PlayerShip object
		gameWindow.addKeyListener(player);
		this.requestFocus();

		gameWindow.add(this);
		gameWindow.setVisible(true);
	}

	// THE NEXT 4 METHODS SHOULD NOT BE CHANGED/REMOVED
	// Those methods are useful as a mechanism to avoid modifying the list of activeGameObjects
	// at the same time that we are iterating on that same list
	public void addGameObject(GameObject newObject) {
		objectsToAdd.add(newObject);
	}

	private void addGameObjects() {
		activeGameObjects.addAll(objectsToAdd);
		objectsToAdd.clear();
	}

	public void removeGameObject(GameObject objectToRemove) {
		objectsToRemove.add(objectToRemove);
	}

	private void deleteGameObjects() {
		activeGameObjects.removeAll(objectsToRemove);
		objectsToRemove.clear();
	}
}
