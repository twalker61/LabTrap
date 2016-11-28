package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by twalker61 on 11/14/16.
 */
public class ExitPortal extends GameElement {

    private boolean open;
    private ImageView openPortal;

    public ExitPortal() {
        super();
        super.setImage(new Image(getClass().getResource("../images/exitPortalClosed.png").toExternalForm()));
        openPortal = new ImageView(new Image(getClass().getResource("../images/exitPortal1.png").toExternalForm()));
    }

    public void open() {
        super.setImage(openPortal.getImage());
        open = true;
    }

    public boolean isOpen() {
        return open;
    }

}
