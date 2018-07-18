package exercise2;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PongFrame extends Application {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;
    private static final int PADDLE_HEIGHT = 48;
    private static final int PADDLE_WIDTH = 10;
    private static final int SET_MATCH = 11;
    private static final int MARGIN_OF_ERROR = 4; //play-testing yielded this value, which affects collisions
    private static final int PLAYER_MOVESPEED = 6;

    private Scene openingScene;
    private Scene pongScene;
    private Button btStart;
    private Button btCancel;

    private void setOpeningScene(){
        GridPane openingPane = new GridPane();
        openingPane.setAlignment(Pos.CENTER);
        openingPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        openingPane.setHgap(5.5);
        openingPane.setVgap(5.5);

        openingPane.add(new Label("P O N G !"), 0,0);
        btStart = new Button("Start Game");
        openingPane.add(btStart, 0, 1);
        btCancel = new Button("Exit");
        openingPane.add(btCancel, 1, 1);
        openingScene = new Scene(openingPane);

    }

    private void setPongScene(){
        Pane pongPane = new Pane();
        pongPane.setMinSize(WIDTH, HEIGHT);

        //pongPane.resize(WIDTH, HEIGHT);
        pongPane.setStyle("-fx-background-color: black");


        Image playerImage = new Image(getClass().getResourceAsStream("sprites/Player.png"));
        Image midLine = new Image(getClass().getResourceAsStream("sprites/MidLine.png"));
        Image ballImage = new Image(getClass().getResourceAsStream("sprites/Ball.png"));

        Label player1 = new Label(null, new ImageView(playerImage));
        Label player2 = new Label(null, new ImageView(playerImage));
        Label divider = new Label(null, new ImageView(midLine));
        Label ball = new Label(null, new ImageView(ballImage));
        Label score1 = new Label("0");
        Label score2 = new Label("0");
        Label instructions = new Label("blah");
        score1.setFont(new Font("Serif", 54));
        score1.setTextFill(Color.WHITE);
        score2.setFont(new Font("Serif", 54));
        score2.setTextFill(Color.WHITE);
        instructions.setStyle("-fx-font-family: Serif; -fx-font-color: white; fx-font-size: 14; -fx-text-alignment: center");
        score1.setPrefSize(100,100);
        score2.setPrefSize(100,100);
        instructions.setMinSize(624,14);
        divider.setMinSize(1, 441);

        score1.setLayoutX(100);
        score1.setLayoutY(20);
        score2.setLayoutX(242);
        score2.setLayoutY(20);
        divider.setLayoutX(312);
        divider.setLayoutY(0);

        //score1.relocate(100, 20);
        //score2.relocate(242, 20);
        //instructions.relocate(0,5);
        //divider.relocate(1,441);
        pongPane.getChildren().add(score1);
        pongPane.getChildren().add(score2);
        pongPane.getChildren().add(divider);


        //pongPane.getChildren().addAll(player1, player2, score1, score2, instructions, divider);

        pongScene = new Scene(pongPane);



    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        setOpeningScene();
        setPongScene();

        primaryStage.setTitle("Pong!");
        primaryStage.setScene(pongScene);
        //note: primaryStage has setResizable
        primaryStage.show();


        //btStart.setOnAction(e->showPongPane());

    }
}
