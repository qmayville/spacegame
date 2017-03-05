import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Created by envy on 3/4/17.
 */
public class gameWindow extends Application{

//    private static Stage gameStage;
    static Stage gameStage = new Stage();
    private Scene gameScene;
    private int shipWidth = 60;
    private int shipHeight = 80;
    public int shipYCoord;
    private int shipXCoord = 250;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Used to make the primaryStage publicly available to mainMenuWindow so that it can be called.
        gameWindow.gameStage = primaryStage;

        //Creates window and canvas for drawing images
        setup();

        gameStage.show();

    }

    private void setup(){
        gameStage.setTitle("Space Adventurer");
        //Create a new Group for the scene and canvas
        Group root = new Group();

        gameScene = new Scene(root);
        gameStage.setScene( gameScene );

        Canvas canvas = new Canvas( 550, 700 );

        root.getChildren().add(canvas);

        GraphicsContext graphics = canvas.getGraphicsContext2D();

        Image ship = new Image( "ship.png", shipWidth, shipHeight, true, true );

        shipYCoord = 600;

        graphics.drawImage( ship, shipXCoord, shipYCoord );
    }

}
