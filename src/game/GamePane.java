package game;

import com.sun.tools.javah.Util;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Created by twalker61 on 11/14/16.
 */
public class GamePane extends BorderPane {

    private StackPane layers;
    private HBox topBar;
    private Label timerLabel;
    private StringProperty clock = new SimpleStringProperty("00:00:00");
    private Timeline timeline;
    private LocalTime time;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.systemDefault());
    private Label buttonCountLabel;
    private Label portalStatus;
    private HBox bottomBar;
    private Button playButton;
    private GameScreen gameScreen;
    private ScrollPane scroller;
    private PlayerCanvas playerCanvas;

    private boolean moveLeft;
    private boolean moveRight;
    private boolean scrollLock;
    private boolean jump;
    private boolean playerDescent;
    private double jumpHeight;
    private boolean builderMode;
    private int buttonCount;
    private boolean notHere;
    private boolean upPressed;

    private static Main main;

    public GamePane(Main m, boolean mode) {
        main = m;
        builderMode = mode;
        playerDescent = true;

        layers = new StackPane();
        gameScreen = new GameScreen(main, builderMode);
        scroller = new ScrollPane();
        scroller.setContent(gameScreen);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setLoop();

        setKeyEvents();

        topBar = new HBox(10);
        buttonCountLabel = new Label();
        portalStatus = new Label();
        updateButtonCount();

        time = LocalTime.now();
        timerLabel = new Label();
        timerLabel.textProperty().bind(clock);
        timeline = new Timeline(new KeyFrame(Duration.ZERO, e->{
            clock.set(LocalTime.now().minusNanos(time.toNanoOfDay()).format(fmt));
        }),new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        topBar.getChildren().addAll(timerLabel, buttonCountLabel, portalStatus);
        bottomBar = new HBox(10);
        playButton = new Button("Play Game");
        playButton.setOnAction(e -> {
            main.switchToGamePlay();
        });
        bottomBar.getChildren().addAll(playButton);

        if (!builderMode) {
            playerCanvas = new PlayerCanvas(1017, 550, scroller);
            playerCanvas.setMouseTransparent(true);
            playButton.setVisible(false);
        } else {
            buttonCountLabel.setVisible(false);
            portalStatus.setVisible(false);
            timerLabel.setVisible(false);
        }
        setTop(topBar);
        setBottom(bottomBar);
        if (!builderMode) {
            layers.getChildren().addAll(scroller, playerCanvas);
        } else {
            layers.getChildren().addAll(scroller);
        }
        setCenter(layers);
        upPressed = true;

    }

    private void updateButtonCount() {
        buttonCountLabel.setText("Buttons Pressed: " + buttonCount);
        String status = (buttonCount >= 3) ? "Unlocked" : "Locked";
        portalStatus.setText("Portal " + status);
    }

    private void setKeyEvents() {
        this.setOnKeyPressed(e -> {
            KeyCode k = e.getCode();
            if (k.isArrowKey()) {
                if (k.name().equals("UP") && upPressed) {
                    upPressed = false;
                    scrollLock = true;
                    jump = true;
                    if (!builderMode) {
                        jumpHeight = playerCanvas.getY() - playerCanvas.getJumpMax();
                    }
                }
                if (k.name().equals("DOWN")) {
                    scrollLock = true;
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
                            upPressed = true;
                        }
                    }
                    for (Wall w : main.getWallList()) {
                        if (w.collision(playerCanvas)) {
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
                        }
                    }
                    if (buttonCount >= 3) {
                        for (ExitPortal e : main.getExitPortals()) {
                            e.open();
                        }
                    }

                    for (ExitPortal e : main.getExitPortals()) {
                        if (e.collision(playerCanvas) && e.isOpen()) {
                            if (!notHere) {
                                Platform.runLater(() -> {
                                    main.switchToResults(true);
                                    notHere = true;
                                });
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
                        if (playerCanvas.getY() > 600) {
                            if (!notHere) {
                                main.switchToResults(false);
                                notHere = true;
                            }
                        }
                        playerCanvas.clear();
                        playerCanvas.draw(playerCanvas.getX(), playerCanvas.getY(), direction);
                    }

                    int magnify = (hDelta < 0) ? 500 : 300;
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
                            if (playerCanvas.getY() > jumpHeight) {
                                playerCanvas.draw(playerCanvas.getX(), playerCanvas.getY() - increment, direction);
                            } else {
                                playerDescent = true;
                                jump = false;
                            }
                        }
                        playerDescent = !(grounded || jump);

                        if (playerDescent) {
                            playerCanvas.draw(playerCanvas.getX(), playerCanvas.getY() + increment, direction);
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

    /*public ScrollPane getScroller() {
        return scroller;
    }
    public Canvas getCanvas() {
        return playerCanvas;
    }*/

}
