package game;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by twalker61
 */
public class Main extends Application {

    private Stage stage;
    private GamePane play;
    private StackPane welcome;
    private StackPane instructions;
    private GamePane builder;
    private StackPane results;
    private static List<PortalButton> buttons = new ArrayList<>();
    private static List<Floor> floorTiles = new ArrayList<>();
    private static List<Wall> walls = new ArrayList<>();
    private static List<ExitPortal> exits = new ArrayList<>();
    private boolean hover;
    private boolean back;
    private boolean next;
    private boolean won;
    private int instructionPage;
    private AudioClip clickSound;
    private AudioClip resultSound;
    private MediaPlayer backgroundMusic;

    @Override
    public void start(Stage primaryStage) throws Exception{

        welcome = new StackPane();
        welcome.getChildren().add(new ImageView(new Image(getClass().getResource("../images/introScreen.png").toExternalForm())));
        instructions = new StackPane();
        instructions.getChildren().add(new ImageView(new Image(getClass().getResource("../images/introScreen.png").toExternalForm())));
        results = new StackPane();

        backgroundMusic = new MediaPlayer(new Media(getClass().getResource("../sounds/introSong.wav").toExternalForm()));
        backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundMusic.play();
        clickSound = new AudioClip(getClass().getResource("../sounds/click.wav")
                .toExternalForm());

        setWelcomeMouseEvents(welcome);
        setInstructionsMouseEvents(instructions);
        setResultsMouseEvents(results);

        stage = primaryStage;
        primaryStage.setTitle("Lab Trap!");
        primaryStage.setScene(new Scene(welcome, 1067, 600));
        primaryStage.show();

    }

    private void setWelcomeMouseEvents(StackPane node) {
        node.setOnMouseClicked(e -> {
            if (hover) {
                clickSound.play();
                instructions = new StackPane();
                setInstructionsMouseEvents(instructions);
                instructions.getChildren().add(new ImageView(new Image(getClass().getResource("../images/instructionscreen1.png").toExternalForm())));
                stage.setScene(new Scene(instructions, 1067, 600));
                instructionPage = 1;
            }
        });
        node.setOnMouseMoved(e -> {
            node.getChildren().remove(0);
            if (e.getY() > 309 && e.getY() < 387 && e.getX() > 342 && e.getX() < 734) {
                node.getChildren().add(new ImageView(new Image(getClass().getResource("../images/introScreenHover.png").toExternalForm())));
                hover = true;
            } else {
                node.getChildren().add(new ImageView(new Image(getClass().getResource("../images/introScreen.png").toExternalForm())));
                hover = false;
            }
        });
    }

    private void setInstructionsMouseEvents(StackPane node) {
        node.setOnMouseClicked(e -> {
            if (instructionPage == 1 && next) {
                clickSound.play();
                node.getChildren().add(new ImageView(new Image(getClass().getResource("../images/instructionscreen2.png").toExternalForm())));
                instructionPage = 2;
            } else if (instructionPage == 2) {
                clickSound.play();
                if (back) {
                    node.getChildren().add(new ImageView(new Image(getClass().getResource("../images/instructionscreen1.png").toExternalForm())));
                    instructionPage = 1;
                }
                if (next) {
                    node.getChildren().add(new ImageView(new Image(getClass().getResource("../images/instructionscreen3.png").toExternalForm())));
                    instructionPage = 3;
                }
            } else if (instructionPage == 3) {
                clickSound.play();
                if (back) {
                    node.getChildren().add(new ImageView(new Image(getClass().getResource("../images/instructionscreen2.png").toExternalForm())));
                    instructionPage = 2;
                } if (next) {
                    instructionPage = 4;
                }
            }
            if (instructionPage == 4) {
                clickSound.play();
                switchToBuilderScreen();
            }
        });
        node.setOnMouseMoved(e -> {
            node.getChildren().remove(0);
            if (instructionPage == 1) {
                if (e.getY() > 499 && e.getY() < 566 && e.getX() > 635 && e.getX() < 846) {
                    node.getChildren().add(new ImageView(new Image(getClass().getResource("../images/instructionscreen1nexthover.png").toExternalForm())));
                    back = false;
                    next = true;
                } else {
                    node.getChildren().add(new ImageView(new Image(getClass().getResource("../images/instructionscreen1.png").toExternalForm())));
                    back = false;
                    next = false;
                }
            }
            if (instructionPage == 2) {
                if (e.getY() > 506 && e.getY() < 572 && e.getX() > 230 && e.getX() < 444) {
                    node.getChildren().add(new ImageView(new Image(getClass().getResource("../images/instructionscreen2backhover.png").toExternalForm())));
                    back = true;
                    next = false;
                } else if (e.getY() > 506 && e.getY() < 572 && e.getX() > 638 && e.getX() < 852) {
                    node.getChildren().add(new ImageView(new Image(getClass().getResource("../images/instructionscreen2nexthover.png").toExternalForm())));
                    back = false;
                    next = true;
                } else {
                    node.getChildren().add(new ImageView(new Image(getClass().getResource("../images/instructionscreen2.png").toExternalForm())));
                    back = false;
                    next = false;
                }
            }
            if (instructionPage == 3) {
                if (e.getY() > 506 && e.getY() < 572 && e.getX() > 230 && e.getX() < 444) {
                    node.getChildren().add(new ImageView(new Image(getClass().getResource("../images/instructionscreen3backhover.png").toExternalForm())));
                    back = true;
                    next = false;
                } else if (e.getY() > 506 && e.getY() < 572 && e.getX() > 638 && e.getX() < 852) {
                    node.getChildren().add(new ImageView(new Image(getClass().getResource("../images/instructionscreen3beginhover.png").toExternalForm())));
                    back = false;
                    next = true;
                } else {
                    node.getChildren().add(new ImageView(new Image(getClass().getResource("../images/instructionscreen3.png").toExternalForm())));
                    back = false;
                    next = false;
                }
            }
        });
    }

    private void setResultsMouseEvents(StackPane node) {
        node.setOnMouseClicked(e -> {
            if (hover) {
                switchToBuilderScreen();
            }
        });
        node.setOnMouseMoved(e -> {
            node.getChildren().remove(0);
            if (e.getY() > 487 && e.getY() < 565 && e.getX() > 351 && e.getX() < 743) {
                if (won) {
                    node.getChildren().add(new ImageView(new Image(getClass().getResource("../images/winnerScreenHover.png").toExternalForm())));
                } else {
                    node.getChildren().add(new ImageView(new Image(getClass().getResource("../images/loserScreenHover.png").toExternalForm())));
                }
                hover = true;
            } else {
                if (won) {
                    node.getChildren().add(new ImageView(new Image(getClass().getResource("../images/winnerScreen.png").toExternalForm())));
                } else {
                    node.getChildren().add(new ImageView(new Image(getClass().getResource("../images/loserScreen.png").toExternalForm())));
                }
                hover = false;
            }
        });
    }

    public void switchToBuilderScreen() {
        backgroundMusic.stop();
        backgroundMusic = new MediaPlayer(new Media(getClass().getResource("../sounds/labSong.wav").toExternalForm()));
        backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundMusic.setVolume(0.4);
        StackPane stageOne = new StackPane();
        stageOne.getChildren().add(new ImageView(new Image(getClass
                ().getResource("../images/stageonescreen.png").toExternalForm())));
        stage.setScene(new Scene(stageOne, 1067, 600));
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        builder = new GamePane(this, true);
        delay.setOnFinished(event -> {
            backgroundMusic.play();
            stage.setScene(new Scene(builder, 1067, 600))
            ;
        });
        delay.play();
    }

    public void switchToGamePlay() {
        StackPane stageTwo = new StackPane();
        stageTwo.getChildren().add(new ImageView(new Image(getClass
                ().getResource("../images/stagetwoscreen.png").toExternalForm())));
        stage.setScene(new Scene(stageTwo, 1067, 600));
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        play = new GamePane(this, false);
        delay.setOnFinished(event -> {
            stage.setScene(new Scene (play, 1067, 600));
            play.requestFocus();
        });
        delay.play();
    }

    public void switchToResults(boolean w) {
        backgroundMusic.setVolume(0.1);
        won = w;
        results = new StackPane();
        walls.clear();
        floorTiles.clear();
        buttons.clear();
        exits.clear();
        setResultsMouseEvents(results);
        if (won) {
            resultSound = new AudioClip(getClass().getResource("../sounds/win" +
                    ".wav").toExternalForm());
            resultSound.play();
            resultSound = new AudioClip(getClass().getResource("../sounds/woohoo" +
                    ".wav").toExternalForm());
            resultSound.play();
            results.getChildren().add(new ImageView(new Image(getClass().getResource("../images/winnerScreen.png").toExternalForm())));
        } else {
            resultSound = new AudioClip(getClass().getResource("../sounds/lose" +
                    ".wav").toExternalForm());
            resultSound.play();
            results.getChildren().add(new ImageView(new Image(getClass().getResource("../images/loserScreen.png").toExternalForm())));
        }
        stage.setScene(new Scene(results, 1067, 600));
    }

    public void addWall(Wall w) {
        walls.add(w);
    }
    public void addFloor(Floor f) {
        floorTiles.add(f);
    }
    public void addButton(PortalButton b) {
        buttons.add(b);
    }
    public void addExit(ExitPortal p) {
        exits.add(p);
    }

    public List<Wall> getWallList() {
        return walls;
    }
    public List<Floor> getFloorList() {
        return floorTiles;
    }
    public List<PortalButton> getButtonList() {
        return buttons;
    }
    public List<ExitPortal> getExitPortals() {
        return exits;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
