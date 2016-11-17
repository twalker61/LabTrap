package game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Created by BurtonGuster on 11/17/16.
 */
public class PlayerCanvas extends Canvas {

    private Image player;

    public PlayerCanvas(double width, double height) {
        super(width, height);
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setLineWidth(2.0);
        gc.setFill(Color.RED);
        double playerHeight = 20;
        gc.fillOval(getWidth()/2 - playerHeight,
                getHeight() - playerHeight - 10, playerHeight, playerHeight);
    }
}
