import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;
import java.util.ArrayList;
import java.util.Random;

public class SnakePanel extends JPanel implements Runnable {
    private static final long serialVersionUID = 1L; //from screen, ???
    public static final int WIDTH = 800, HEIGHT = 800;
    private Thread thread; //???
    public boolean running = false;
    private BodyPart b;
    public ArrayList<BodyPart> snake;
    private int xCrd = 10, yCrd = 10; //location of snake's head
    private int size = 5; //snake's size
    private boolean right = false, left = false, up = false, down = true;
    private int ticks = 0;
    private Key key;
    Random rnd = new Random();
    Random rnd2 = new Random();
    private Apple apple;
    private ArrayList<Apple> apples;

    //game board's screen
    public void Screen() {
        setFocusable(true);
        key = new Key(); // new key object?
        addKeyListener(key);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        snake = new ArrayList<BodyPart>();
        apples = new ArrayList<Apple>();
        start();
    }

    //starts the game
    public void start() {
        int rndLuku = rnd.nextInt(4); //randomizes the snake's starting direction
        //System.out.println(rndLuku);
        if(rndLuku == 0) {
            up = false;
            down = false;
            right = true;
            left = false;
        }
        if(rndLuku == 1) {
            up = false;
            down = true;
            right = false;
            left = false;
        }
        if(rndLuku == 2) {
            up = false;
            down = false;
            right = false;
            left = true;
        }
        if(rndLuku == 3) {
            up = true;
            down = false;
            right = false;
            left = false;
        }
        running = true;
        thread = new Thread((Runnable) this, "Game loop"); //???
        thread.start();
    }

    //stops the game
    public void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // draw the game board
    public void paint(Graphics g) {
        if(running == true){
            g.clearRect(0, 0, WIDTH, HEIGHT); //removes tail, moves snake
            g.setColor(Color.black);
            g.fillRect(0, 0, 800, 800);
            g.setColor(Color.GRAY);
            for(int i = 0; i < WIDTH / 10; i++) {
                g.drawLine(i*10, 0, i*10, HEIGHT);
            }
            for(int i = 0; i < HEIGHT / 10; i++) {
                g.drawLine(0, i*10, WIDTH, i*10);
            }
            for(int i = 0; i < snake.size(); i++) {
                snake.get(i).draw(g);
            }
            for(int i = 0; i<apples.size(); i++) {
                apples.get(i).draw(g);
            }
        } else {
            g.setColor(Color.green);
            g.fillRect(0, 0, 800, 800);
            JLabel gameOver = new JLabel("GAME OVER!");
            gameOver.setText("asd");
            gameOver.setBounds(0, 20, 200, 50);
            gameOver.setFont(new Font("Verdana",1,20));
            add(gameOver);

        }

    }

    //runs the game
    public void run() {
        while(running) {
            update();
            repaint();
        }
    }

    //update gameboard
    public void update() {
        for(int i = 0; i<snake.size(); i++) {
            if(xCrd == snake.get(i).getxCrd() && yCrd == snake.get(i).getyCrd() ) {
                if(i != snake.size() -1) {
                    stop();
                }
            }
        }
        if(snake.size() == 0) {
            b = new BodyPart(xCrd, yCrd, 10);
            snake.add(b);
        }
        //adds apples
        if(apples.size() == 0) {
            int xCrd = rnd2.nextInt(79);
            int yCrd = rnd2.nextInt(79);

            apple = new Apple(xCrd, yCrd, 10);
            apples.add(apple);
        }
        for(int i = 0; i<apples.size(); i++) {
            if(xCrd == apples.get(i).getxCrd() && yCrd == apples.get(i).getyCrd()) {
                size++;
                apples.remove(i);
                i--;
            }
        }

        //this ends the game when the snake hits a wall
        if(xCrd < 0 || xCrd > 79 || yCrd < 0 || yCrd > 79) {
            stop();
        }
        //this rather makes the snake to continue from the other side of the board when snake hits a wall
        /*if(xCrd < 0) {
            xCrd = 79;
        }
        if(xCrd > 79) {
            xCrd = 0;
        }
        if(yCrd < 0) {
            yCrd = 79;
        }
        if(yCrd > 79) {
            yCrd = 0;
        }*/

        ticks++;
        if(ticks > 1000000) { //changes game speed
            if(right) xCrd++;
            if(left) xCrd--;
            if(up) yCrd--;
            if(down) yCrd++;
            ticks = 0;
            //here we move the snake by adding and removing bodyparts
            b = new BodyPart(xCrd, yCrd, 10);
            snake.add(b);
            if(snake.size() > size) {
                snake.remove(0);
            }
        }
    }



    private class Key implements KeyListener{
        //listens to key presses to change the snake's direction
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if(key == KeyEvent.VK_RIGHT && !left) {
                up = false;
                down = false;
                right = true;
            }
            if(key == KeyEvent.VK_LEFT && !right) {
                up = false;
                down = false;
                left = true;
            }
            if(key == KeyEvent.VK_UP && !down) {
                left = false;
                right = false;
                up = true;
            }
            if(key == KeyEvent.VK_DOWN && !up) {
                left = false;
                right = false;
                down = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub

        }
    }
}

