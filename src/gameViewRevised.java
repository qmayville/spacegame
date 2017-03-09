import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

/*
 * Quinn Mayville, Michael Vue, Ethan Cassel-Mace, Hannah Barnstone
 *
 * The window for the actual gameplay. For now displays a static background, once game is implemented
 * the background will change over time.
 */
public class gameViewRevised extends Application{

    static Stage gameStage = new Stage();
    private Scene gameScene;


    private gameController controller;
    private GameModel model;
    GraphicsContext graphics;

    private int canvasWidth = 550;
    private int canvasHeight = 700;

    //Placeholders for actual game implementation where background is moving
    private int backgroundWidth = canvasWidth;
//    4000
    private int backgroundHeight = 0;
    private int backgroundXCoord = 0;
//    -2290
    public double backgroundYCoord = -5280;
    double spaceBound = -700;
    double earthMovement = .7;
    double spaceMovement = .1;

    //Items here are public for now because we may need to modify them later
    public Image background;
    public Image space;
    //public Image ship;
    public Image fuel;
    //public Image asteroid;
    public Image fuelGauge;


    public gameViewRevised(gameController controller, GameModel model) {
        super();
        this.model = model;
        this.controller = controller;
    }

    /*
     * Creates JavaFX window and sets up canvas for drawing images.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        //Used to make the primaryStage publicly available to mainMenuViewRevised so that it can be called.
        gameViewOld.gameStage = primaryStage;

        //Creates window and canvas for drawing images
        setup();

        gameStage.setResizable(false); //could change so that it is resizable
        gameStage.show();

    }
    /*
     * Constructs the images to be used in the background.
     */
    private void createImages() {
        background = new Image("resources/gameBackground.png", backgroundWidth, backgroundHeight, true, true);
        fuel = new Image("resources/fuel.png", 30, 50, true, true);
        fuelGauge = new Image("resources/bar.png", 300, 300, true, true);
        space = new Image("resources/planetaryBackground.png", backgroundWidth, backgroundHeight, true, true);
    }

    /*
     * Preprocess the game window gui and constructs key handlers for left and right movement
     */
    private void setup() {
        gameStage.setTitle("Space Adventurer");
        Group root = new Group();
        gameScene = new Scene(root, canvasWidth, canvasHeight, Color.BLACK);
//        gameScene.getStylesheets().addAll(this.getClass().getResource("gameViewStyle.css").toExternalForm());
        gameStage.setScene(gameScene);
        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        root.getChildren().add(canvas);
        graphics = canvas.getGraphicsContext2D();
        createImages();

        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.RIGHT) {
                    controller.rightArrowKey();
                } else if (event.getCode() == KeyCode.LEFT) {
                    controller.leftArrowKey();
                }
            }
        });

        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT) {
                    controller.arrowKeyReleased();
                }
            }
        });
    }


    //Order matters
    public void update(){
        graphics.clearRect(0, 0, canvasWidth, canvasHeight);
        drawBackgroundImages(background, fuel, fuelGauge);
        drawAsteroids();
        drawSpaceship();
        drawFuelIndicator();
    }

    private void drawFuelIndicator() {
        FuelIndicatorSprite fuelIndicator = model.getFuelIndicator();
        Image fuelIndicatorImage = fuelIndicator.getImage();
        double fuelIndicatorPositionX = fuelIndicator.getPositionX();
        double fuelIndicatorPositionY = fuelIndicator.getPositionY();
        graphics.drawImage( fuelIndicatorImage, fuelIndicatorPositionX, fuelIndicatorPositionY);
    }

    public void drawSpaceship() {
        ShipSprite spaceship = model.getSpaceship();
        Image spaceshipImage = spaceship.getImage();
        double spaceshipPositionX = spaceship.getPositionX();
        double spaceshipPositionY = spaceship.getPositionY();
        graphics.drawImage( spaceshipImage, spaceshipPositionX, spaceshipPositionY);
    }

    public void drawAsteroids() {
        ArrayList<AsteroidSprite> obstacleList = model.getObstacleList();
        for (AsteroidSprite obstacle : obstacleList) {
            Image obstacleImage = obstacle.getImage();
            double obstaclePositionX = obstacle.getPositionX();
            double obstaclePositionY = obstacle.getPositionY();
            graphics.drawImage( obstacleImage, obstaclePositionX, obstaclePositionY);
        }
    }


    /*
     * Draws the background images. Temporary method, will be implemented in controller once background is changing.
     */
    private void drawBackgroundImages(Image background, Image fuel, Image fuelGauge) {

        if (backgroundYCoord < -5100){
            graphics.drawImage(background, backgroundXCoord, backgroundYCoord);
            backgroundYCoord = backgroundYCoord + 5;
            System.out.println(backgroundYCoord);
        }
        else if (backgroundYCoord < 0){
            graphics.drawImage(background, backgroundXCoord, backgroundYCoord);
            backgroundYCoord = backgroundYCoord + earthMovement;

        }
        else{
            graphics.drawImage(space, backgroundXCoord, spaceBound);
            spaceBound = spaceBound + spaceMovement;
        }
//        the x and y placement of these units are only temporary
        graphics.drawImage(fuel, 250, 250);
        graphics.drawImage(fuelGauge,530, 400);
    }

}