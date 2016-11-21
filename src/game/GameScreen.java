package game;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
        ImageView img = new ImageView(getClass().getResource("spaceBackground.jpg").toExternalForm());
        this.getChildren().add(img);
        //backgroundElements.setOpacity(.5);
        //playerCanvas = new Canvas(400, 400);
        //playerCanvas.widthProperty().bind(this.widthProperty());
        //playerCanvas.heightProperty().bind(this.heightProperty());
        //bind gamescreen to size of image
        //backgroundImageView.fitWidthProperty().bind(layer.widthProperty());
        //is image loading?
        //make player image an imageview?
        /*GraphicsContext gc = playerCanvas.getGraphicsContext2D();
        gc.setLineWidth(2.0);
        gc.setFill(Color.RED);
        gc.fillRoundRect(10, 10, 50, 50, 10, 10);*/

        //getChildren().addAll(backgroundElements);
        //getChildren().add(playerCanvas);
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
