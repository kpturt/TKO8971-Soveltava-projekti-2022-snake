import javax.swing.*;
import java.awt.*;

//the main frame
public class SnakeFrame extends JFrame {
    public SnakeFrame(){
        //default frame operations
        setTitle("Snake");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //changed toolbar image to a snake
        Image icon = Toolkit.getDefaultToolkit().getImage("src/images/snake.png");
        setIconImage(icon);
        //creates a new game panel and starts the game
        SnakePanel snake = new SnakePanel();
        snake.setLayout( new GridLayout(1, 1, 0, 0) ); //sets layout type
        snake.setPreferredSize(new Dimension(snake.WIDTH, snake.HEIGHT)); //sets the size according to panel's variables
        add(snake); //adds the game panel to main frame
        pack(); //sizes the frame so all contents are at or above preferred size
        setLocationRelativeTo(null); //center the gui
        setVisible(true); //makes the frame visible
        snake.Screen(); //starts the game
    }
}
