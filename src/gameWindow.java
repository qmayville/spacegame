import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.scene.control.Button;


/**
 * Created by mayvilleq on 2/27/17.
 */
public class gameWindow extends Application {
    public static final double MAX_BUTTON_WIDTH = 180;
    private static final double SCENE_WIDTH = 550;
    private static final double SCENE_HEIGHT = 800;
    private Text title = new Text("SPACE ADVENTURE");
    private Stage mainStage;


    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(50, 0, 20, 0));

        Node titlePane = addText(title, FontWeight.BOLD, 50);
        Node buttonPane = addButtons(root, primaryStage);

        root.setTop(titlePane);
        root.setCenter(buttonPane);

        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        //This is where you can add a custom background (look at stylesheet)
        scene.getStylesheets().addAll(this.getClass().getResource("stylesheet.css").toExternalForm());

        primaryStage.setTitle("Space Adventurer");
        primaryStage.setScene(scene);

        //Fixes window size; Maybe change so window size can change while maintaining aspect ratio
        primaryStage.setResizable(false);

        primaryStage.show();


    }

    private Node addButtons(BorderPane root, Stage mainStage) {
        VBox homeButtons = new VBox();
//        Setting button alignment and aesthetic format
        homeButtons.setAlignment(Pos.CENTER);
        String buttonStyle = "-fx-font: 22 copperplate; -fx-base: none; -fx-text-fill: orangered; -fx-border-color: transparent";
        String buttonStyleHover = "-fx-font: 22 copperplate; -fx-base: none; -fx-text-fill: orangered; -fx-border-color: orangered";
//        Create new buttons
        Button startGame = new Button("Start");
        startGame.setMaxWidth(MAX_BUTTON_WIDTH);
        startGame.setStyle(buttonStyle);

        Button instructions = new Button("Instructions");
        instructions.setMaxWidth(MAX_BUTTON_WIDTH);
        instructions.setStyle(buttonStyle);

        //Links to instructions page by calling method to create new window and closing currentwindow. Not sure if best way to do this
        instructions.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {

//                Stage stage = (Stage) instructions.getScene().getWindow();
//                double x = stage.getX();
//                double y = stage.getY();
//                instructionsWindow(x,y);
//                stage.close();
                BorderPane bpane2 = new BorderPane();
                Scene instructionScene = new Scene(bpane2, SCENE_WIDTH, SCENE_HEIGHT);
                instructionScene.getStylesheets().addAll(this.getClass().getResource("stylesheet.css").toExternalForm());
//                Button backButton = new Button("Back");
//                backButton.setMaxWidth(MAX_BUTTON_WIDTH);
//                backButton.setStyle(buttonStyle);
                Node buttonPane = addInstructionButtons();
                bpane2.setCenter(buttonPane);
                mainStage.setScene(instructionScene);
            }
        });


        Button settings = new Button("Settings");
        settings.setMaxWidth(MAX_BUTTON_WIDTH);
        settings.setStyle(buttonStyle);

        //Event handlers for mouse hover; feel like there should be a way so this is only written once, not three times
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

    //Creates instructions window. Not sure if best way to do this
    private void instructionsWindow(double x,double y) {
        Stage instructionsStage = new Stage();
        instructionsStage.setX(x);
        instructionsStage.setY(y);

        Text instructionsTitle = new Text("INSTRUCTIONS");
        Node buttonPane = addInstructionButtons();

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(50, 0, 20, 0));


        Node titlePane = addText(instructionsTitle, FontWeight.BOLD, 50);

        root.setTop(titlePane);
        root.setCenter(buttonPane);

        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

//        This is where you can add a custom background (look at stylesheet)
        scene.getStylesheets().addAll(this.getClass().getResource("stylesheet.css").toExternalForm());

        instructionsStage.setTitle("Space Adventurer");
        instructionsStage.setScene(scene);

        //Fixes window size; Maybe change so window size can change while maintaining aspect ratio
        instructionsStage.setResizable(false);

        instructionsStage.show();
    }

    private Node addInstructionButtons() {
        VBox instructionButtons = new VBox();
        instructionButtons.setAlignment(Pos.CENTER);
        String buttonStyle = "-fx-font: 22 copperplate; -fx-base: transparent; -fx-text-fill: orangered; -fx-border-color: transparent";
        String buttonStyleHover = "-fx-font: 22 copperplate; -fx-base: transparent; -fx-text-fill: orangered; -fx-border-color: orangered";

        Button menu = new Button("Main Menu");
        menu.setMaxWidth(MAX_BUTTON_WIDTH);
        menu.setStyle(buttonStyle);


        //Links to instructions page by calling method to create new window and closing currentwindow. Not sure if best way to do this
        menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {

                Stage stage = (Stage) menu.getScene().getWindow();
                Stage primaryStage = new Stage();
                primaryStage.setX(stage.getX());
                primaryStage.setY(stage.getY());
                try {
                    start(primaryStage);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                stage.close();
            }
        });


        menu.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        menu.setStyle(buttonStyleHover);
                    }
                });

        menu.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        menu.setStyle(buttonStyle);
                    }
                });

        instructionButtons.setSpacing(30);
        instructionButtons.setPadding(new Insets(0,0,400,0));
        instructionButtons.getChildren().addAll(menu);

        return instructionButtons;
    }

}
