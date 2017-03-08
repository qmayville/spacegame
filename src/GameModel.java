import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Created by Michael Vue, Ethan Cassel-Mace, Hannah Barnstone, && Quinn Mayville
 */
public class GameModel {

    private ShipSprite spaceship;
    private ArrayList<AsteroidSprite> obstacleList;
    // TODO private ArrayList<PowerUpSprite> obstacleList;
    // TODO Needs fuel gaugue/indicator
    // TODO needs life indicators and score tracker
    private double height;
    private double time;
    private double score;


    public GameModel(){
        this.height = 0;
        this.time = 0;
        this.score = 0;
        obstacleList = new ArrayList<>();
        initializeSprites();
    }

    private void initializeSprites() {
        Image shipImage = new Image("resources/ship.png", 60, 80, true, true );
        this.spaceship = new ShipSprite(250, 600, shipImage, 100, 3);

        //For testing am only creating one 20 x 20 asteroid
        Image asteroidImage = new Image("resources/asteroid.png", 20, 20, true, true);
        AsteroidSprite testAsteroid = new AsteroidSprite(200,-10,asteroidImage,700);
        obstacleList.add(testAsteroid);
    }


}
