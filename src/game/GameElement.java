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

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, 500 - positionY, width, height);
    }

    public double getCenterX() {
        return positionX + width/2;
    }

    public void shift(double x, double y) {
        positionX += x;
        positionY += y;
    }

    public boolean collision(PlayerCanvas g) {
        //System.out.println("In method");

        /*System.out.println("Floor Min y: " + this.getBoundary().getMinY());
        System.out.println("Floor Max y: " + this.getBoundary().getMaxY());
        System.out.println("Rat Min y: " + g.getBoundary().getMinY());
        System.out.println("Rat Max y: " + g.getBoundary().getMaxY());

        System.out.println("Floor Min x: " + this.getBoundary().getMinX());
        System.out.println("Floor Max x: " + this.getBoundary().getMaxX());
        System.out.println("Rat Min x: " + g.getBoundary().getMinX());
        System.out.println("Rat Max x: " + g.getBoundary().getMaxX());
        System.out.println(this.getBoundary().intersects(g.getBoundary()));*/

        return this.getBoundary().intersects(g.getBoundary());
    }

}
