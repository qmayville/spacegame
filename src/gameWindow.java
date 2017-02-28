import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Created by mayvilleq on 2/27/17.
 */
public class gameWindow extends Application {
    private static final double SCENE_WIDTH = 550;
    private static final double SCENE_HEIGHT = 800;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        primaryStage.setTitle("Test");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
