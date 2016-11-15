package game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

//import java.util.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BurtonGuster on 11/14/16.
 */
public class GamePane extends BorderPane {

    private VBox elementBar;
    private List<ImageView> elements;
    private HBox topBar;
    private Label timer;
    private final StringProperty clock = new SimpleStringProperty("00:00:00");
    private Timeline timeline;
    private LocalTime time;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.systemDefault());
    private Label buttonCount;
    private HBox bottomBar;
    private Button restart;
    private GameScreen screen;

    public GamePane() {
        screen = new GameScreen();

        elementBar = new VBox(10);
        elements = new ArrayList<>();
        //fill list with thumbnails
        elementBar.getChildren().addAll(elements);
        topBar = new HBox(10);
        buttonCount = new Label("Buttons Pressed: " + screen.getButtonCount() + "/5");
        timer = new Label();
        timer.textProperty().bind(clock);
        timeline = new Timeline(new KeyFrame(Duration.ZERO, e->{
            clock.set(LocalTime.now().minusNanos(time.toNanoOfDay()).format(fmt));
        }),new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        topBar.getChildren().addAll(timer, buttonCount);
        bottomBar = new HBox(10);
        restart = new Button("Restart");
        bottomBar.getChildren().addAll(restart);
        screen = new GameScreen();

        setTop(topBar);
        setBottom(bottomBar);
        setLeft(elementBar);
        setCenter(screen);
    }
}
