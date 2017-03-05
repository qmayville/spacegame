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

    @Override
    public void start(Stage primaryStage) throws Exception {

        gameStage = primaryStage;

        gameStage.setTitle("Space Adventurer");
//        gameScene = buildGameScene(gameStage);

        Group root = new Group();

        gameScene = new Scene(root);
        gameStage.setScene( gameScene );

        Canvas canvas = new Canvas( 500, 500 );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image ship = new Image( "ship.png" );
        gc.drawImage( ship, 300, 300 );

        gameStage.show();


    }

//    private Scene buildGameScene(Stage gameStage){
//
//        Group root = new Group();
//        Scene theScene = new Scene( root );
//        gameStage.setScene( theScene );
//
//        Canvas canvas = new Canvas( 400, 200 );
//        root.getChildren().add( canvas );
//
//        GraphicsContext gc = canvas.getGraphicsContext2D();
//
//        gc.setFill( Color.RED );
//        gc.setStroke( Color.BLACK );
//        gc.setLineWidth(2);
//        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
//        gc.setFont( theFont );
//        gc.fillText( "Hello, World!", 60, 50 );
//        gc.strokeText( "Hello, World!", 60, 50 );
//
//        Image earth = new Image( "ship.png" );
//        gc.drawImage( earth, 180, 100 );
//
//        gameStage.show();
//    }
}
