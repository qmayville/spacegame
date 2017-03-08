import javafx.animation.AnimationTimer;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
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
    private gameViewRevised view;


    public GameModel(){
        this.height = 0;
        this.time = 0;
        this.score = 0;
        obstacleList = new ArrayList<>();
    }

    public ShipSprite getSpaceship() {
        return spaceship;
    }

    public ArrayList<AsteroidSprite> getObstacleList() {
        return obstacleList;
    }

    public void initialize() {
        Image shipImage = new Image("resources/ship.png", 60, 80, true, true);
        this.spaceship = new ShipSprite(250, 600, shipImage, 100, 3);

        //For testing am only creating one 40 x 40 asteroid
        Image asteroidImage = new Image("resources/asteroid.png", 70, 70, true, true);
        AsteroidSprite testAsteroid = new AsteroidSprite(250, 200, 50, asteroidImage, 700);
        obstacleList.add(testAsteroid);
    }

    public void setView(gameViewRevised view) {
        this.view = view;
    }

    public void runTimer() {
        //Timer for game
        LongProperty lastUpdateTime = new SimpleLongProperty();
        AnimationTimer gameTimer = new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                if (lastUpdateTime.get() > 0) {
                    final double elapsedTime = (currentTime - lastUpdateTime.get()) / 1_000_000_000.0;
                    //Move spaceship
                    spaceship.updatePositionX(elapsedTime);
                    //Move existing obstacles
                    for (AsteroidSprite obstacle : obstacleList) {
                        obstacle.updatePositionY(elapsedTime);
                    }
                    //Check for collisions
                    for (AsteroidSprite obstacle : obstacleList) {
                        if (spaceship.intersects(obstacle)) {

                            //TODO make ship immune to prevent further collisions for a bit

                            spaceship.changeLives(-1);
                            //TODO check if lives is below 0


                            //For testing purposes
                            System.out.println("COLLISION");
                        }
                    }

                    //Remove obstacles off screen
                    if (obstacleList.size() > 0 ) {
                        for (AsteroidSprite obstacle : obstacleList) {
                            if (obstacle.isBelowScreen()) {
                                obstacleList.remove(obstacle);
                            }
                        }
                    }

                    //TODO implement this method
                    //generateObstacles();

                    //TODO fuel, scorekeeping, bonuses

                    view.update();
                }
                lastUpdateTime.set(currentTime);
            }
        };
        gameTimer.start();

    }
    /*
     * Generates obstacle sprites that are added to obstacleList
     */
    private void generateObstacles() {
    }

    public void moveShipRight() {
        spaceship.addVelocityX(50);
    }

    public void moveShipLeft() {
        spaceship.addVelocityX(-50);
    }

    public void stopShipMovement() {
        spaceship.setVelocityX(0);
    }

}
