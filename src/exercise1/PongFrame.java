package exercise1;

import javax.swing.JFrame;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Insets;

public class PongFrame extends JFrame {
    public PongFrame(){
        super("Pong!");
        setLayout(null);
        Insets insets = getInsets();

        //drawing pong board
        getContentPane().setBackground(Color.BLACK);

        Icon playerIcon = new ImageIcon(getClass().getResource("sprites/Player.png"));
        Icon midLine = new ImageIcon(getClass().getResource("sprites/MidLine.png"));
        Icon ball = new ImageIcon(getClass().getResource("sprites/Ball.png"));

        JLabel player1 = new JLabel(playerIcon);
        JLabel player2 = new JLabel(playerIcon);
        JLabel divider = new JLabel(midLine);
        player1.setBounds(30 + insets.left, 215+insets.top, 10, 50);
        player2.setBounds(580 + insets.left, 215+insets.top, 10, 50);
        divider.setBounds(320+insets.left, insets.top, 1, 480);

        add(player1);
        add(player2);
        add(divider);
    }
}
