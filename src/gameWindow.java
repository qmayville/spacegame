import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.*;

/**
 * Created by mayvilleq on 2/27/17.
 */
public class gameWindow extends Application {
    private static final double SCENE_WIDTH = 550;
    private static final double SCENE_HEIGHT = 800;
    private Text title = new Text("SPACE ADVENTURE");

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(50, 0, 20, 0));

        Node titlePane = addText(title, FontWeight.BOLD, 50);

        root.setTop(titlePane);


        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

//        This is where you can add a custom background (look at stylesheet)
        scene.getStylesheets().addAll(this.getClass().getResource("stylesheet.css").toExternalForm());
        primaryStage.setTitle("Space Adventurer");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    private Node addText(Text text, FontWeight fontWeight, int fontSize) {
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER);
        text.setFont(Font.font("Times New Roman", fontWeight, fontSize));
        text.setFill(Color.BEIGE);
        flowPane.getChildren().add(text);
        return flowPane;
    }
}
