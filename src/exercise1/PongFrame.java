package exercise1;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.Color;
import java.awt.event.ActionEvent;
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
        player1.setLocation(30,215);
        player1.setSize(10,50);

        player2.setLocation(580, 215);
        player2.setSize(10,50);

        ball.setLocation(40, 235);
        ball.setSize(10,10);

        divider.setLocation(320,0);
        divider.setSize(1,480);


        add(player1);
        add(player2);
        add(divider);
        add(ball);


        //adding handlers
        KeyHandler keyHandler = new KeyHandler();
        player1.addKeyListener(keyHandler);

        Action upP1 = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player1.getY() > 0 && player1.getY()<480){
                    player1.setLocation(player1.getX(), player1.getY()+1);
                }
            }
        };

        player1.getInputMap().put(KeyStroke.getKeyStroke("W"), upP1);
    }

    public class KeyHandler implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

            //double currentP2position = player2.getLocation().getY();
            //switch(e.getKeyCode()){
            //    case KeyEvent.VK_W:
            //        if (currentP1position > 0 || currentP1position < 480){
            //            player1.setLocation((int)player1.getLocation().getX(),currentP1position + 1);
            //            //player1.setVerticalTextPosition(currentP1position + 1);
            //        }
            //        break;
            //    case KeyEvent.VK_S:
            //        if (currentP1position > 0 || currentP1position < 480){
            //            player1.setVerticalTextPosition(currentP1position - 1);
            //        }
            //        break;
            //    default:
            //        break;
            //}

        }

        @Override
        public void keyReleased(KeyEvent e) {
            JOptionPane.showMessageDialog(null, e.getKeyCode());

        }
    }
}
