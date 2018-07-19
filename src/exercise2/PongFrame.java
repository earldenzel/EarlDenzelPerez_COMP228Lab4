package exercise2;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

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
    private Label player1;
    private Label player2;
    private Label ball;
    private Label score1;
    private Label score2;
    private Label instructions;
    private Label divider;
    private Timeline timer;
    private int server;
    private int velocityX;
    private int velocityY;
    private int player1Score;
    private int player2Score;
    private boolean shotsFired;
    private Map<KeyCode, Boolean> keys = new HashMap<>();

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

        keys.put(KeyCode.SPACE, false);
        keys.put(KeyCode.W, false);
        keys.put(KeyCode.S, false);
        keys.put(KeyCode.UP, false);
        keys.put(KeyCode.DOWN, false);

    }

    private void setPongScene(){
        Pane pongPane = new Pane();
        pongPane.setPadding(new Insets(0,0,0,0));
        pongPane.setMinSize(WIDTH, HEIGHT);
        pongPane.setStyle("-fx-background-color: black");

        initializePongObjects();
        pongPane.getChildren().addAll(player1, player2, score1, score2, instructions, divider, ball);

        pongScene = new Scene(pongPane);
        pongScene.setOnKeyPressed(e -> keys.put(e.getCode(), true));
        pongScene.setOnKeyReleased(e -> keys.put(e.getCode(), false));

        //game loop
        startGame();

        timer = new Timeline(new KeyFrame(
                Duration.millis(60),
                ae -> gameLoop()));

        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }

    //checks keyboard input
    private void keyCheck(){
        double x1Pos = player1.getLayoutX();
        double y1Pos = player1.getLayoutY();
        double x2Pos = player2.getLayoutX();
        double y2Pos = player2.getLayoutY();
        double xBall = ball.getLayoutX();
        double yBall = ball.getLayoutY();

        //space denotes start of round
        if(keys.get(KeyCode.SPACE) && !shotsFired){
            shotsFired = true;
            if (server == 1){
                velocityX = 8;
            }
            else if (server == 2){
                velocityX = -8;
            }
            instructions.setText("");
        }

        //W is Up for player 1
        if(keys.get(KeyCode.W)){
            if (y1Pos >= 0) {
                y1Pos -= PLAYER_MOVESPEED;
                if (!shotsFired && server == 1){
                    yBall -= PLAYER_MOVESPEED;
                }
            }
        }
        //S is down for player 1
        if(keys.get(KeyCode.S)){
            if (y1Pos <= HEIGHT - PADDLE_HEIGHT) {
                y1Pos += PLAYER_MOVESPEED;
                if (!shotsFired && server == 1){
                    yBall +=PLAYER_MOVESPEED;
                }
            }
        }
        //up arrow key is up for player 2
        if(keys.get(KeyCode.UP)){
            if (y2Pos >= 0) {
                y2Pos -= PLAYER_MOVESPEED;
                if (!shotsFired && server == 2){
                    yBall -= PLAYER_MOVESPEED;
                }
            }
        }
        //down arrow key is down for player 2
        if (keys.get(KeyCode.DOWN)){
            if (y2Pos <= HEIGHT - PADDLE_HEIGHT) {
                y2Pos += PLAYER_MOVESPEED;
                if (!shotsFired && server == 2){
                    yBall += PLAYER_MOVESPEED;
                }
            }
        }

        //update player position and, when ball is not in motion, also updates ball position
        player1.relocate(x1Pos, y1Pos);
        player2.relocate(x2Pos, y2Pos);
        ball.relocate(xBall, yBall);
    }

    //show end condition and if met, show congratulation message
    private boolean endCheck(){
        if (player1Score == SET_MATCH){
            instructions.setText("Player 1 wins!");
            return true;
        }
        if (player2Score == SET_MATCH){
            instructions.setText("Player 2 wins");
            return true;
        }
        return false;
    }

    private void gameLoop(){
        collisionCheck();
        keyCheck();
        if (endCheck()){
            timer.stop();
        }

        double ballX = ball.getLayoutX();
        double ballY = ball.getLayoutY();

        if (ballX <= 0 || ballX >= WIDTH-10){
            velocityX = -velocityX;
        }
        if (ballY <= 0 || ballY >= HEIGHT-10){
            velocityY = -velocityY;
        }

        ball.relocate(ballX + velocityX, ballY+ velocityY);
    }

    //checks contact with paddle or the bounds and promptly updates score
    //the speed of the ball increases every time it hits paddles
    private void collisionCheck(){
        double hitPoint1 = ball.getLayoutY()- player1.getLayoutY();
        double hitPoint2 = ball.getLayoutY()- player2.getLayoutY();

        //player1 paddle check
        if (Math.abs(player1.getLayoutX()-ball.getLayoutX()) <=PADDLE_WIDTH){
            if (hitPoint1 <= PADDLE_HEIGHT  && hitPoint1 >= -MARGIN_OF_ERROR) {
                //velocity is negative before paddle is hit
                //this, we will add some velocity after reversing
                velocityX = -velocityX;
                velocityX += 1;
                setYVelocity(hitPoint1);
            }
        }

        //player2 paddle check
        if (Math.abs(player2.getLayoutX()-ball.getLayoutX()) <= PADDLE_WIDTH){
            if (hitPoint2 <= PADDLE_HEIGHT && hitPoint2 >= -MARGIN_OF_ERROR) {
                //velocity is positive before paddle is hit
                //thus, we will add some velocity before reversing
                velocityX += 1;
                velocityX = -velocityX;
                setYVelocity(hitPoint2);
            }
        }

        //player1 score check
        if (ball.getLayoutX() <= 0){
            player2Score++;
            server = 2;
            resetGame(server);
        }
        if (ball.getLayoutX() >= 624){
            player1Score++;
            server = 1;
            resetGame(server);
        }
    }

    //initialize all objects in the pong game
    private void initializePongObjects(){
        ImageView playerImage1 = new ImageView(new Image(getClass().getResourceAsStream("sprites/Player1.png")));
        ImageView playerImage2 = new ImageView(new Image(getClass().getResourceAsStream("sprites/Player2.png")));
        ImageView midLine = new ImageView(new Image(getClass().getResourceAsStream("sprites/MidLine.png")));
        ImageView ballImage = new ImageView (new Image(getClass().getResourceAsStream("sprites/Ball.png")));
        playerImage1.setFitHeight(PADDLE_HEIGHT);
        playerImage1.setFitWidth(PADDLE_WIDTH);
        playerImage2.setFitHeight(PADDLE_HEIGHT);
        playerImage2.setFitWidth(PADDLE_WIDTH);
        midLine.setFitHeight(441);
        midLine.setFitWidth(1);
        ballImage.setFitHeight(10);
        ballImage.setFitWidth(10);

        player1 = new Label(null, playerImage1);
        player2 = new Label(null, playerImage2);
        divider = new Label(null, midLine);
        ball = new Label(null, ballImage);
        score1 = new Label("0");
        score2 = new Label("0");
        instructions = new Label("blah");
        score1.setFont(new Font("Serif", 54));
        score1.setTextFill(Color.WHITE);
        score2.setFont(new Font("Serif", 54));
        score2.setTextFill(Color.WHITE);
        score1.setAlignment(Pos.CENTER_RIGHT);
        score2.setAlignment(Pos.CENTER_LEFT);
        instructions.setAlignment(Pos.CENTER);
        instructions.setFont(new Font("Serif", 14));
        instructions.setTextFill(Color.WHITE);
        player1.setMinWidth(50);

        score1.setMinSize(100,100);
        score2.setMinSize(100,100);
        instructions.setMinSize(624, 14);

        score1.relocate(100, 20);
        score2.relocate(424, 20);
        instructions.relocate(0,5);
        divider.relocate(312,0);
    }

    //set start conditions
    private void startGame(){
        player1Score = 0;
        player2Score = 0;
        shotsFired = false;
        server = 1;
        resetGame(server);
    }

    //after a player has scored, this
    //resets ball and paddle positions
    //and updates scores
    public void resetGame(int player){
        player1.relocate(30,195);
        player2.relocate(584, 195);

        score1.setText(String.format("%d", player1Score));
        score2.setText(String.format("%d", player2Score));

        instructions.setText("Player 1 controls: W/S     Press SPACE to start round     Player 2 controls: UP/DOWN");

        if (player == 1) {
            ball.relocate(50, 215);
            shotsFired = false;
            velocityX = 0;
            velocityY = 0;
        }
        else if (player == 2){
            ball.relocate(564, 215);
            shotsFired = false;
            velocityX = 0;
            velocityY = 0;
        }
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
        //primaryStage.setResizable(false);
        //note: primaryStage has setResizable
        primaryStage.show();

    }

    private void setYVelocity(double hitpoint){
        hitpoint += MARGIN_OF_ERROR;
        //theoretical value of hitpoint is now 0 to 52
        //assuming margin of error as 4 and player movespeed as 6, actual values are
        //0, 6, 12, 18, 24, 30, 36, 42, 48
        //this function assumes that set values are unchanged
        int rawVelocityX = Math.abs(velocityX);
        double multiplier = (hitpoint/24)-1;
        velocityY = (int)(multiplier*rawVelocityX);
    }
}
