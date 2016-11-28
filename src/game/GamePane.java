package game;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    private Label timerLabel;
    private StringProperty clock = new SimpleStringProperty("00:00:00");
    private Timeline timeline;
    private LocalTime time;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.systemDefault());
    private Label buttonCountLabel;
    private HBox bottomBar;
    private Button playButton;
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
    private boolean builderMode;
    private int buttonCount;

    private Main main;

    public GamePane(Main m, boolean mode) {
        main = m;
        builderMode = mode;
        playerDescent = true;

        layers = new StackPane();
        gameScreen = new GameScreen(main);
        scroller = new ScrollPane();
        scroller.setContent(gameScreen);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setLoop();

        setKeyEvents();

        elementBar = new VBox(10);
        elements = new ArrayList<>();
        //fill list with thumbnails
        elementBar.getChildren().addAll(elements);
        topBar = new HBox(10);
        buttonCountLabel = new Label();
        updateButtonCount();

        time = LocalTime.now();
        timerLabel = new Label();
        timerLabel.textProperty().bind(clock);
        timeline = new Timeline(new KeyFrame(Duration.ZERO, e->{
            clock.set(LocalTime.now().minusNanos(time.toNanoOfDay()).format(fmt));
        }),new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        topBar.getChildren().addAll(timerLabel, buttonCountLabel);
        bottomBar = new HBox(10);
        playButton = new Button("Play Game");
        playButton.setOnAction(e -> {
            main.switchToGamePlay();
        });
        statusCheck = new Label("");
        bottomBar.getChildren().addAll(playButton, statusCheck);

        if (!builderMode) {
            playerCanvas = new PlayerCanvas(1017, 550, scroller);
            playerCanvas.setMouseTransparent(true);
            playButton.setVisible(false);
        } else {
            buttonCountLabel.setVisible(false);
            timerLabel.setVisible(false);
        }
        setTop(topBar);
        setBottom(bottomBar);
        setLeft(elementBar);
        if (!builderMode) {
            layers.getChildren().addAll(scroller, playerCanvas);
        } else {
            layers.getChildren().addAll(scroller);
        }
        setCenter(layers);

    }

    private void updateButtonCount() {
        buttonCountLabel.setText("Buttons Pressed: " + buttonCount);
    }

    private void setKeyEvents() {
        this.setOnKeyPressed(e -> {
            KeyCode k = e.getCode();
            if (k.isArrowKey()) {
                if (k.name().equals("UP")) {
                    scrollLock = true;
                    jump = true;
                    if (!builderMode) {
                        jumpHeight = playerCanvas.getY() - playerCanvas.getJumpMax();
                    }
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
            } else {
                gameScreen.setPieceSelector(e);
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
            double scroll = .5;
            double increment;
            double vVal = scroller.getVvalue();
            @Override
            public void handle(long time) {
                boolean grounded = false;
                boolean rightBlocked = false;
                boolean leftBlocked = false;
                boolean hitButton = false;
                if (!builderMode) {
                    increment = playerCanvas.getJumpMax() /20;
                    for (Floor f : main.getFloorList()) {
                        if (f.collision(playerCanvas)) {
                            grounded = true;
                            //System.out.println("true");
                            //System.out.println("Intersect with " + f);
                        }
                        //System.out.println(grounded);
                    }
                    for (Wall w : main.getWallList()) {
                        if (w.collision(playerCanvas)) {
                            //System.out.println("Blocked");
                            if (w.getCenterX() > playerCanvas.getCenterX()) {
                                rightBlocked = true;
                            } else {
                                leftBlocked = true;
                            }
                        }
                    }
                    for (PortalButton b : main.getButtonList()) {
                        if (b.collision(playerCanvas)) {
                            hitButton = true;
                            if (!b.pressed()) {
                                b.press();
                                buttonCount++;
                                updateButtonCount();
                            }
                            if (buttonCount >= 3) {
                                main.getExitPortal().open();
                            }
                        }
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

                    if (!builderMode) {
                        playerCanvas.clear();
                        playerCanvas.draw(playerCanvas.getX(), playerCanvas.getY(), direction);
                    }

                    int magnify = (hDelta < 0) ? 500 : 300;
                    /*int magnify = 300;
                    if (hDelta < 0) {
                        magnify = 500;
                    }*/
                    double newHValue =
                            clamp(scroller.getHvalue() + hDelta, scroller.getHmin(), scroller.getHmax());

                    if (!builderMode) {
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
                    } else {
                        scroller.setHvalue(newHValue);
                    }

                    // if jump, move up and player descent = false. When I hit jumpHeight, jump = false and player descent = true

                    if (!builderMode) {
                        if (jump) {
                            playerCanvas.clear();
                            playerDescent = false;
                            if (hitButton) {
                                playerDescent = true;
                            }
                            if (playerCanvas.getY() > jumpHeight) {
                                playerCanvas.draw(playerCanvas.getX(), playerCanvas.getY() - increment, direction);
                            } else {
                                playerDescent = true;
                                jump = false;
                            }
                        }
                        playerDescent = !(grounded || jump);

                    /*if (grounded) {
                        playerDescent = false;
                    } else if (jump){
                        playerDescent = false;
                    } else {
                        playerDescent = true;
                    }*/
                        if (playerDescent) {
                            playerCanvas.draw(playerCanvas.getX(), playerCanvas.getY() + increment, direction);
                        }


                    /*playerDescent = true;
                    if (!grounded) {
                    } else {
                        // previous 'if' logic: playerCanvas.getY() >= playerCanvas.getHeight() - playerCanvas.getGroundElevation()
                        jump = false;
                        playerDescent = false;
                    }*/
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
