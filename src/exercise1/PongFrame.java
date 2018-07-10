package exercise1;


import javafx.scene.input.KeyCode;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class PongFrame extends JFrame {
    private JLabel player1;
    private JLabel player2;
    private JLabel divider;
    private JLabel ball;

    public PongFrame(){
        super("Pong!");
        setLayout(null);
        setResizable(false);

        //drawing pong board
        getContentPane().setBackground(Color.BLACK);
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
        while(true){

            repaint();
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
        player1.setSize(10,50);

        player2.setLocation(580, 215);
        player2.setSize(10,50);

        ball.setLocation(40, 235);
        ball.setSize(10,10);

        divider.setLocation(320,0);
        divider.setSize(1,480);

    }
}
