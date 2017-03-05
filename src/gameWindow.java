import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.Random;

/**
 * Created by envy on 3/4/17.
 */
public class gameWindow extends Application{

//    private static Stage gameStage;
    static Stage gameStage = new Stage();
    private Scene gameScene;

    private int canvasWidth = 550;
    private int canvasHeight = 700;

    public Image background;
    private int backgroundWidth = canvasWidth;
//    9699 is actual height of background
    public int backgroundHeight = 4000;
    public int backgroundXCoord = 0;
    public int backgroundYCoord = -2290;

    public Image ship;
    private int shipWidth = 60;
    private int shipHeight = 80;
    public int shipYCoord;
//    variable kept as private because the x coord for the ship should never change
    private int shipXCoord = 250;

//    Items here should be kept public because we're going to have to modify it later
    public Image fuel;
    private int fuelWidth = 30;
    private int fuelHeight = 50;

    public Image asteroid;
    public int asteroidSize;
//    public int asteroidHeight;

    public Image fuelGauge;
    private int fuelGaugeHeight = 300;
    private int fuelGaugeWidth = 1000;


    @Override
    public void start(Stage primaryStage) throws Exception {

        //Used to make the primaryStage publicly available to mainMenuWindow so that it can be called.
        gameWindow.gameStage = primaryStage;

        //Creates window and canvas for drawing images
        setup();

        gameStage.show();

    }

    /*
     * Preprocess the game window gui
     */
    private void setup(){
        gameStage.setTitle("Space Adventurer");
        //Create a new Group for the scene and canvas
        Group root = new Group();

        gameScene = new Scene(root);
        gameStage.setScene( gameScene );

        Canvas canvas = new Canvas( canvasWidth, canvasHeight );

        root.getChildren().add(canvas);

        GraphicsContext graphics = canvas.getGraphicsContext2D();

        background = new Image("resources/gameBackgroundGround.png", backgroundWidth, backgroundHeight, true, true);

        ship = new Image("resources/ship.png", shipWidth, shipHeight, true, true );
        fuel = new Image("resources/fuel.png", fuelWidth, fuelHeight, true, true);

        asteroidSize = randomAsteroidSize();

        asteroid = new Image("resources/asteroid.png", asteroidSize, asteroidSize, true, true);
        fuelGauge = new Image("resources/bar.png", fuelGaugeWidth, fuelGaugeHeight, true, true);

        shipYCoord = 600;

        Sprite spaceship = new Sprite();
        spaceship.setImage(ship);
        spaceship.setPosition(shipXCoord,shipYCoord);
        spaceship.setVelocity(0,0);
        spaceship.render(graphics);

        LongProperty lastUpdateTime = new SimpleLongProperty();
        AnimationTimer testAnimation = new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                if (lastUpdateTime.get() > 0) {
                    final double elapsedTime = (currentTime - lastUpdateTime.get()) / 1_000_000_000.0;

                    gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            if (event.getCode() == KeyCode.RIGHT) {
                                spaceship.addVelocity(50,0);
                            } else if (event.getCode() == KeyCode.LEFT) {
                                spaceship.addVelocity(-50,0);
                            }
                        }
                    });

                    gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT) {
                                spaceship.setVelocity(0,0);
                            }
                        }
                    });

                    spaceship.update(elapsedTime);
                    graphics.clearRect(0,0,canvasWidth,canvasHeight);
                    graphics.drawImage(background, backgroundXCoord, backgroundYCoord);
//        the x and y placement of these units are only temporary
                    graphics.drawImage(fuel, 250, 250);
                    graphics.drawImage(asteroid, 100, 100);
                    graphics.drawImage(fuelGauge,530, 400);
                    spaceship.render(graphics);
                }
                lastUpdateTime.set(currentTime);
            }
        };
        testAnimation.start();






        graphics.drawImage(background, backgroundXCoord, backgroundYCoord);
//        the x and y placement of these units are only temporary
        graphics.drawImage(fuel, 250, 250);
        graphics.drawImage(asteroid, 100, 100);
        graphics.drawImage(fuelGauge,530, 400);
    }

    /*
     * Randomizes the size of asteroids that are created
     */
    public int randomAsteroidSize(){
        Random rand = new Random();
        int randomSize = rand.nextInt(100);

        return randomSize;
    }

}
