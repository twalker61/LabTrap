package game;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * Created by BurtonGuster on 11/14/16.
 */
public abstract class GameElement extends Pane {

    //make each game element a pane?
    private Image img;
    private double positionX;
    private double positionY;
    private double width;
    private double height;

    public GameElement(Image i) {
        img = i;
        width = i.getWidth();
        height = i.getHeight();
    }

    public void setPositionX(double x) {
        positionX = x;
    }

    public void setPositionY(double y) {
        positionY = y;
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    public void shift(double x, double y) {
        positionX += x;
        positionY += y;
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(img, positionX, positionY);
    }

    public boolean collision(GameElement g) {
        return getBoundary().intersects(g.getBoundary());
    }

    public abstract void react(GameElement g);
}
