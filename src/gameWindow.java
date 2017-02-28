import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
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
    public static final double MAX_BUTTON_WIDTH = 180;
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

        //Fixes window size; Maybe change so window size can change while maintaining aspect ratio
        primaryStage.setResizable(false);

        primaryStage.show();


    }

    private Node addButtons() {
        VBox homeButtons = new VBox();
        homeButtons.setAlignment(Pos.CENTER);
        String buttonStyle = "-fx-font: 22 copperplate; -fx-base: none; -fx-text-fill: orangered; -fx-border-color: transparent";
        String buttonStyleHover = "-fx-font: 22 copperplate; -fx-base: none; -fx-text-fill: orangered; -fx-border-color: orangered";

        Button startGame = new Button("Start");
        startGame.setMaxWidth(MAX_BUTTON_WIDTH);
        startGame.setStyle(buttonStyle);

        Button instructions = new Button("Instructions");
        instructions.setMaxWidth(MAX_BUTTON_WIDTH);
        instructions.setStyle(buttonStyle);

        Button settings = new Button("Settings");
        settings.setMaxWidth(MAX_BUTTON_WIDTH);
        settings.setStyle(buttonStyle);

        //Event handlers for mouse hove; feel like there should be a way so this is only written once, not three times
        startGame.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        startGame.setStyle(buttonStyleHover);
                    }
                });

        startGame.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        startGame.setStyle(buttonStyle);
                    }
                });

        instructions.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        instructions.setStyle(buttonStyleHover);
                    }
                });

        instructions.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        instructions.setStyle(buttonStyle);
                    }
                });

        settings.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        settings.setStyle(buttonStyleHover);
                    }
                });

        settings.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        settings.setStyle(buttonStyle);
                    }
                });

        homeButtons.setSpacing(30);
        homeButtons.setPadding(new Insets(0,0,400,0));
        homeButtons.getChildren().addAll(startGame, instructions, settings);

        return homeButtons;
    }

    private Node addText(Text text, FontWeight fontWeight, int fontSize) {
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER);
        text.setFont(Font.font("Herculanum", fontWeight, fontSize));
        text.setFill(Color.BEIGE);
        flowPane.getChildren().add(text);
        return flowPane;
    }
}
