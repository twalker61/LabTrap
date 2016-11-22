package game;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Created by BurtonGuster on 11/17/16.
 */
public class PlayerCanvas extends Canvas {

    private Image player;
    private double groundElevation;
    private double playerHeight;
    private double startX;
    private double startY;
    private double currentX;
    private double currentY;
    private double jumpMax;
    private GraphicsContext gc;

    public PlayerCanvas(double width, double height) {
        super(width, height);
        playerHeight = 20;
        groundElevation = playerHeight + 10;
        startX = 0;
        startY = getHeight() - groundElevation;
        currentX = startX;
        currentY = startY;
        jumpMax = startY - 40;
        gc = this.getGraphicsContext2D();
        gc.setLineWidth(2.0);
        gc.setFill(Color.RED);
        gc.fillOval(startX, startY, playerHeight, playerHeight);
    }

    public double getGroundElevation() {
        return groundElevation;
    }

    public void draw(double x, double y) {
        gc.fillOval(x, y, playerHeight, playerHeight);
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
        return currentX + playerHeight/2;
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(currentX, currentY, playerHeight, playerHeight);
    }

    public double getJumpMax() {
        return jumpMax;
    }

    public void clear() {
        gc.clearRect(0, 0, getWidth(), getHeight());
    }
}
