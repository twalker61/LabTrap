package game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Created by twalker61
 */
public class Main extends Application {

    private GamePane root;
    private GameScreen layer;
    private ScrollPane scroller;
    private HBox background;
    private AnimationTimer gameLoop;
    private ImageView backgroundImageView;
    private double scroll = .5;
    private boolean moveUp;
    private boolean moveDown;
    private boolean moveLeft;
    private boolean moveRight;

    @Override
    public void start(Stage primaryStage) throws Exception{

        root = new GamePane();
        //root = new GamePane();
        //layer = (GameScreen) root.getCenter();
        /*scroller = new ScrollPane();
        background = new HBox();
        background.setMinWidth(1000);
        backgroundImageView = new ImageView( getClass().getResource( "spaceBackground.jpg").toExternalForm());
        background.getChildren().add(backgroundImageView);
        scroller.setContent(background);

        scroller.setOnKeyPressed(e -> {
            KeyCode k = e.getCode();
            if (k.isArrowKey()) {
                if (k.name().equals("UP")) {
                    moveUp = true;
                }
                if (k.name().equals("DOWN")) {
                    moveDown = true;
                }
                if (k.name().equals("LEFT")) {
                    moveLeft = true;
                }
                if (k.name().equals("RIGHT")) {
                    moveRight = true;
                }
            }
        });

        scroller.setOnKeyReleased(e -> {
            KeyCode k = e.getCode();
            if (k.isArrowKey()) {
                if (k.name().equals("UP")) {
                    moveUp = false;
                }
                if (k.name().equals("DOWN")) {
                    moveDown = false;
                }
                if (k.name().equals("LEFT")) {
                    moveLeft = false;
                }
                if (k.name().equals("RIGHT")) {
                    moveRight = false;
                }
            }
        });*/

        primaryStage.setTitle("Lab Trap!");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        //setLoop();
        //setLoop();



        //loadBackground();

        //startGameLoop();
    }

    private void loadBackground() {
        backgroundImageView = new ImageView( getClass().getResource( "spaceBackground.jpg").toExternalForm());
        //backgroundImageView.fitWidthProperty().bind(layer.widthProperty());
        //backgroundImageView.fitHeightProperty().bind(layer.heightProperty());
        backgroundImageView.relocate( 0, -backgroundImageView.getImage().getHeight() + 275);
        layer.getChildren().add(backgroundImageView);
    }

    /*private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double y = 0;
                double x = 0;
                if (root.isUp()) {
                    y = backgroundImageView.getLayoutY() + scroll;
                }
                if (root.isDown()) {
                    y = backgroundImageView.getLayoutY() - scroll;
                }
                if (root.isLeft()) {
                    x = backgroundImageView.getLayoutX() - scroll;
                }
                if (root.isRight()) {
                    x = backgroundImageView.getLayoutX() + scroll;
                }

                backgroundImageView.setLayoutY( y);
                backgroundImageView.setLayoutX( x);
            }
        };

        gameLoop.start();
    }*/

    private void setLoop() {
        AnimationTimer timer = new AnimationTimer() {

            private long lastUpdate = 0 ;
            @Override
            public void handle(long time) {
                if (lastUpdate > 0) {
                    long elapsedNanos = time - lastUpdate ;
                    double elapsedSeconds = elapsedNanos / 1_000_000_000.0 ;
                    double vDelta = 0 ;
                    double hDelta = 0;
                    if (moveUp) {
                        vDelta = -scroll * elapsedSeconds ;
                    }
                    if (moveDown) {
                        vDelta = scroll * elapsedSeconds ;
                    }
                    if (moveLeft) {
                        hDelta = -scroll * elapsedSeconds;
                    }
                    if (moveRight) {
                        hDelta = scroll * elapsedSeconds;
                    }
                    double newVValue =
                            clamp(scroller.getVvalue() + vDelta, scroller.getVmin(), scroller.getVmax());
                    scroller.setVvalue(newVValue);
                    double newHValue =
                            clamp(scroller.getHvalue() + hDelta, scroller.getHmin(), scroller.getHmax());
                    scroller.setHvalue(newHValue);
                }
                lastUpdate = time ;
            }
        };

        timer.start();
    }

    private double clamp(double value, double min, double max) {
        return Math.min(max, Math.max(min, value));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
