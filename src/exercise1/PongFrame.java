package exercise1;

import javax.swing.JFrame;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PongFrame extends JFrame implements KeyListener{
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;
    private static final int PADDLE_HEIGHT = 48;
    private static final int PADDLE_WIDTH = 10;
    private static final int SET_MATCH = 11;
    private static final int MARGIN_OF_ERROR = 4; //play-testing yielded this value, which affects collisions
    private static final int PLAYER_MOVESPEED = 6;
    private JLabel player1;
    private JLabel player2;
    private JLabel divider;
    private JLabel ball;
    private JLabel score1;
    private JLabel score2;
    private JLabel instructions;
    private int server;
    private int velocityX;
    private int velocityY;
    private int player1Score;
    private int player2Score;
    private boolean shotsFired;
    private boolean[] keys = new boolean[KeyEvent.KEY_TYPED];
    private Timer timer;

    public PongFrame(){
        //pong settings - title, background color, and images for player, ball and divider
        super("Pong!");
        setLayout(null);
        setResizable(false);
        setSize(new Dimension(WIDTH, HEIGHT));
        Container container = getContentPane();
        container.setBackground(Color.BLACK);
        Icon playerIcon = new ImageIcon(getClass().getResource("sprites/Player.png"));
        Icon midLine = new ImageIcon(getClass().getResource("sprites/MidLine.png"));
        Icon ballIcon = new ImageIcon(getClass().getResource("sprites/Ball.png"));
        player1 = new JLabel(playerIcon);
        player2 = new JLabel(playerIcon);
        divider = new JLabel(midLine);
        ball = new JLabel(ballIcon);

        //ui settings - score and instructions
        score1 = new JLabel();
        score2 = new JLabel();
        instructions = new JLabel();
        score1.setFont(new Font("Serif", Font.BOLD, 54));
        score2.setFont(new Font("Serif", Font.BOLD, 54));
        instructions.setFont(new Font("Serif", Font.PLAIN, 14));
        score1.setForeground(Color.WHITE);
        score2.setForeground(Color.WHITE);
        instructions.setForeground(Color.WHITE);
        score1.setSize(100,100);
        score2.setSize(100,100);
        instructions.setSize(624,14);
        score1.setLocation(100,20);
        score2.setLocation(424,20);
        instructions.setLocation(0,5);
        score1.setHorizontalAlignment(JLabel.RIGHT);
        instructions.setHorizontalAlignment(JLabel.CENTER);
        divider.setLocation(312,0);
        divider.setSize(1,441);

        //add all components and listeners to frame
        add(player1);
        add(player2);
        add(divider);
        add(ball);
        add(score1);
        add(score2);
        add(instructions);
        addKeyListener(this);

        //game loop
        startGame();
        gameLoop(container);
    }

    //set start conditions
    private void startGame(){
        player1Score = 0;
        player2Score = 0;
        shotsFired = false;
        server = 1;
        resetGame(server);
    }

    //primarily updates the location of the ball at all times
    private void gameLoop(Container container){
        timer = new Timer(60, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                collisionCheck();
                keyCheck();
                if (endCheck()){
                    timer.stop();
                }

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

    //checks contact with paddle or the bounds and promptly updates score
    //the speed of the ball increases every time it hits paddles
    private void collisionCheck(){
        int hitPoint1 = ball.getY()- player1.getY();
        int hitPoint2 = ball.getY()- player2.getY();

        //player1 paddle check
        if (Math.abs(player1.getX()-ball.getX()) <=PADDLE_WIDTH){
            if (hitPoint1 <= PADDLE_HEIGHT  && hitPoint1 >= -MARGIN_OF_ERROR) {
                //velocity is negative before paddle is hit
                //this, we will add some velocity after reversing
                velocityX = -velocityX;
                velocityX += 1;
                setYVelocity(hitPoint1);
                //velocityY += (hitPoint1+2)/13 - 2;
            }
        }

        //player2 paddle check
        if (Math.abs(player2.getX()-ball.getX()) <= PADDLE_WIDTH){
            if (hitPoint2 <= PADDLE_HEIGHT && hitPoint2 >= -MARGIN_OF_ERROR) {
                //velocity is positive before paddle is hit
                //thus, we will add some velocity before reversing
                velocityX += 1;
                velocityX = -velocityX;
                setYVelocity(hitPoint2);

                //velocityY += (hitPoint2+2)/13 - 2;

            }
        }

        //player1 score check
        if (ball.getX() <= 0){
            player2Score++;
            server = 2;
            resetGame(server);
        }
        if (ball.getX() >= 624){
            player1Score++;
            server = 1;
            resetGame(server);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    //checks keyboard input
    private void keyCheck(){
        int x1Pos = player1.getX();
        int y1Pos = player1.getY();
        int x2Pos = player2.getX();
        int y2Pos = player2.getY();
        int xBall = ball.getX();
        int yBall = ball.getY();

        //space denotes start of round
        if(keys[KeyEvent.VK_SPACE] && !shotsFired){
            shotsFired = true;
            if (server == 1){
                velocityX = 8;
                //velocityY = 8;
            }
            else if (server == 2){
                velocityX = -8;
                //velocityY = -8;
            }
            instructions.setText("");
        }

        //W is Up for player 1
        if(keys[KeyEvent.VK_W]){
            if (y1Pos >= 0) {
                y1Pos -= PLAYER_MOVESPEED;
                if (!shotsFired && server == 1){
                    yBall -= PLAYER_MOVESPEED;
                }
            }
        }
        //S is down for player 1
        if(keys[KeyEvent.VK_S]){

            if (y1Pos <= 390) {
                y1Pos += PLAYER_MOVESPEED;
                if (!shotsFired && server == 1){
                    yBall +=PLAYER_MOVESPEED;
                }
            }
        }
        //up arrow key is up for player 2
        if(keys[KeyEvent.VK_UP]){
            if (y2Pos >= 0) {
                y2Pos -= PLAYER_MOVESPEED;
                if (!shotsFired && server == 2){
                    yBall -= PLAYER_MOVESPEED;
                }
            }
        }
        //down arrow key is down for player 2
        if (keys[KeyEvent.VK_DOWN]){
            if (y2Pos <= 390) {
                y2Pos += PLAYER_MOVESPEED;
                if (!shotsFired && server == 2){
                    yBall += PLAYER_MOVESPEED;
                }
            }
        }

        //update player position and, when ball is not in motion, also updates ball position
        player1.setLocation(x1Pos, y1Pos);
        player2.setLocation(x2Pos, y2Pos);
        ball.setLocation(xBall, yBall);
    }

    //after a player has scored, this
    //resets ball and paddle positions
    //and updates scores
    public void resetGame(int player){
        player1.setLocation(30,195);
        player1.setSize(PADDLE_WIDTH,PADDLE_HEIGHT);

        player2.setLocation(584, 195);
        player2.setSize(PADDLE_WIDTH,PADDLE_HEIGHT);

        score1.setText(String.format("%d", player1Score));
        score2.setText(String.format("%d", player2Score));

        instructions.setText("Player 1 controls: W/S     Press SPACE to start round     Player 2 controls: UP/DOWN");

        if (player == 1) {
            ball.setLocation(50, 215);
            ball.setSize(10, 10);
            shotsFired = false;
            velocityX = 0;
            velocityY = 0;
        }
        else if (player == 2){
            ball.setLocation(564, 215);
            ball.setSize(10, 10);
            shotsFired = false;
            velocityX = 0;
            velocityY = 0;
        }
    }

    private void setYVelocity(int hitpoint){
        hitpoint += MARGIN_OF_ERROR;
        //theoretical value of hitpoint is now 0 to 52
        //assuming margin of error as 4 and player movespeed as 6, actual values are
        //0, 6, 12, 18, 24, 30, 36, 42, 48
        //this function assumes that set values are unchanged
        int rawVelocityX = Math.abs(velocityX);
        double multiplier = ((double)hitpoint/24)-1;
        velocityY = (int)(multiplier*rawVelocityX);
    }
}
