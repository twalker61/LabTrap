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
    private PlayerCanvas playerCanvas;

    //private boolean moveUp;
    //private boolean moveDown;
    private boolean moveLeft;
    private boolean moveRight;
    private boolean scrollLock;
    private boolean jump;
    private boolean duck;
    private boolean playerDescent;
    private double jumpHeight;

    public GamePane() {
        layers = new StackPane();
        gameScreen = new GameScreen();
        scroller = new ScrollPane();
        scroller.setContent(gameScreen);
        playerCanvas = new PlayerCanvas(550, 550);
        playerCanvas.setMouseTransparent(true);

        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setLoop();

        setKeyEvents();

        elementBar = new VBox(10);
        elements = new ArrayList<>();
        //fill list with thumbnails
        elementBar.getChildren().addAll(elements);
        topBar = new HBox(10);
        buttonCount = new Label("Buttons Pressed: " + gameScreen.getButtonCount() + "/5");

        time = LocalTime.now();
        timer = new Label();
        timer.textProperty().bind(clock);
        timeline = new Timeline(new KeyFrame(Duration.ZERO, e->{
            clock.set(LocalTime.now().minusNanos(time.toNanoOfDay()).format(fmt));
        }),new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
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

    }

    private void setKeyEvents() {
        this.setOnKeyPressed(e -> {
            KeyCode k = e.getCode();
            if (k.isArrowKey()) {
                if (k.name().equals("UP")) {
                    scrollLock = true;
                    jump = true;
                    jumpHeight = playerCanvas.getY() - playerCanvas.getJumpMax();
                }
                if (k.name().equals("DOWN")) {
                    scrollLock = true;
                    duck = true;
                }
                if (k.name().equals("LEFT")) {
                    moveLeft = true;
                }
                if (k.name().equals("RIGHT")) {
                    moveRight = true;
                }
            }
        });

        this.setOnKeyReleased(e -> {
            KeyCode k = e.getCode();
            if (k.isArrowKey()) {
                if (k.name().equals("UP")) {
                    scrollLock = false;
                }
                if (k.name().equals("DOWN")) {
                    scrollLock = false;
                    duck = false;
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

    private void setLoop() {
        AnimationTimer timer = new AnimationTimer() {

            long lastUpdate = 0 ;
            double scroll = .75;
            double increment = playerCanvas.getJumpMax() /15;
            double vVal = scroller.getVvalue();
            @Override
            public void handle(long time) {
                boolean grounded = false;
                boolean rightBlocked = false;
                boolean leftBlocked = false;
                boolean hitButton = false;

                for (Floor f : gameScreen.getFloors()) {
                    if (f.collision(playerCanvas)) {
                        grounded = true;
                        //System.out.println("true");
                    }
                }
                for (Wall w : gameScreen.getWalls()) {
                    if (w.collision(playerCanvas)) {
                        if (w.getCenterX() > playerCanvas.getCenterX()) {
                            rightBlocked = true;
                        } else {
                            leftBlocked = true;
                        }
                    }
                }
                for (PortalButton b : gameScreen.getButtons()) {
                    if (b.collision(playerCanvas)) {
                        hitButton = true;
                    }
                }

                //if playerCanvas and game elements intersecting, react.
                //otherwise, do this normal position update
                if (lastUpdate > 0) {
                    long elapsedNanos = time - lastUpdate;
                    double elapsedSeconds = elapsedNanos / 1_000_000_000.0;
                    double hDelta = 0;
                    if (scrollLock) {
                        scroller.setVvalue(vVal);
                    }
                    if (moveLeft && !leftBlocked) {
                        hDelta = -scroll * elapsedSeconds;
                    }
                    if (moveRight && !rightBlocked) {
                        hDelta = scroll * elapsedSeconds;
                    }

                    int direction = (int)(hDelta * 1000);

                    playerCanvas.clear();
                    playerCanvas.draw(playerCanvas.getX(), playerCanvas.getY(), direction);

                    int magnify = 300;
                    if (hDelta < 0) {
                        magnify = 500;
                    }
                    double newHValue =
                            clamp(scroller.getHvalue() + hDelta, scroller.getHmin(), scroller.getHmax());
                    if (playerCanvas.getX() > playerCanvas.getStartX()) {
                        playerCanvas.clear();
                        playerCanvas.draw(playerCanvas.getX() + hDelta * magnify, playerCanvas.getY(), direction);
                    } else {
                        scroller.setHvalue(newHValue);
                    }
                    if (newHValue == scroller.getHmax() && moveRight) {
                        playerCanvas.clear();
                        playerCanvas.draw(playerCanvas.getX() + hDelta * magnify, playerCanvas.getY(), direction);
                    }

                    if (jump) {
                        playerCanvas.clear();
                        if (hitButton) {
                            playerDescent = true;
                        }
                        if (playerCanvas.getY() > jumpHeight && !playerDescent) {
                            playerCanvas.draw(playerCanvas.getX(), playerCanvas.getY() - increment, direction);
                        } else {
                            playerCanvas.draw(playerCanvas.getX(), playerCanvas.getY() + increment, direction);
                            playerDescent = true;
                        }
                        if (grounded) {
                            // previous 'if' logic: playerCanvas.getY() >= playerCanvas.getHeight() - playerCanvas.getGroundElevation()
                            jump = false;
                            playerDescent = false;
                        }
                    }
                }
                lastUpdate = time;
            }
        };

        timer.start();
    }

    private double clamp(double value, double min, double max) {
        return Math.min(max, Math.max(min, value));
    }

    public ScrollPane getScroller() {
        return scroller;
    }
    public Canvas getCanvas() {
        return playerCanvas;
    }

}
