package exercise1;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpeningFrame extends JFrame {
    private final JButton startGame;
    private final JButton quitGame;
    private final JLabel title;

    public OpeningFrame(){
        super("Welcome to Pong!");
        setLayout(new GridLayout(2,2));

        //title
        title = new JLabel("PONG!");
        title.setHorizontalTextPosition(SwingConstants.CENTER);
        add(title);
        add(new JLabel());

        //start game button
        startGame = new JButton("Start Game");
        add(startGame);

        //quit game button
        quitGame = new JButton("Quit Game");
        add(quitGame);

        //adding handlers to buttons
        ButtonHandler handler = new ButtonHandler();
        startGame.addActionListener(handler);
        quitGame.addActionListener(handler);
    }

    private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            //close current window
            OpeningFrame.this.setVisible(false);
            OpeningFrame.this.dispose();

            //open pong window if action command is from start game button
            if (e.getActionCommand() == "Start Game"){
                PongFrame pongFrame = new PongFrame();
                pongFrame.setDefaultCloseOperation(pongFrame.EXIT_ON_CLOSE);
                pongFrame.setVisible(true);
            }
        }
    }
}
