package game;

import javafx.collections.ObservableList;
import javafx.scene.Node;
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

    public GameScreen() {
        backgroundElements = new HBox();
        playerPane = new Pane();
        playerPane.getChildren().add(new Player(new Image("")));
        getChildren().addAll(backgroundElements, playerPane);
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
