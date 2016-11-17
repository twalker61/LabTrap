package game;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

//import java.util.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by twalker61 on 11/14/16.
 */
public class GamePane extends BorderPane {

    private StackPane layers;
    private VBox elementBar;
    private List<ImageView> elements;
    private HBox topBar;
    private Label timer;
    private StringProperty clock = new SimpleStringProperty("00:00:00");
    private Timeline timeline;
    private LocalTime time;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.systemDefault());
    private Label buttonCount;
    private HBox bottomBar;
    private Button restart;
    private Label statusCheck;
    private GameScreen gameScreen;
    private ScrollPane scroller;
    private Canvas playerCanvas;

    private boolean moveUp;
    private boolean moveDown;
    private boolean moveLeft;
    private boolean moveRight;

    public GamePane() {
        layers = new StackPane();
        gameScreen = new GameScreen();
        scroller = new ScrollPane();
        scroller.setContent(gameScreen);
        playerCanvas = new Canvas(300, 275);
        playerCanvas.setMouseTransparent(true);
        /*int top = (int)snappedTopInset();
        int right = (int)snappedRightInset();
        int bottom = (int)snappedBottomInset();
        int left = (int)snappedLeftInset();
        int w = (int)getWidth() - left - right;
        int h = (int)getHeight() - top - bottom;
        playerCanvas.setLayoutX(left);
        playerCanvas.setLayoutY(top);
        playerCanvas.setWidth(w);
        playerCanvas.setHeight(h);*/

        GraphicsContext gc = playerCanvas.getGraphicsContext2D();
        gc.setLineWidth(2.0);
        gc.setFill(Color.RED);
        gc.fillRoundRect(10, 10, 50, 50, 10, 10);

        setScrollerEvent();
        setLoop();

        elementBar = new VBox(10);
        elements = new ArrayList<>();
        //fill list with thumbnails
        elementBar.getChildren().addAll(elements);
        topBar = new HBox(10);
        buttonCount = new Label("Buttons Pressed: " + gameScreen.getButtonCount() + "/5");
        timer = new Label();
        timer.textProperty().bind(clock);
        timeline = new Timeline(new KeyFrame(Duration.ZERO, e->{
            clock.set(LocalTime.now().minusNanos(time.toNanoOfDay()).format(fmt));
        }),new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        topBar.getChildren().addAll(timer, buttonCount);
        bottomBar = new HBox(10);
        restart = new Button("Restart");
        statusCheck = new Label("");
        bottomBar.getChildren().addAll(restart, statusCheck);

        setTop(topBar);
        setBottom(bottomBar);
        setLeft(elementBar);
        layers.getChildren().addAll(scroller, playerCanvas);
        setCenter(layers);

        //addKeyHandler(this);
    }

    public boolean isUp() {
        return moveUp;
    }

    public boolean isDown() {
        return moveDown;
    }

    public boolean isLeft() {
        return moveLeft;
    }

    public boolean isRight() {
        return moveRight;
    }

    public void setMoveUp(boolean b) {
        moveUp = b;
    }
    public void setMoveDown(boolean b) {
        moveDown = b;
    }
    public void setMoveRight(boolean b) {
        moveRight = b;
    }
    public void setMoveLeft(boolean b) {
        moveLeft = b;
    }

    private void setScrollerEvent() {
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
        });
    }

    private void addKeyHandler(Node node) {
        node.setOnKeyPressed(e -> {
            KeyCode k = e.getCode();
            if (k.isArrowKey()) {
                gameScreen.move(k);
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
    }

    private void setLoop() {
        AnimationTimer timer = new AnimationTimer() {

            long lastUpdate = 0 ;
            double scroll = .5;
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

}
