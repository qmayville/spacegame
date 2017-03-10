
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
    private ArrayList<AsteroidSprite> obstacleList2;
    private ArrayList<BonusSprite> bonusList;
    // TODO Needs fuel gaugue/indicator
    // TODO needs life indicators and score tracker
    private double height;
    private double time;
    private double score;
    private double immuneTime;
    private double lastObstacleGenerationTime;
    private double lastFuelGenerationTime;
    private double lastBonusGenerationTime;
    private gameViewRevised view;
    private Random randomNumberGenerator = new Random();


    public GameModel(){
        this.height = 0;
        this.time = 0;
        this.score = 0;
        this.immuneTime = -5;
        //These can be changed to manipulate when the first generation of each occurs
        //The lower the number, the earlier it happens
        this.lastObstacleGenerationTime = -5;
        this.lastFuelGenerationTime = -1;
        this.lastBonusGenerationTime = 1;

        obstacleList = new ArrayList<>();
        obstacleList2 = new ArrayList<>();
        bonusList = new ArrayList<>();
    }

    public double getScore(){return score;}

    public ShipSprite getSpaceship() {
        return spaceship;
    }

    public FuelIndicatorSprite getFuelIndicator() { return fuelIndicator; }

    public ArrayList<AsteroidSprite> getObstacleList() {
        return obstacleList;
    }

    public ArrayList<AsteroidSprite> getObstacleLis2t() {
        return obstacleList2;
    }

    public ArrayList<BonusSprite> getBonusList() { return bonusList; }

    public void initialize() {
        Image shipImage = new Image("resources/ship.png", 60, 80, true, true);
        this.spaceship = new ShipSprite(250, 600, shipImage, 100, 3);
        Image fuelIndicatorImage = new Image("resources/arrow.png",23,20,true,true);
        this.fuelIndicator = new FuelIndicatorSprite(516,393,fuelIndicatorImage);

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

                    //Move existing obstacles
                    for (AsteroidSprite obstacle : obstacleList2) {
                        obstacle.updatePositionY(elapsedTime);
                    }

                    //Move existing bonuses
                    for (BonusSprite bonus : bonusList) {
                        bonus.updatePositionY(elapsedTime);
                    }

                    //Check for collisions
                    checkObstacleCollisions(this);

                    //Check if bonuses are below screen
                    checkBonusCollisions();

                    //Generate asteroids every 2 time-units
                    if (lastObstacleGenerationTime + 2 < time) {
                        generateObstacles();
                        lastObstacleGenerationTime = time;
                    }

                    //Generate fuel every 10.4 time-units
                    if (lastFuelGenerationTime + 7.4 < time) {
                        generateFuel();
                        lastFuelGenerationTime = time;
                    }

                    //Generate bonus every 24.7 time-units
                    if (lastBonusGenerationTime + 24.7 < time) {
                        generateBonus();
                        lastBonusGenerationTime = time;
                    }


                    //TODO fuel, scorekeeping, bonuses
                    view.update();
                }
                lastUpdateTime.set(currentTime);
            }
        };
        gameTimer.start();

    }

    private void generateBonus() {
        //Temporary implementation only generates life bonuses. Was thinking we could randomize which bonus it generates
        int positionX = randomNumberGenerator.nextInt(460);
        Image bonusImage = new Image("resources/heart.png", 30, 50, true, true);
        BonusSprite bonus = new BonusSprite(positionX, -100, 100, bonusImage, 700, "life");
        if (noIntersect(bonus)) {
            bonusList.add(bonus);
        } else {
            generateBonus();
        }
    }


    /*
     * Generates fuel
     */
    private void generateFuel(){
        int positionX = randomNumberGenerator.nextInt(460);
        Image fuelImage = new Image("resources/fuel.png", 30, 50, true, true);
        BonusSprite fuelBonus = new BonusSprite(positionX, -100, 100, fuelImage, 700, "fuel");
        if (noIntersect(fuelBonus)) {
            bonusList.add(fuelBonus);
        } else {
            generateFuel();
        }
    }

    /*
     * Returns true if the given bonus does not intersect any asteroids or bonuses.
     */
    //TODO check if this method works. I saw a bonus intersect with an asteroid, but when I purposely caused an intersection to test this then the method prevented it
    private boolean noIntersect(BonusSprite bonus) {
        for (AsteroidSprite obstacle : obstacleList) {
            if (bonus.intersects(obstacle)) {
                return false;
            }
        }
        for (AsteroidSprite obstacle : obstacleList2) {
            if (bonus.intersects(obstacle)) {
                return false;
            }
        }
        for (BonusSprite otherBonus : bonusList) {
            if (bonus.intersects(otherBonus)) {
                return false;
            }
        }
        return true;
    }

    /*
     * Checks whether bonuses are below screen or if spaceship has collided with them
     */
    private void checkBonusCollisions() {
        Iterator<BonusSprite> bonusIterator = bonusList.iterator();
        while (bonusIterator.hasNext()) {
            BonusSprite bonus = bonusIterator.next();
            if (bonus.isBelowScreen()) {
                bonusIterator.remove();
            }
            if (spaceship.intersects(bonus)) {
                giveBonus(bonus.getBonusType());
                bonusIterator.remove();
            }
        }
    }

    /*
     * Performs the specified bonus action
     */
    private void giveBonus(String bonusType) {
        if (bonusType.equals("life")) {
            spaceship.changeLives(1);
        }
        if (bonusType.equals("fuel")) {
            spaceship.setFuel(spaceship.getFuel() + 20);
        }
    }

    /*
     * Remove obstacles that are off screen and check for collisions
     */
    private void checkObstacleCollisions(AnimationTimer gameTimer) {
        Iterator<AsteroidSprite> obstacleIterator = obstacleList.iterator();
        Iterator<AsteroidSprite> obstacleIterator2 = obstacleList2.iterator();
        while (obstacleIterator.hasNext()) {
            AsteroidSprite obstacle = obstacleIterator.next();
            if (obstacle.isBelowScreen()) {
                obstacleIterator.remove();
            }
            if (spaceship.intersects(obstacle) && !spaceship.isImmune()) {
                collision(gameTimer);
            }
        }
        while (obstacleIterator2.hasNext()) {
            AsteroidSprite obstacle = obstacleIterator2.next();
            if (obstacle.isBelowScreen()) {
                obstacleIterator2.remove();
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
     * Updates the fuel and fuel indicator
     */
    private void updateFuel() {
        spaceship.updateFuel(-.04);
        double fuel = spaceship.getFuel();
        fuelIndicator.setPositionY(693 - (3 * fuel));
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
        int positionX = randomNumberGenerator.nextInt(229);
        int positionX2 = (randomNumberGenerator.nextInt(230) + 230);
        Image asteroidImage = new Image("resources/asteroid.png", 70, 70, true, true);
        //TODO make velocity faster as time goes by, make sure velocity is same as backgrounds
        AsteroidSprite testAsteroid = new AsteroidSprite(positionX, -200, 120, asteroidImage, 700);
        obstacleList.add(testAsteroid);
        AsteroidSprite testAsteroid2 = new AsteroidSprite(positionX2, -50, 120, asteroidImage, 700);
        obstacleList2.add(testAsteroid2);
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

    public ArrayList<AsteroidSprite> getObstacleList2() {
        return obstacleList2;
    }
}