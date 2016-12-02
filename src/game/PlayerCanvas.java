package game;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;

/**
 * Created by BurtonGuster on 11/17/16.
 */
public class PlayerCanvas extends Canvas {

    private Image playerForward;
    private Image playerBackward;
    private double groundElevation;
    private double startX;
    private double startY;
    private double currentX;
    private double currentY;
    private double jumpMax;
    private GraphicsContext gc;
    private boolean forward;
    private ScrollPane scroll;

    public PlayerCanvas(double width, double height, ScrollPane s) {
        super(width, height);
        groundElevation = 80;
        startX = 0;
        startY = getHeight() - groundElevation;
        currentX = startX;
        currentY = startY;
        jumpMax = 100;
        gc = this.getGraphicsContext2D();
        playerForward = new Image(getClass().getResource("../images/labtrapRat.png").toExternalForm());
        playerBackward = new Image(getClass().getResource("../images/labtrapRatFlipped.png").toExternalForm());
        gc.drawImage(playerForward, startX, startY);
        forward = true;
        scroll = s;
    }

    public double getGroundElevation() {
        return groundElevation;
    }

    public void draw(double x, double y, int direction) {
        if (direction > 0) {
            forward = true;
        }
        if (direction < 0) {
            forward = false;
        }
        if (x > currentX || forward) {
            gc.drawImage(playerForward, x, y);
            forward = true;
        } else {
            gc.drawImage(playerBackward, x ,y);
        }

        currentX = x;
        currentY = y;
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public double getX() {
        return currentX;
    }

    public double getY() {
        return currentY;
    }

    public double getCenterX() {
        return currentX + playerForward.getWidth()/2.0;
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(currentX + scroll.getHvalue() * ((GameScreen)(scroll.getContent())).getBackgroundImage().getImage().getWidth() * .5, currentY, playerForward.getWidth(), playerForward.getHeight());
    }

    public double getJumpMax() {
        return jumpMax;
    }

    public void clear() {
        gc.clearRect(0, 0, getWidth(), getHeight());
    }
}
