package exercise1;

import javax.swing.JFrame;
import java.awt.*;

public class PongFrame extends JFrame {
    public PongFrame(){
        super("Pong!");
        setLayout(new FlowLayout());
        getContentPane().setBackground(Color.BLACK);
    }
}
