package game;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by twalker61 on 11/14/16.
 */
public class GameScreen extends HBox {

    //private HBox backgroundElements;
    //private Canvas playerCanvas;
    private List<PortalButton> buttons;
    private List<Floor> floorTiles;
    private List<Wall> walls;

    public GameScreen() {
        //backgroundElements = new HBox();
        /*Image background = new Image(getClass().getResource("spaceBackground.jpg").toExternalForm());
        BackgroundImage myBI= new BackgroundImage(background,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        this.setBackground(new Background(myBI));*/
        /*String image = getClass().getResource("spaceBackground.jpg").toExternalForm();
        this.setStyle("-fx-background-image: url('" + image + "'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch;");*/
        ImageView img = new ImageView(getClass().getResource("labtrapBackground.png").toExternalForm());
        //img.fitHeightProperty().bind(this.heightProperty());
        this.getChildren().add(img);

        buttons = new ArrayList<>();
        floorTiles = new ArrayList<>();
        walls = new ArrayList<>();
    }

    public int getButtonCount() {
        if (buttons.size() == 0) {
            return 0;
        }
        return buttons.get(0).getNumPressed();
    }

    public List<PortalButton> getButtons() {
        return buttons;
    }
    public List<Floor> getFloors() {
        return floorTiles;
    }
    public List<Wall> getWalls() {
        return walls;
    }

    /*public void move(KeyCode k) {
        if (k.name().equals("RIGHT")) {
            //shift all background elements to the left
        }
        if (k.name().equals("UP")) {
            //make player jump...
        }
    }*/

    /*public ObservableList<Node> getChildren() {
        return super.getChildren();
    }*/
}
