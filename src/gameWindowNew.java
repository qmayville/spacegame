import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
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
public class gameWindowNew extends Application {
    public static final double MAX_BUTTON_WIDTH = 180;
    private static final double SCENE_WIDTH = 550;
    private static final double SCENE_HEIGHT = 800;
    private Stage mainStage;
    private String buttonStyle;
    private String buttonStyleHover;
    //scene variables
    private Scene menuScene;
    private Scene instructionsScene;
    private Scene settingsScene;
    private Scene playScene;


    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;

        // variables that keep track of style. TODO: Move these to the css file if possible.
        buttonStyle = "-fx-font: 22 copperplate; -fx-base: none; -fx-text-fill: orangered; -fx-border-color: transparent";
        buttonStyleHover = "-fx-font: 22 copperplate; -fx-base: none; -fx-text-fill: orangered; -fx-border-color: orangered";

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
        startGame.setStyle(buttonStyle);

        Button instructions = new Button("Instructions");
        instructions.setMaxWidth(MAX_BUTTON_WIDTH);
        instructions.setStyle(buttonStyle);

        Button settings = new Button("Settings");
        settings.setMaxWidth(MAX_BUTTON_WIDTH);
        settings.setStyle(buttonStyle);

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
        root.setCenter(buttonPane);

        Scene InstructionsScene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        //This is where you can add a custom background (look at stylesheet)
        InstructionsScene.getStylesheets().addAll(this.getClass().getResource("stylesheet.css").toExternalForm());

        return InstructionsScene;
    }

    private Node getInstructionsButtonsNode(Stage mainStage) {
        VBox buttons = new VBox();
        buttons.setAlignment(Pos.CENTER);
        // Create new buttons
        Button startGame = new Button("Start");
        startGame.setMaxWidth(MAX_BUTTON_WIDTH);
        startGame.setStyle(buttonStyle);

        Button menu = new Button("Main Menu");
        menu.setMaxWidth(MAX_BUTTON_WIDTH);
        menu.setStyle(buttonStyle);

        Button settings = new Button("Settings");
        settings.setMaxWidth(MAX_BUTTON_WIDTH);
        settings.setStyle(buttonStyle);

        startGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainStage.setScene(playScene);
            }
        });

        menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainStage.setScene(menuScene);
            }
        });

        settings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainStage.setScene(settingsScene);
            }
        });

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

        buttons.setSpacing(30);
        buttons.setPadding(new Insets(0, 0, 400, 0));
        buttons.getChildren().addAll(menu, startGame, settings);

        return buttons;
    }

    private Scene buildSettingsScene(Stage mainStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(50, 0, 20, 0));

        Node titlePane = getTitleNode("SETTINGS", FontWeight.BOLD, 50);
        Node buttonPane = getInstructionsButtonsNode(mainStage);

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
        Button startGame = new Button("Start");
        startGame.setMaxWidth(MAX_BUTTON_WIDTH);
        startGame.setStyle(buttonStyle);

        Button instructions = new Button("Instructions");
        instructions.setMaxWidth(MAX_BUTTON_WIDTH);
        instructions.setStyle(buttonStyle);

        Button menu = new Button("Menu");
        menu.setMaxWidth(MAX_BUTTON_WIDTH);
        menu.setStyle(buttonStyle);

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

        menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainStage.setScene(menuScene);
            }
        });

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

        homeButtons.setSpacing(30);
        homeButtons.setPadding(new Insets(0, 0, 400, 0));
        homeButtons.getChildren().addAll(menu, startGame, instructions);

        return homeButtons;
    }

    private Scene buildPlayScene(Stage mainStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(50, 0, 20, 0));

        Node titlePane = getTitleNode("PLAY", FontWeight.BOLD, 50);
        Node buttonPane = getInstructionsButtonsNode(mainStage);

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
        menu.setStyle(buttonStyle);

        Button instructions = new Button("Instructions");
        instructions.setMaxWidth(MAX_BUTTON_WIDTH);
        instructions.setStyle(buttonStyle);

        Button settings = new Button("Settings");
        settings.setMaxWidth(MAX_BUTTON_WIDTH);
        settings.setStyle(buttonStyle);

        menu.setOnAction(new EventHandler<ActionEvent>() {
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

        //Event handlers for mouse hover; feel like there should be a way so this is only written once, not three times
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

        buttons.setSpacing(30);
        buttons.setPadding(new Insets(0, 0, 400, 0));
        buttons.getChildren().addAll(menu, instructions, settings);

        return buttons;
    }
}