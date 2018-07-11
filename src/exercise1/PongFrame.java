package exercise1;

import javax.swing.JFrame;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PongFrame extends JFrame {
    private JLabel player1;
    private JLabel player2;
    private JLabel divider;
    private JLabel ball;
    private int velocityX;
    private int velocityY;
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;
    private static final int PADDLE_HEIGHT = 50;
    private static final int PADDLE_WIDTH = 10;
    private int player1Score;
    private int player2Score;

    public PongFrame(){
        super("Pong!");
        setLayout(null);
        setResizable(false);
        setSize(new Dimension(WIDTH, HEIGHT));

        Container container = getContentPane();
        container.setBackground(Color.BLACK);

        //drawing pong board
        Icon playerIcon = new ImageIcon(getClass().getResource("sprites/Player.png"));
        Icon midLine = new ImageIcon(getClass().getResource("sprites/MidLine.png"));
        Icon ballIcon = new ImageIcon(getClass().getResource("sprites/Ball.png"));

        player1 = new JLabel(playerIcon);
        player2 = new JLabel(playerIcon);
        divider = new JLabel(midLine);
        ball = new JLabel(ballIcon);


        add(player1);
        add(player2);
        add(divider);
        add(ball);

        startGame();

        player1.getInputMap().put(KeyStroke.getKeyStroke("W"),
                "player1goUp");
        player1.getActionMap().put("player1goUp",
                new PlayerMotion(true, "p1"));

        player1.getInputMap().put(KeyStroke.getKeyStroke("S"),
                "player1goDown");
        player1.getActionMap().put("player1goDown",
                new PlayerMotion(false, "p1"));

        player2.getInputMap().put(KeyStroke.getKeyStroke("I"),
                "player2goUp");
        player2.getActionMap().put("player2goUp",
                new PlayerMotion(true, "p2"));

        player2.getInputMap().put(KeyStroke.getKeyStroke("K"),
                "player2goDown");
        player2.getActionMap().put("player2goDown",
                new PlayerMotion(false, "p2"));

        gameLoop(container);
    }

    private void startGame(){
        player1Score = 0;
        player2Score = 0;
        resetGame(player1);
    }

    private void gameLoop(Container container){

        Timer timer = new Timer(60, new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                int ballX = ball.getX();
                int ballY = ball.getY();

                if (ballX <= 0 || ballX >= container.getWidth()){
                    velocityX = -velocityX;
                }
                if (ballY <= 0 || ballY >= container.getHeight()){
                    velocityY = -velocityY;
                }

                ball.setLocation(ballX + velocityX,ballY+ velocityY);
            }
        });
        timer.start();

    }

    private void update(){
        int hitPoint1 = ball.getY()- player1.getY();
        int hitPoint2 = ball.getY()- player2.getY();

        //player1 paddle check
        if (Math.abs(player1.getX()-ball.getX()) <=4){
            if (hitPoint1 <= PADDLE_HEIGHT && hitPoint1 >=-2) {
                velocityX = -velocityX;
                velocityX += 2;

                velocityY += (hitPoint1+2)/13 - 2;

            }
        }
        //player2 paddle check
        if (Math.abs(player2.getX()-ball.getX()) <= 4){
            if (hitPoint2 <= PADDLE_HEIGHT && hitPoint2 >=-2) {
                velocityX = -velocityX;
                velocityX -= 2;

                velocityY += (hitPoint2+2)/13 - 2;

            }
        }
        //player1 score check
        if (ball.getX() <= 0){
            player2Score++;
            resetGame(player1);
        }
        if (ball.getX() >= 624){
            player1Score++;
            resetGame(player2);
        }
    }

    private class PlayerMotion extends AbstractAction implements ActionListener
    {
        private boolean upwards;
        private String player;

        public PlayerMotion(boolean upwards, String player)
        {
            super();
            this.upwards = upwards;
            this.player = player;
        }

        public void actionPerformed(ActionEvent e)
        {
            if(player == "p1") {
                int x1Pos = player1.getX();
                int y1Pos = player1.getY();
                if (upwards) {

                    if (y1Pos >= 0) {
                        y1Pos -= 8;
                    }
                } else {

                    if (y1Pos <= 390) {
                        y1Pos += 8;
                    }
                }

                player1.setLocation(x1Pos, y1Pos);
            }
            else{
                int x2Pos = player2.getX();
                int y2Pos = player2.getY();
                if(upwards) {
                    if (y2Pos >= 0) {
                        y2Pos -= 8;
                    }
                }
                else{

                    if (y2Pos <= 390){
                        y2Pos+=8;
                    }
                    player2.setLocation(x2Pos, y2Pos);
                }
                player2.setLocation(x2Pos, y2Pos);
            }
        }
    }

    public void resetGame(JLabel player){
        player1.setLocation(30,195);
        player1.setSize(PADDLE_WIDTH,PADDLE_HEIGHT);

        player2.setLocation(584, 195);
        player2.setSize(PADDLE_WIDTH,PADDLE_HEIGHT);

        divider.setLocation(312,0);
        divider.setSize(1,441);

        if (player == player1) {
            ball.setLocation(40, 215);
            ball.setSize(10, 10);
            velocityX = 8;
            velocityY = 8;
        }
        if (player == player2){
            ball.setLocation(574, 215);
            ball.setSize(10, 10);
            velocityX = -8;
            velocityY = -8;
        }
    }
}
