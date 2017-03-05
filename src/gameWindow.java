import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * Created by mayvilleq on 2/27/17.
 */
public class gameWindow extends Application {
    public static final double MAX_BUTTON_WIDTH = 180;
    private static final double SCENE_WIDTH = 550;
    private static final double SCENE_HEIGHT = 800;
    private Stage mainStage;
    //scene variables
    private Scene menuScene;
    private Scene instructionsScene;
    private Scene settingsScene;
    private Scene playScene;


    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;

        // Sets scene variables to the scene returned by their respective builder methods
        menuScene = buildMenuScene(mainStage);
        instructionsScene = buildInstructionsScene(mainStage);
        settingsScene = buildSettingsScene(mainStage);
        playScene = buildPlayScene(mainStage);

        // sets up and starts the stage
        primaryStage.setTitle("Space Adventurer");
        primaryStage.setScene(menuScene);
        primaryStage.setResizable(false); //could change so that it is resizable
        primaryStage.show();
    }


    private Node getTitleNode(String textString, FontWeight fontWeight, int fontSize) {
        Text text = new Text(textString);
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER);
        text.setFont(Font.font("Herculanum", fontWeight, fontSize));
        text.setFill(Color.BEIGE);
        flowPane.getChildren().add(text);
        return flowPane;
    }


    private Scene buildMenuScene(Stage mainStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(50, 0, 20, 0));

        Node titlePane = getTitleNode("SPACE ADVENTURE", FontWeight.BOLD, 50);
        Node buttonPane = getMenuButtonsNode(mainStage);

        root.setTop(titlePane);
        root.setCenter(buttonPane);

        Scene menuScene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        //This is where you can add a custom background (look at stylesheet)
        menuScene.getStylesheets().addAll(this.getClass().getResource("stylesheet.css").toExternalForm());
        return menuScene;
    }


    private Node getMenuButtonsNode(Stage mainStage) {
        VBox buttons = new VBox();
        buttons.setAlignment(Pos.CENTER);
        // Create new buttons
        Button startGame = new Button("Start");
        startGame.setMaxWidth(MAX_BUTTON_WIDTH);

        Button instructions = new Button("Instructions");
        instructions.setMaxWidth(MAX_BUTTON_WIDTH);

        Button settings = new Button("Settings");
        settings.setMaxWidth(MAX_BUTTON_WIDTH);

        startGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainStage.setScene(playScene);
            }
        });

        instructions.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainStage.setScene(instructionsScene);
            }
        });

        settings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainStage.setScene(settingsScene);
            }
        });

        buttons.setSpacing(30);
        buttons.setPadding(new Insets(0, 0, 400, 0));
        buttons.getChildren().addAll(startGame, instructions, settings);

        return buttons;
    }


    private Scene buildInstructionsScene(Stage mainStage) {


        BorderPane root = new BorderPane();
        root.setPadding(new Insets(50, 0, 20, 0));

        Node titlePane = getTitleNode("INSTRUCTIONS", FontWeight.BOLD, 50);
        Node buttonPane = getInstructionsButtonsNode(mainStage);

        root.setTop(titlePane);
        root.setBottom(buttonPane);

        Scene InstructionsScene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        //This is where you can add a custom background (look at stylesheet)
        InstructionsScene.getStylesheets().addAll(this.getClass().getResource("stylesheet.css").toExternalForm());

        return InstructionsScene;
    }

    private Node getInstructionsButtonsNode(Stage mainStage) {
        VBox buttons = new VBox();
        buttons.setAlignment(Pos.BOTTOM_CENTER);

        // Create new buttons
        Button backButton = new Button("Back");
        backButton.setMaxWidth(MAX_BUTTON_WIDTH);

        StackPane textTransparency = new StackPane();

        Rectangle blackTransparency = new Rectangle();
        blackTransparency.setHeight(360);
        blackTransparency.setWidth(520);
        blackTransparency.setFill(Color.web("black", 0.75));

        Text instructions = new Text();
        instructions.setCache(true);
        instructions.setX(50);
        instructions.setY(50);
        instructions.setFill(Color.ORANGERED);
        instructions.setFont(Font.font("copperplate", FontWeight.THIN, 15));
        instructions.setWrappingWidth(500);
        instructions.setTextAlignment(TextAlignment.JUSTIFY);
        instructions.setText("• The goal of the game is to fly your rocket ship as high into the sky as possible.\n" +
                "\n" +
                "• As your ship flies you must avoid obstacles through moving side to side by pressing the left and " +
                "right arrow keys on your keyboard.\n" +
                "\n" +
                "• If you fail to dodge an obstacle you will lose a life.\n" +
                "\n" +
                "• By steering your ship through hearts dispersed throughout your journey you can gain extra lives. \n" +
                "\n" +
                "• Your rocket ship has a limit quantity of fuel that diminishes over time. You can gain extra fuel by " +
                "steering your ship through fuel tanks disperse throughout your journey. \n" +
                "\n" +
                "• If you either run out of lives or fuel, you lose the game. The distance traveled prior to running out" +
                " of fuel/lives is your score for the game.\n");

        textTransparency.getChildren().add(blackTransparency);
        textTransparency.getChildren().add(instructions);

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainStage.setScene(menuScene);
            }
        });


        buttons.setSpacing(10);
        buttons.setPadding(new Insets(50, 0, 600, 0));
        buttons.getChildren().addAll(textTransparency, backButton);

        return buttons;
    }

    private Scene buildSettingsScene(Stage mainStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(50, 0, 20, 0));

        Node titlePane = getTitleNode("SETTINGS", FontWeight.BOLD, 50);
        Node buttonPane = getSettingsButtonsNode(mainStage);

        root.setTop(titlePane);
        root.setCenter(buttonPane);

        Scene settingsScene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        //This is where you can add a custom background (look at stylesheet)
        settingsScene.getStylesheets().addAll(this.getClass().getResource("stylesheet.css").toExternalForm());

        return settingsScene;
    }

    private Node getSettingsButtonsNode(Stage mainStage) {
        VBox homeButtons = new VBox();
        homeButtons.setAlignment(Pos.CENTER);
        // Create new buttons
        Button toggleSound = new Button("Toggle Sound");
        toggleSound.setMaxWidth(280);

        Button back = new Button("Back");
        back.setMaxWidth(MAX_BUTTON_WIDTH);

        Button highscores = new Button("Highscores");
        highscores.setMaxWidth(MAX_BUTTON_WIDTH);

        highscores.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

            }
        });

        toggleSound.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

            }
        });

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainStage.setScene(menuScene);
            }
        });

        homeButtons.setSpacing(30);
        homeButtons.setPadding(new Insets(0, 0, 400, 0));
        homeButtons.getChildren().addAll(highscores, toggleSound, back);

        return homeButtons;
    }

    private Scene buildPlayScene(Stage mainStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(50, 0, 20, 0));

        Node titlePane = getTitleNode("PLAY", FontWeight.BOLD, 50);
        Node buttonPane = getPlayButtonsNode(mainStage);

        root.setTop(titlePane);
        root.setCenter(buttonPane);

        Scene playScene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        //This is where you can add a custom background (look at stylesheet)
        playScene.getStylesheets().addAll(this.getClass().getResource("stylesheet.css").toExternalForm());

        return playScene;
    }

    private Node getPlayButtonsNode(Stage mainStage) {
        VBox buttons = new VBox();
        buttons.setAlignment(Pos.CENTER);
        // Create new buttons
        Button menu = new Button("Menu");
        menu.setMaxWidth(MAX_BUTTON_WIDTH);

//        Button instructions = new Button("Instructions");
//        instructions.setMaxWidth(MAX_BUTTON_WIDTH);
//
//        Button settings = new Button("Settings");
//        settings.setMaxWidth(MAX_BUTTON_WIDTH);

        menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainStage.setScene(menuScene);
            }
        });

//        instructions.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                mainStage.setScene(instructionsScene);
//            }
//        });
//
//        settings.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                mainStage.setScene(settingsScene);
//            }
//        });

        buttons.setSpacing(30);
        buttons.setPadding(new Insets(0, 0, 400, 0));
        buttons.getChildren().addAll(menu);

        return buttons;
    }
}