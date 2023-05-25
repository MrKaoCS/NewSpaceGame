import java.util.Random;

public class LevelManager {

    public static void generateLevel(int levelNumber) {

        if (levelNumber == 1) {
            generateLevel1();
        } else if (levelNumber == 2) {
            generateLevel2();
        } else if (levelNumber == 3) {
            generateLevel3();
        } else {
            generateRandomLevel();
        }
    }

    private static void generateLevel1() {

        for (int i = 0; i < 5; i++) {
            SpaceGame.getGame().addGameObject(new EnemyShip(50 + i * 100, 50));
        }
    }

    private static void generateLevel2() {

        for (int i = 0; i < 5; i++) {

            SpaceGame.getGame().addGameObject(new EnemyShipWithProjectile(50 + i * 100, 50));
            SpaceGame.getGame().addGameObject(new EnemyShip(50 + i * 100, 150));
        }
    }

    private static void generateLevel3() {

        for (int i = 0; i < 3; i++) {
            SpaceGame.getGame().addGameObject(new EnemyShipWithProjectile(150 + i * 100, 50));
        }

        for (int i = 0; i < 4; i++) {
            SpaceGame.getGame().addGameObject(new EnemyShip(100 + i * 100, 150));
        }

        for (int i = 0; i < 5; i++) {
            SpaceGame.getGame().addGameObject(new EnemyMine(50 + i * 100, 250));
        }
    }

    private static void generateRandomLevel() {

		Random random = new Random();

		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 5; i++) {

				int randomNumber = random.nextInt(3);
				int x = 50 + i * 100;
				int y = 50 + j * 100;

				if (randomNumber == 0) {
					SpaceGame.getGame().addGameObject(new EnemyShip(x, y));
				} else if (randomNumber == 1){
                    SpaceGame.getGame().addGameObject(new EnemyShipWithProjectile(x, y));
				} else {
                    SpaceGame.getGame().addGameObject(new EnemyMine(x, y));
				}
			}
		}
    }
}
