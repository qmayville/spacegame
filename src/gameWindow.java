import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.scene.control.Button;


import java.awt.*;

/**
 * Created by mayvilleq on 2/27/17.
 */
public class gameWindow extends Application {
    public static final double MIN_BUTTON_WIDTH = 60;
    private static final double SCENE_WIDTH = 550;
    private static final double SCENE_HEIGHT = 800;
    private Text title = new Text("SPACE ADVENTURE");

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(50, 0, 20, 0));

        Button fake = new Button();

        Node titlePane = addText(title, FontWeight.BOLD, 50);
        Node buttonPane = addButtons();

        root.setTop(titlePane);
        root.setCenter(buttonPane);

        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

//        This is where you can add a custom background (look at stylesheet)
        scene.getStylesheets().addAll(this.getClass().getResource("stylesheet.css").toExternalForm());

        primaryStage.setTitle("Space Adventurer");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    private Node addButtons() {
        VBox verticalAlignment = new VBox();
        verticalAlignment.setAlignment(Pos.CENTER);

        Button startGame = new Button("Start");
        startGame.setMinWidth(MIN_BUTTON_WIDTH);

        verticalAlignment.getChildren().addAll(startGame);

        return verticalAlignment;
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
