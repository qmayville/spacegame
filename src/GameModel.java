
import javafx.animation.AnimationTimer;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Michael Vue, Ethan Cassel-Mace, Hannah Barnstone, && Quinn Mayville
 */
public class GameModel {

    private ShipSprite spaceship;
    private FuelIndicatorSprite fuelIndicator;
    private ArrayList<AsteroidSprite> obstacleList;
    // TODO private ArrayList<PowerUpSprite> PowerUpList;
    // TODO Needs fuel gaugue/indicator
    // TODO needs life indicators and score tracker
    private double height;
    private double time;
    private double score;
    private double immuneTime;
    private double lastObstacleGenerationTime;
    private gameViewRevised view;
    private Random randomNumberGenerator = new Random();


    public GameModel(){
        this.height = 0;
        this.time = 0;
        this.score = 0;
        this.immuneTime = -1.5;
        this.lastObstacleGenerationTime = -2;

        obstacleList = new ArrayList<>();
    }

    public ShipSprite getSpaceship() {
        return spaceship;
    }

    public FuelIndicatorSprite getFuelIndicator() { return fuelIndicator; }

    public ArrayList<AsteroidSprite> getObstacleList() {
        return obstacleList;
    }

    public void initialize() {
        Image shipImage = new Image("resources/ship.png", 60, 80, true, true);
        this.spaceship = new ShipSprite(250, 600, shipImage, 100, 3);
        Image fuelIndicatorImage = new Image("resources/arrow.png",23,19,true,true);
        this.fuelIndicator = new FuelIndicatorSprite(516,400,fuelIndicatorImage,20);



        /*
        //For testing am creating four 40 x 40 asteroids
        Image asteroidImage = new Image("resources/asteroid.png", 70, 70, true, true);
        AsteroidSprite testAsteroid = new AsteroidSprite(250, 150, 100, asteroidImage, 700);
        AsteroidSprite testAsteroid2 = new AsteroidSprite(250, -200, 100, asteroidImage, 700);
        AsteroidSprite testAsteroid3 = new AsteroidSprite(250, -550, 100, asteroidImage, 700);
        AsteroidSprite testAsteroid4 = new AsteroidSprite(250, -900, 100, asteroidImage, 700);
        obstacleList.add(testAsteroid);
        obstacleList.add(testAsteroid2);
        obstacleList.add(testAsteroid3);
        obstacleList.add(testAsteroid4);
        */
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
                    //increment time
                    time += elapsedTime;

                    //Check if spaceship should be immune
                    if (spaceship.isImmune() && immuneTime + 1.5 < time) {
                        Image shipImage = new Image("resources/ship.png", 60, 80, true, true);
                        spaceship.setImage(shipImage);
                        spaceship.setImmune(false);
                    }

                    //Move spaceship and fuel indicator
                    spaceship.updatePositionX(elapsedTime);
                    fuelIndicator.updatePositionY(elapsedTime);

                    //Move existing obstacles
                    for (AsteroidSprite obstacle : obstacleList) {
                        obstacle.updatePositionY(elapsedTime);
                    }
                    //Remove obstacles that are off screen and check for collisions
                    Iterator<AsteroidSprite> obstacleIterator = obstacleList.iterator();
                    while (obstacleIterator.hasNext()) {
                        AsteroidSprite obstacle = obstacleIterator.next();
                        if (obstacle.isBelowScreen()) {
                            obstacleIterator.remove();
                        }
                        if (spaceship.intersects(obstacle) && !spaceship.isImmune()) {

                            spaceship.changeLives(-1);
                            if (spaceship.getLives() < 0) {
                                Image explosion = new Image("resources/explosion.png", 60, 80, true, true);
                                spaceship.setImage(explosion);
                            } else {
                                spaceship.setImmune(true);
                                Image immuneImage = new Image("resources/temporaryImmune.png", 60, 80, true, true);
                                spaceship.setImage(immuneImage);
                                immuneTime = time;
                            }
                        }
                    }

                    //Generate asteroids every 2 time-units
                    if (lastObstacleGenerationTime + 2 < time) {
                        generateObstacles();
                        lastObstacleGenerationTime = time;
                    }

                    //TODO fuel, scorekeeping, bonuses
                    view.update();
                }
                lastUpdateTime.set(currentTime);
            }
        };
        gameTimer.start();

    }
    //TODO implement this method
    /*
     * Generates obstacle sprites that are added to obstacleList
     */
    private void generateObstacles() {
        //Temporary basic implementation generates one asteroid at a time
        int positionX = randomNumberGenerator.nextInt(460);
        Image asteroidImage = new Image("resources/asteroid.png", 70, 70, true, true);
        //TODO make velocity faster as time goes by, make sure velocity is same as backgrounds
        AsteroidSprite testAsteroid = new AsteroidSprite(positionX, -100, 100, asteroidImage, 700);
        obstacleList.add(testAsteroid);
    }


    //TODO change spaceship physics
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