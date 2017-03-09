
import javafx.animation.AnimationTimer;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

    public double getScore(){return score;}

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
        Image fuelIndicatorImage = new Image("resources/arrow.png",23,20,true,true);
        this.fuelIndicator = new FuelIndicatorSprite(516,393,fuelIndicatorImage,10);

    }

    public void setView(gameViewRevised view) {
        this.view = view;
    }

    /*
     * Runs the timer that manages the game
     */
    public void runTimer() {
        LongProperty lastUpdateTime = new SimpleLongProperty();
        AnimationTimer gameTimer = new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                if (lastUpdateTime.get() > 0) {
                    final double elapsedTime = (currentTime - lastUpdateTime.get()) / 1_000_000_000.0;
                    //increment time
                    time += elapsedTime;

                    //Checks if spaceship should still be immune
                    if (spaceship.isImmune() && immuneTime + 1.5 < time) {
                        removeImmunity();
                    }

                    //Move spaceship and fuel indicator
                    spaceship.updatePositionX(elapsedTime);
                    fuelIndicator.updatePositionY(elapsedTime);

                    updateFuel();
                    checkFuel(this);

                    //Move existing obstacles
                    for (AsteroidSprite obstacle : obstacleList) {
                        obstacle.updatePositionY(elapsedTime);
                    }

                    checkCollisions(this);

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

    /*
     * Remove obstacles that are off screen and check for collisions
     */
    private void checkCollisions(AnimationTimer gameTimer) {
        Iterator<AsteroidSprite> obstacleIterator = obstacleList.iterator();
        while (obstacleIterator.hasNext()) {
            AsteroidSprite obstacle = obstacleIterator.next();
            if (obstacle.isBelowScreen()) {
                obstacleIterator.remove();
            }
            if (spaceship.intersects(obstacle) && !spaceship.isImmune()) {
                collision(gameTimer);
            }
        }
    }
    /*
     * Handles collisions with obstacles
     */
    private void collision(AnimationTimer gameTimer) {
        spaceship.changeLives(-1);
        if (spaceship.getLives() < 0) {
            gameOver(gameTimer);
        } else {
            spaceship.setImmune(true);
            Image immuneImage = new Image("resources/temporaryImmune.png", 60, 80, true, true);
            spaceship.setImage(immuneImage);
            immuneTime = time;
        }
    }

    /*
     * Updates the fuel variable within spaceship based on position of fuel indicator
     */
    //TODO fix this method; might not be best way to link fuel indicator and fuel variable
    private void updateFuel() {
        double fuelPosition = fuelIndicator.getPositionY();
        //Note that position 693 means fuel is empty and 393 means it is full
        //This should be made more abstract
        //Also arrow goes to far below screen before game over
        double fuelValue = 100 - (fuelPosition - 393)/3;
        spaceship.setFuel(fuelValue);
    }

    /*
     * Checks whether the fuel is empty
     */
    private void checkFuel(AnimationTimer gameTimer) {
        if (spaceship.getFuel() <= 0) {
            gameOver(gameTimer);
        }
    }

    /*
     * Makes the ship not immune
     */
    private void removeImmunity() {
        Image shipImage = new Image("resources/ship.png", 60, 80, true, true);
        spaceship.setImage(shipImage);
        spaceship.setImmune(false);
    }

    /*
     * Ends the game.
     */
    private void gameOver(AnimationTimer gameTimer) {
        Image explosion = new Image("resources/explosion.png", 60, 80, true, true);
        spaceship.setImage(explosion);
        gameTimer.stop();
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