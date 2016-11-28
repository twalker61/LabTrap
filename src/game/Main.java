package game;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

/**
 * Created by twalker61
 */
public class Main extends Application {

    private Stage stage;
    private GamePane play;
    private StackPane welcome;
    private StackPane instructions;
    private GamePane builder;
    private StackPane results;
    private List<PortalButton> buttons;
    private List<Floor> floorTiles;
    private List<Wall> walls;
    private ExitPortal exit;
    private boolean hover;

    @Override
    public void start(Stage primaryStage) throws Exception{

        play = new GamePane(this, false);
        welcome = new StackPane();
        welcome.getChildren().add(new ImageView(new Image(getClass().getResource("../images/introScreen.png").toExternalForm())));
        instructions = new StackPane();
        instructions.getChildren().add(new ImageView(new Image(getClass().getResource("../images/introScreen.png").toExternalForm())));
        builder = new GamePane(this, true);
        results = new StackPane();
        results.getChildren().addAll(new ImageView(new Image(getClass().getResource("../images/loserScreen.png").toExternalForm())),
                new ImageView(new Image(getClass().getResource("../images/loserScreenHover.png").toExternalForm())),
                new ImageView(new Image(getClass().getResource("../images/winnerScreen.png").toExternalForm())),
                new ImageView(new Image(getClass().getResource("../images/winnderScreenHover.png").toExternalForm())));
        //results.getChildren().get(0).setVisible(false);
        //Do that to the three images that don't apply to the game state after level completion

        welcome.setOnMouseClicked(e -> {
            if (hover) {
                stage.setScene(new Scene (builder, 1067, 600));
                //stage.setScene(new Scene(instructions, 1067, 600));
            }
        });
        welcome.setOnMouseMoved(e -> {
            if (e.getY() > 309 && e.getY() < 387 && e.getX() > 342 && e.getX() < 734) {
                welcome.getChildren().remove(0);
                welcome.getChildren().add(new ImageView(new Image(getClass().getResource("../images/introScreenHover.png").toExternalForm())));
                hover = true;
            } else {
                welcome.getChildren().remove(0);
                welcome.getChildren().add(new ImageView(new Image(getClass().getResource("../images/introScreen.png").toExternalForm())));
                hover = false;
            }
        });

        instructions.setOnMouseClicked(e -> {
            if (hover) {
                stage.setScene(new Scene (builder, 1067, 600));
            }
        });

        stage = primaryStage;
        primaryStage.setTitle("Lab Trap!");
        primaryStage.setScene(new Scene(welcome, 1067, 600));
        primaryStage.show();

        play.getCanvas().requestFocus();

    }

    /*public Parent welcomeScene() {
        VBox vbox = new VBox(30);
        Button startButton = new Button();
        startButton.setText("START");
        Button instructionButton = new Button();
        instructionButton.setText("INSTRUCTION");
        vbox.getChildren().addAll(startButton, instructionButton);
        vbox.setAlignment(Pos.CENTER);
        startButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                stage.setScene(new Scene (play, 1067, 600));
            }
        });
        return vbox;
    }*/

    public void setWallList(List<Wall> w) {
        walls = w;
    }
    public void setFloorList(List<Floor> f) {
        floorTiles = f;
    }
    public void setButtonList(List<PortalButton> b) {
        buttons = b;
    }
    public void setExitPortal(ExitPortal p) {
        exit = p;
    }

    public List<Wall> getWallList() {
        return walls;
    }
    public List<Floor> getFloorList() {
        return floorTiles;
    }
    public List<PortalButton> getButtonList() {
        return buttons;
    }
    public ExitPortal getExitPortal() {
        return exit;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
