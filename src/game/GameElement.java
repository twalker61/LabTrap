package game;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Created by twalker61 on 11/14/16.
 */
public class GameElement extends Pane {

    //make each game element a pane?
    private ImageView img;
    private double positionX;
    private double positionY;
    private double width;
    private double height;

    public GameElement() {
    }

    public void setImage(Image i) {
        img = new ImageView(i);
        width = i.getWidth();
        height = i.getHeight();
        this.getChildren().add(img);
    }

    public Image getImage() {
        return img.getImage();
    }

    public void setPositionX(double x) {
        positionX = x;
    }

    public void setPositionY(double y) {
        positionY = y;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    public double getCenterX() {
        return positionX + width/2;
    }

    public void shift(double x, double y) {
        positionX += x;
        positionY += y;
    }

    public boolean collision(PlayerCanvas g) {
        return this.getBoundary().intersects(g.getBoundary());
    }

}
