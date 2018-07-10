package exercise1;


import javafx.scene.input.KeyCode;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.Timer;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class PongFrame extends JFrame {
    private JLabel player1;
    private JLabel player2;
    private JLabel divider;
    private JLabel ball;
    private int velocityX;
    private int velocityY;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static final int PADDLE_HEIGHT = 50;

    public PongFrame(){
        super("Pong!");
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);

        JPanel pongPanel = new JPanel();
        pongPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

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

        resetGame();

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

        gameLoop();
    }

    private void gameLoop(){
        Timer timer = new Timer(60, new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                checkCollision();
                int ballX = ball.getX();
                int ballY = ball.getY();

                if (ballX <= 0 || ballX >= WIDTH){
                    velocityX = -velocityX;
                }
                if (ballY <= 0 || ballY >= HEIGHT){
                    velocityY = -velocityY;
                }

                ball.setLocation(ballX + velocityX,ballY+ velocityY);
            }
        });
        timer.start();

    }

    private void checkCollision(){
        int hitPoint = ball.getY()- player1.getY();

        if (player1.getX()-ball.getX() == 0){
            if (hitPoint <= PADDLE_HEIGHT && hitPoint >=0) {
                velocityX = -velocityX;

                velocityY += (6 * hitPoint / PADDLE_HEIGHT - 3);
                //velocityX++;
            }

        }

        System.out.printf("Current velocity: %d, %d %n", velocityX, velocityY);

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
                        y1Pos -= 4;
                    }
                } else {

                    if (y1Pos <= 390) {
                        y1Pos += 4;
                    }
                }

                player1.setLocation(x1Pos, y1Pos);
            }
            else{
                int x2Pos = player2.getX();
                int y2Pos = player2.getY();
                if(upwards) {
                    if (y2Pos >= 0) {
                        y2Pos -= 4;
                    }
                }
                else{

                    if (y2Pos <= 390){
                        y2Pos+=4;
                    }
                    player2.setLocation(x2Pos, y2Pos);
                }
                player2.setLocation(x2Pos, y2Pos);
            }
        }
    }

    public void resetGame(){
        player1.setLocation(30,215);
        player1.setSize(10,PADDLE_HEIGHT);

        player2.setLocation(580, 215);
        player2.setSize(10,PADDLE_HEIGHT);

        ball.setLocation(40, 235);
        ball.setSize(10,10);

        divider.setLocation(320,0);
        divider.setSize(1,480);

        velocityX = 10;
        velocityY = 10;

    }
}
