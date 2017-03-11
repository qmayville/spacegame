
import javafx.animation.AnimationTimer;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
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
    private ArrayList<BonusSprite> bonusList;
    private ArrayList<LifeIndicatorSprite> lifeIndicatorList;
    // TODO Needs fuel gaugue/indicator
    // TODO needs life indicators and score tracker
    private double height;
    private double time;
    private int score;
    private double immuneTime;
    private double lastObstacleGenerationTime;
    private double lastFuelGenerationTime;
    private double lastBonusGenerationTime;
    private double imageSwitchTime;
    private gameViewRevised view;
    private Random randomNumberGenerator = new Random();
    private boolean acceleratingPositive;
    private boolean acceleratingNegative;
    private Image shipImage1;
    private Image shipImage2;
    private Image immuneImage;
    private Image asteroidImage;
    private Image lifeImage;
    private Image lifeUsedImage;
    private int imageNumber;



    public GameModel(){
        this.height = 0;
        this.time = 0;
        this.score = 0;
        this.immuneTime = -5;
        this.imageSwitchTime = -5;
        //These can be changed to manipulate when the first generation of each occurs
        //The lower the number, the earlier it happens
        this.lastObstacleGenerationTime = -5;
        this.lastFuelGenerationTime = -1;
        this.lastBonusGenerationTime = 1;

        acceleratingNegative = false;
        acceleratingPositive = false;

        obstacleList = new ArrayList<>();
        bonusList = new ArrayList<>();
        lifeIndicatorList = new ArrayList<>();

        shipImage1 = new Image("resources/toonship_1.png", 60, 80, true, true);
        shipImage2 = new Image("resources/toonship_2.png", 60, 80, true, true);
        immuneImage =new WritableImage(60,80);
        asteroidImage = new Image("resources/asteroid.gif", 70, 70, true, true);
        imageNumber = 1;

        lifeImage = new Image("resources/heart.png", 30, 30, true, true);
        lifeUsedImage = new Image("resources/heartUsed.png", 30, 30, true, true);

        for(int i = 0; i < 4; i++){
            double positionX = 300 + i*50;
            LifeIndicatorSprite indicator = new LifeIndicatorSprite(positionX, 10, lifeImage, lifeUsedImage);
            lifeIndicatorList.add(indicator);

        }
    }

    public double getScore(){return score;}

    public ShipSprite getSpaceship() {
        return spaceship;
    }

    public FuelIndicatorSprite getFuelIndicator() { return fuelIndicator; }

    public ArrayList<AsteroidSprite> getObstacleList() {
        return obstacleList;
    }

    public ArrayList<LifeIndicatorSprite> getLifeIndicatorList() {
        return lifeIndicatorList;
    }


    public ArrayList<BonusSprite> getBonusList() { return bonusList; }

    public void initialize() {
        this.spaceship = new ShipSprite(250, 600, shipImage1, 100, 4);
        Image fuelIndicatorImage = new Image("resources/arrow.png",23,20,true,true);
        this.fuelIndicator = new FuelIndicatorSprite(516,393,fuelIndicatorImage);

    }

    public void setView(gameViewRevised view) {
        this.view = view;
    }

    private void setLifeIndicators(int lives){
        for(int i = lifeIndicatorList.size()-1; i >= 0; i --){
            if(i >= lifeIndicatorList.size() - lives) {
                lifeIndicatorList.get(i).turnOn();
            }
            else{
                lifeIndicatorList.get(i).turnOff();
            }
        }
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

                    //Flicker flames or flicker immunity
                    if (!spaceship.isImmune()) {

                        flickerImage(shipImage1, shipImage2, .15);
                    } else {
                        flickerImage(immuneImage, shipImage2, .2);
                    }

                    //accelerate or decelerate ship
                    if (acceleratingPositive) {
                        moveShipRight();
                    }

                    if (acceleratingNegative) {
                        moveShipLeft();
                    }

                    if (!acceleratingNegative && !acceleratingNegative){
                        slowDownShip();
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

                    score = score + 1;


                    //TODO fuel, scorekeeping, bonuses
                    view.update();
                }
                lastUpdateTime.set(currentTime);
            }
        };
        gameTimer.start();

    }

    private void flickerImage(Image image1, Image image2, double flickerTime) {
        if (imageSwitchTime + flickerTime < time) {
            if (imageNumber == 1) {
                spaceship.setImage(image2);
                imageNumber = 2;
            } else {
                spaceship.setImage(image1);
                imageNumber = 1;
            }
            imageSwitchTime = time;
        }
    }


    private void generateBonus() {
        //Temporary implementation only generates life bonuses. Was thinking we could randomize which bonus it generates
        int positionX = randomNumberGenerator.nextInt(460);
        Image bonusImage = new Image("resources/heart.png", 30, 50, true, true);
        BonusSprite bonus = new BonusSprite(positionX, -100, 120, bonusImage, 700, "life");
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
        BonusSprite fuelBonus = new BonusSprite(positionX, -100, 120, fuelImage, 700, "fuel");
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
                setLifeIndicators(spaceship.getLives());

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
            String songFile = new File("src/resources/life.mp3").toURI().toString();
            Media media = new Media(songFile);
            MediaPlayer mp = new MediaPlayer(media);
            mp.play();
        }
        if (bonusType.equals("fuel")) {
            spaceship.setFuel(spaceship.getFuel() + 20);
            String songFile = new File("src/resources/fuel.mp3").toURI().toString();
            Media media = new Media(songFile);
            MediaPlayer mp = new MediaPlayer(media);
            mp.play();
        }
    }

    /*
     * Remove obstacles that are off screen and check for collisions
     */
    private void checkObstacleCollisions(AnimationTimer gameTimer) {
        Iterator<AsteroidSprite> obstacleIterator = obstacleList.iterator();
        while (obstacleIterator.hasNext()) {
            AsteroidSprite obstacle = obstacleIterator.next();
            if (obstacle.isBelowScreen()) {
                obstacleIterator.remove();
            }
            if (spaceship.intersects(obstacle) && !spaceship.isImmune()) {
                collision(gameTimer);
                setLifeIndicators(spaceship.getLives());
            }
        }
    }
    /*
     * Handles collisions with obstacles
     */
    private void collision(AnimationTimer gameTimer) {
        spaceship.changeLives(-1);
        if (spaceship.getLives() <= 0) {
            gameOver(gameTimer);
        } else {
            spaceship.setImmune(true);
            spaceship.setImage(immuneImage);
            imageNumber = 1;
            immuneTime = time;
            imageSwitchTime = time;
            String songFile = new File("src/resources/collision.mp3").toURI().toString();
            Media media = new Media(songFile);
            MediaPlayer mp = new MediaPlayer(media);
            mp.play();
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
        spaceship.setImage(shipImage1);
        spaceship.setImmune(false);
    }

    /*
     * Ends the game.
     */
    private void gameOver(AnimationTimer gameTimer) {
        Image explosion = new Image("resources/explosion.png", 60, 80, true, true);
        String songFile = new File("src/resources/gameover.mp3").toURI().toString();
        Media media = new Media(songFile);
        MediaPlayer mp = new MediaPlayer(media);
        mp.play();
        spaceship.setImage(explosion);
        gameTimer.stop();
    }

    //TODO implement this method
    /*
     * Generates obstacle sprites that are added to obstacleList
     */
    private void generateObstacles() {
        //TODO: check asteroids doint' intersect bonuses
        //Temporary basic implementation generates one asteroid at a time
        int positionX = randomNumberGenerator.nextInt(229);
        int positionX2 = (randomNumberGenerator.nextInt(230) + 230);
        //TODO make velocity faster as time goes by, make sure velocity is same as backgrounds
        AsteroidSprite asteroid1 = new AsteroidSprite(positionX, -200, 120, asteroidImage, 700);
        obstacleList.add(asteroid1);
        AsteroidSprite asteroid2 = new AsteroidSprite(positionX2, -50, 120, asteroidImage, 700);
        obstacleList.add(asteroid2);
    }



    public void startAccelerationPositive(){
        acceleratingPositive = true;
    }

    public void startAccelerationNegative(){
        acceleratingNegative = true;
    }

    public void stopAccelerationPositive(){
        acceleratingPositive = false;
    }

    public void stopAccelerationNegative(){
        acceleratingNegative = false;
    }

    public void moveShipRight() {
        spaceship.addVelocityX(10);
    }

    public void moveShipLeft() {
        spaceship.addVelocityX(-10);
    }

    public void slowDownShip() {
        spaceship.slowDownX(2.5);
    }

    public void stopShipMovement() {
        spaceship.setVelocityX(0);
    }


}