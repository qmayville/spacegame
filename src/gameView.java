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

/*
 * The window for the actual gameplay. For now displays a static background, once game is implemented
 * the background will change over time.
 */
public class gameView extends Application{

    static Stage gameStage = new Stage();
    private Scene gameScene;

    private int canvasWidth = 550;
    private int canvasHeight = 700;

    //Placeholders for actual game implementation where background is moving
    private int backgroundWidth = canvasWidth;
    private int backgroundHeight = 4000;
    private int backgroundXCoord = 0;
    public int backgroundYCoord = -2290;

    //Items here are public for now because we may need to modify them later
    public Image background;
    public Image ship;
    public Image fuel;
    public Image asteroid;
    public Image fuelGauge;

    /*
     * Creates JavaFX window and sets up canvas for drawing images.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        //Used to make the primaryStage publicly available to mainMenuView so that it can be called.
        gameView.gameStage = primaryStage;

        //Creates window and canvas for drawing images
        setup();

        gameStage.show();

    }
    /*
     * Constructs the images to be used in the background.
     */
    private void createImages() {
        background = new Image("resources/gameBackgroundGround.png", backgroundWidth, backgroundHeight, true, true);
        fuel = new Image("resources/fuel.png", 30, 50, true, true);
        int asteroidSize = randomAsteroidSize();
        asteroid = new Image("resources/asteroid.png", asteroidSize, asteroidSize, true, true);
        fuelGauge = new Image("resources/bar.png", 300, 300, true, true);
        ship = new Image("resources/ship.png", 60, 80, true, true );
    }

    /*
     * Preprocess the game window gui and constructs animation to handle spaceship movement
     */
    private void setup(){
        gameStage.setTitle("Space Adventurer");
        Group root = new Group();
        gameScene = new Scene(root);
        gameStage.setScene( gameScene );
        Canvas canvas = new Canvas( canvasWidth, canvasHeight );
        root.getChildren().add(canvas);
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        createImages();

        Sprite spaceship = new Sprite();
        spaceship.setImage(ship);
        spaceship.setPosition(250,600);
        spaceship.setVelocity(0,0);
        spaceship.render(graphics);

        //Animation to move spaceship side to side
        LongProperty lastUpdateTime = new SimpleLongProperty();
        AnimationTimer spaceshipAnimation = new AnimationTimer() {
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
                    drawBackgroundImages(graphics, background, fuel, asteroid, fuelGauge);
                    spaceship.render(graphics);
                }
                lastUpdateTime.set(currentTime);
            }
        };
        spaceshipAnimation.start();

    }


    /*
     * Draws the background images. Temporary method, will be implemented in controller once background is changing.
     */
    private void drawBackgroundImages(GraphicsContext graphics, Image background, Image fuel, Image asteroid, Image fuelGauge) {
        graphics.drawImage(background, backgroundXCoord, backgroundYCoord);
//        the x and y placement of these units are only temporary
        graphics.drawImage(fuel, 250, 250);
        graphics.drawImage(asteroid, 100, 100);
        graphics.drawImage(fuelGauge,530, 400);
    }


    /*
     * Randomizes the size of asteroids that are created.
     */
    public int randomAsteroidSize(){
        Random rand = new Random();
        int randomSize = rand.nextInt(100);

        return randomSize;
    }

}
