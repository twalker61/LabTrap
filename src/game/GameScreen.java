package game;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by twalker61 on 11/14/16.
 */
public class GameScreen extends StackPane {

    private HBox backgroundElements;
    private Pane playerPane;
    private List<PortalButton> buttons;
    private Label test;

    public GameScreen() {
        test = new Label("GameScreen");
        backgroundElements = new HBox();
        playerPane = new Pane();
        playerPane.getChildren().add(new Player(new Image(getClass().getResource( "spaceBackground.jpg").toExternalForm())));
        //bind gamescreen to size of image
        //backgroundImageView.fitWidthProperty().bind(layer.widthProperty());
        //is image loading?
        //make player image an imageview?
        getChildren().addAll(backgroundElements, playerPane, test);
        buttons = new ArrayList<>();
    }

    public int getButtonCount() {
        if (buttons.size() == 0) {
            return 0;
        }
        return buttons.get(0).getNumPressed();
    }

    public void move(KeyCode k) {
        if (k.name().equals("RIGHT")) {
            //shift all background elements to the left
        }
        if (k.name().equals("UP")) {
            //make player jump...
        }
    }

    public ObservableList<Node> getChildren() {
        return super.getChildren();
    }
}
