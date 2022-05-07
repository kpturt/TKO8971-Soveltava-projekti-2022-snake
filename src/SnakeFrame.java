import javax.swing.*;
import java.awt.*;

public class SnakeFrame extends JFrame {
    public SnakeFrame(){
        setTitle("Snake");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Image icon = Toolkit.getDefaultToolkit().getImage("src/images/snake.png");
        setIconImage(icon);

        SnakePanel snake = new SnakePanel();
        snake.setLayout( new GridLayout(1, 1, 0, 0) );
        snake.setPreferredSize(new Dimension(snake.WIDTH, snake.HEIGHT));
        add(snake);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        snake.Screen();
    }
}
