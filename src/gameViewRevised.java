import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Button;



import java.io.File;
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
    private double backgroundYCoord = -5280;
    private double spaceBound = -800;
    private double earthMovement = .7;
    private double spaceMovement = .1;

    //Images used in background
    private Image background;
    private Image space;
    private Image fuelGauge;
    private Image pause;

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
        gameViewRevised.gameStage = primaryStage;

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
        pause = new Image("resources/pausebutton.png",30, 30, true, true);
        //Sets fill color and font/font size for score
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
                } else if (event.getCode() == KeyCode.P) {
                    controller.pKey();
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


    /*
     * Updates the view to match the model's current state
     */
    public void update(){
        //Order matters
        graphics.clearRect(0, 0, canvasWidth, canvasHeight);
        drawBackgroundImages(background, fuelGauge, pause);
        drawAsteroids();
        drawSpaceship();
        drawFuelIndicator();
        drawBonus();
        drawScore();
        drawLifeIndicators();
    }

    /*
     * Draws the bonuses on the canvas based on the model's state
     */
    private void drawBonus() {
        ArrayList<BonusSprite> bonusList = model.getBonusList();
        for (BonusSprite bonus : bonusList) {
            Image obstacleImage = bonus.getImage();
            double bonusPositionX = bonus.getPositionX();
            double bonusPositionY = bonus.getPositionY();
            graphics.drawImage(obstacleImage, bonusPositionX, bonusPositionY);
        }
    }

    /*
     * Draws the fuel indicator on the canvas based on the model's state
     */
    private void drawFuelIndicator() {
        FuelIndicatorSprite fuelIndicator = model.getFuelIndicator();
        Image fuelIndicatorImage = fuelIndicator.getImage();
        double fuelIndicatorPositionX = fuelIndicator.getPositionX();
        double fuelIndicatorPositionY = fuelIndicator.getPositionY();
        graphics.drawImage( fuelIndicatorImage, fuelIndicatorPositionX, fuelIndicatorPositionY);
    }

    /*
     * Draws the life indicators on the canvas based on the model's state
     */
    private void drawLifeIndicators() {
        ArrayList<LifeIndicatorSprite> indicators = model.getLifeIndicatorList();
        for (LifeIndicatorSprite indicator : indicators) {
            Image indicatorImage = indicator.getImage();
            double indicatorPositionX = indicator.getPositionX();
            double indicatorPositionY = indicator.getPositionY();
            graphics.drawImage(indicatorImage, indicatorPositionX, indicatorPositionY);
        }
    }


    /*
     * Draws the spaceship on the canvas based on the model's state
     */
    public void drawSpaceship() {
        ShipSprite spaceship = model.getSpaceship();
        Image spaceshipImage = spaceship.getImage();
        double spaceshipPositionX = spaceship.getPositionX();
        double spaceshipPositionY = spaceship.getPositionY();
        graphics.drawImage( spaceshipImage, spaceshipPositionX, spaceshipPositionY);
    }

    /*
     * Draws the asteroids on the canvas based on the model's state
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
     * Draws the background images.
     */
    private void drawBackgroundImages(Image background, Image fuelGauge, Image pause) {

        if (backgroundYCoord < -5100){
            graphics.drawImage(background, backgroundXCoord, backgroundYCoord);
            backgroundYCoord = backgroundYCoord + earthMovement;
            //System.out.println(backgroundYCoord);
        }
        else if (backgroundYCoord < 0){
            graphics.drawImage(background, backgroundXCoord, backgroundYCoord);
            backgroundYCoord = backgroundYCoord + earthMovement/2;

        }
        else if (backgroundYCoord > -5101 && spaceBound < -200){
            graphics.drawImage(space, backgroundXCoord, spaceBound);
            spaceBound = spaceBound + spaceMovement;
            System.out.println(spaceBound);
        }
        else {
            graphics.drawImage(space, backgroundXCoord, spaceBound);
        }

        graphics.drawImage(fuelGauge,530, 400);
        graphics.drawImage(pause,500, 10);
    }

    /*
     * Draws the score on the screen
     */
    private void drawScore(){
        score = "Score: " + model.getScore();
        graphics.fillText(score, 5, 20);
    }

    public void gameOver(boolean sound) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(50, 0, 20, 0));

        VBox gameOverButtons = new VBox();

        Text text = new Text(score);
        text.setFont(Font.font("Herculanum",FontWeight.BOLD , 30));
        text.setFill(Color.BEIGE);


        gameOverButtons.setAlignment(Pos.CENTER);
        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.setMaxWidth(280);
        Button playAgainButton = new Button("Play Again");
        playAgainButton.setMaxWidth(280);


        mainMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainMenu(sound);
            }
        });

        playAgainButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                newGame();
            }
        });
        gameOverButtons.setSpacing(30);
        gameOverButtons.setPadding(new Insets(0, 0, 400, 0));
        gameOverButtons.getChildren().addAll(text,playAgainButton, mainMenuButton);
        root.setTop(gameOverButtons);

        Scene gameOverScene = new Scene(root, 550, 700);
        gameOverScene.getStylesheets().addAll(this.getClass().getResource("stylesheet.css").toExternalForm());
        

        gameStage.setScene(gameOverScene);
    }

    private void newGame() {
        gameStage.close();
        gameController newGame = new gameController(new GameModel(model.getSound()));
    }

    private void mainMenu(boolean sound) {
        mainMenuViewRevised menu = new mainMenuViewRevised();
        menu.start(menu.mainStage);
        menu.setSound(sound);
        gameStage.close();
    }

}