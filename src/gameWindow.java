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
    private Stage gameStage;
    private Scene gameScene;
    private int shipWidth = 60;
    private int shipHeight = 80;

    @Override
    public void start(Stage primaryStage) throws Exception {

        gameStage = primaryStage;

        gameStage.setTitle("Space Adventurer");

        Group root = new Group();

        gameScene = new Scene(root);
        gameStage.setScene( gameScene );

        Canvas canvas = new Canvas( 550, 700 );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image ship = new Image( "ship.png", shipWidth, shipHeight, true, true );

        gc.drawImage( ship, 250, 600 );



        gameStage.show();


    }

}
