package game;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by twalker61 on 11/14/16.
 */
public class GameScreen extends StackPane {

    private AnchorPane backgroundElements;
    private HBox background;
    private List<PortalButton> buttons;
    private List<Floor> floorTiles;
    private List<Wall> walls;
    private ExitPortal exit;
    private double mouseX;
    private double mouseY;
    private GameElement lastPiece;
    private List<GameElement> lastPieceList;
    private String pieceKey;
    private boolean builderMode;
    private static Main main;

    public GameScreen(Main m, boolean mode) {
        builderMode = mode;
        main = m;
        backgroundElements = new AnchorPane();
        background = new HBox();
        Floor starter = new Floor();
        AnchorPane.setTopAnchor(starter, 550 - (starter.getImage().getHeight()));
        AnchorPane.setLeftAnchor(starter, 0.0);
        starter.setPositionY(starter.getImage().getHeight());
        starter.setPositionX(10);
        backgroundElements.getChildren().add(starter);
        ImageView img = new ImageView(getClass().getResource("../images/labtrapBackgroundCombined.png").toExternalForm());
        background.getChildren().add(img);
        backgroundElements.setPrefHeight(550);
        backgroundElements.setMaxHeight(550);

        this.getChildren().addAll(background, backgroundElements);

        buttons = new ArrayList<>();
        main.setButtonList(buttons);
        floorTiles = new ArrayList<>();
        floorTiles.add(starter);
        main.setFloorList(floorTiles);
        walls = new ArrayList<>();
        main.setWallList(walls);

        if (builderMode) {
            setMouseListener();
        }
    }

    public ImageView getBackgroundImage() {
        return (ImageView) background.getChildren().get(0);
    }

    private void setMouseListener() {
        this.setOnMouseMoved(e -> {
            mouseX = e.getX();
            mouseY = e.getY();
        });

        this.setOnMouseClicked(e -> {
            if (pieceKey != null ) {
                if (pieceKey.equals("w")) {
                    lastPiece = new Wall();
                    //lastPieceList = walls;
                    walls.add((Wall) lastPiece);
                    main.setWallList(walls);
                } else if (pieceKey.equals("f")) {
                    lastPiece = new Floor();
                    floorTiles.add((Floor) lastPiece);
                    main.setFloorList(floorTiles);
                } else if (pieceKey.equals("b")) {
                    lastPiece = new PortalButton();
                    buttons.add((PortalButton) lastPiece);
                    main.setButtonList(buttons);
                } else if (pieceKey.equals("e")) {
                    lastPiece = new ExitPortal();
                    exit = (ExitPortal) lastPiece;
                    main.setExitPortal(exit);
                }
                AnchorPane.setTopAnchor(lastPiece, mouseY);
                AnchorPane.setLeftAnchor(lastPiece, mouseX);
                lastPiece.setPositionX(mouseX);
                lastPiece.setPositionY(mouseY);
                backgroundElements.getChildren().add(lastPiece);
            }
        });
    }

    public void setPieceSelector(KeyEvent e) {
        KeyCode k = e.getCode();
        pieceKey = k.getName().toLowerCase();
        if (pieceKey.equals("u")) {
            //TODO: undo a lastPiece creation? Need to know which lastPiece was made to remove last index from the right list
        }
    }
}
