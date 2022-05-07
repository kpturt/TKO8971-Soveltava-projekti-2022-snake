import javax.swing.*;
import java.awt.*;

public class SnakeFrame extends JFrame {
    public SnakeFrame(){
        setTitle("Snake");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //TODO: Implement start menu and game over screens

        /* start = new StartPanel();
        start.setPreferredSize(new Dimension(800, 800));
        start.setForeground(Color.black);
        add(start);*/

        SnakePanel snake = new SnakePanel();
        snake.setLayout( new GridLayout(1, 1, 0, 0) );
        snake.setPreferredSize(new Dimension(800, 800));
        add(snake);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        snake.Screen();
    }
}
