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
    private Main main;

    public GameScreen(Main m) {
        main = m;
        backgroundElements = new AnchorPane();
        background = new HBox();
        Floor starter = new Floor();
        AnchorPane.setTopAnchor(starter, 550 - (starter.getImage().getHeight()));
        AnchorPane.setLeftAnchor(starter, 0.0);
        starter.setPositionY(starter.getImage().getHeight());
        starter.setPositionX(10);
        backgroundElements.getChildren().add(starter);
        /*Floor f1 = new Floor();
        Floor f2 = new Floor();
        Floor f3 = new Floor();
        Floor f4 = new Floor();
        Wall w = new Wall();
        backgroundElements.setBottomAnchor(f1, 10.0);
        backgroundElements.setLeftAnchor(f1, 0.0);
        f1.setPositionY(f1.getImage().getHeight());
        f1.setPositionX(10);
        backgroundElements.setBottomAnchor(f2, 50.0);
        backgroundElements.setLeftAnchor(f2, f1.getImage().getWidth() + 10);
        f2.setPositionY(f1.getImage().getHeight() + 50);
        f2.setPositionX(f1.getImage().getWidth() + 10);
        backgroundElements.setBottomAnchor(f3, 90.0);
        backgroundElements.setLeftAnchor(f3, f1.getImage().getWidth()*2 + 10);
        f3.setPositionY(f1.getImage().getHeight() + 90);
        f3.setPositionX(f1.getImage().getWidth()*2 + 10);
        backgroundElements.setBottomAnchor(f4, 130.0);
        backgroundElements.setLeftAnchor(f4, f1.getImage().getWidth()*3 + 10);
        f4.setPositionY(f1.getImage().getHeight() + 130);
        f4.setPositionX(f1.getImage().getWidth()*3 + 10);
        backgroundElements.setBottomAnchor(w, 100.0);
        backgroundElements.setLeftAnchor(w, f1.getImage().getWidth()*4 + 10);
        w.setPositionX(f1.getImage().getWidth()*4 + 10);
        w.setPositionY(w.getImage().getHeight() + 100);
        backgroundElements.getChildren().addAll(f1, f2, f3, f4, w);*/
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
        /*floorTiles.add(f1);
        floorTiles.add(f2);
        floorTiles.add(f3);
        floorTiles.add(f4);*/
        walls = new ArrayList<>();
        main.setWallList(walls);
        //walls.add(w);

        //setPieceSelector();
        setMouseListener();
    }

    public ImageView getBackgroundImage() {
        return (ImageView) background.getChildren().get(0);
    }

    /*public List<PortalButton> getButtons() {
        return buttons;
    }
    public List<Floor> getFloors() {
        return floorTiles;
    }
    public List<Wall> getWalls() {
        return walls;
    }*/

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
        //System.out.println("This happened");
        KeyCode k = e.getCode();
        pieceKey = k.getName().toLowerCase();
        if (pieceKey.equals("u")) {
            //TODO: undo a lastPiece creation? Need to know which lastPiece was made to remove last index from the right list
        }
    }
}
