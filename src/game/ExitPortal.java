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
        super.setImage(new Image(getClass().getResource("../images/closedPortal.png").toExternalForm()));
        openPortal = new ImageView(new Image(getClass().getResource("../images/openPortal.png").toExternalForm()));
    }

    public void open() {
        super.setImage(openPortal.getImage());
        open = true;
    }

    public boolean isOpen() {
        return open;
    }

    @Override
    public void setHover() {
        super.setImage(new Image(getClass().getResource("../images/portalOutline.png").toExternalForm()));
        hover();
    }

}
