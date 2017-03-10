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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    double spaceBound = -800;
    double earthMovement = .7;
    double spaceMovement = .1;

    //Items here are public for now because we may need to modify them later
    public Image background;
    public Image space;
    //public Image ship;
    public Image fuel;
    //public Image asteroid;
    public Image fuelGauge;

    public String score;


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
        background = new Image("resources/GameBackGround.png", backgroundWidth, backgroundHeight, true, true);
        fuelGauge = new Image("resources/bar.png", 300, 300, true, true);
        space = new Image("resources/starryPlanetBackGround.png", backgroundWidth, backgroundHeight, true, true);
        //       Sets fill color and font/font size for score
        graphics.setFill(Color.YELLOW);
        graphics.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));
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
                if (event.getCode() == KeyCode.RIGHT) {
                controller.rightArrowKeyReleased();
                } else if (event.getCode() == KeyCode.LEFT) {
                controller.leftArrowKeyReleased();
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
        drawBonus();
        drawScore();
    }

    private void drawBonus() {
        ArrayList<BonusSprite> bonusList = model.getBonusList();
        for (BonusSprite bonus : bonusList) {
            Image obstacleImage = bonus.getImage();
            double bonusPositionX = bonus.getPositionX();
            double bonusPositionY = bonus.getPositionY();
            graphics.drawImage(obstacleImage, bonusPositionX, bonusPositionY);
        }
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

    /*
     * updates asteroid images and positions
     */
    public void drawAsteroids() {
        ArrayList<AsteroidSprite> obstacleList = model.getObstacleList();
        for (AsteroidSprite obstacle : obstacleList) {
            Image obstacleImage = obstacle.getImage();
            double obstaclePositionX = obstacle.getPositionX();
            double obstaclePositionY = obstacle.getPositionY();
            graphics.drawImage(obstacleImage, obstaclePositionX, obstaclePositionY);
        }
    }
    /*
     * updates life indicators.
     */
    public void drawIndicators() {
//        ArrayList<AsteroidSprite> obstacleList = model.getObstacleList();
//        ArrayList<AsteroidSprite> obstacleList2 = model.getObstacleList2();
//        for (AsteroidSprite obstacle : obstacleList) {
//            Image obstacleImage = obstacle.getImage();
//            double obstaclePositionX = obstacle.getPositionX();
//            double obstaclePositionY = obstacle.getPositionY();
//            graphics.drawImage( obstacleImage, obstaclePositionX, obstaclePositionY);
//        }
    }



    /*
     * Draws the background images. Temporary method, will be implemented in controller once background is changing.
     */
    private void drawBackgroundImages(Image background, Image fuel, Image fuelGauge) {

        if (backgroundYCoord < -5100){
            graphics.drawImage(background, backgroundXCoord, backgroundYCoord);
            backgroundYCoord = backgroundYCoord + 5;
            //System.out.println(backgroundYCoord);
        }
        else if (backgroundYCoord < 0){
            graphics.drawImage(background, backgroundXCoord, backgroundYCoord);
            backgroundYCoord = backgroundYCoord + 5;

        }
        else if (backgroundYCoord > -5101 && spaceBound < -200){
            graphics.drawImage(space, backgroundXCoord, spaceBound);
            spaceBound = spaceBound + spaceMovement;
            System.out.println(spaceBound);
        }
        else {
            graphics.drawImage(space, backgroundXCoord, spaceBound);
        }
//        the x and y placement of these units are only temporary
        graphics.drawImage(fuelGauge,530, 400);
    }

    /*
     * Draws the score on the screen
     */
    private void drawScore(){
        score = "Score: " + model.getScore();
        graphics.fillText(score, 5, 20);
    }

}