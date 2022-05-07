import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;
import java.util.ArrayList;
import java.util.Random;

public class SnakePanel extends JPanel implements Runnable {
    //logic variables initialization
    private static final long serialVersionUID = 1L; //universal version identifier
    private Thread thread; //not sure how threads work in this
    private Key key; //key object for movement keys
    public boolean running = false; //checker for if the game is running or not
    private boolean start = true; //checker for if player is in starting screen
    Random rnd = new Random(); //new randomizer
    private int ticks = 0; //game's ticks
    //sizing and positions
    public static final int WIDTH = 800, HEIGHT = 800; //game board size
    private int xCrd = 40, yCrd = 40; //(starting) location of snake's head
    private int size = 5; //snake's size
    private boolean right = false, left = false, up = false, down = true; //direction of the snake
    //entities initialization
    private BodyPart b;
    public ArrayList<BodyPart> snake;
    private Apple apple;
    private ArrayList<Apple> apples;

    //initialize game board's screen and
    public void Screen() {
        setFocusable(true);
        requestFocus(true); //fixes keys not being responsive sometimes when opening the game
        key = new Key(); // new key object for controls
        addKeyListener(key);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        snake = new ArrayList<BodyPart>();
        apples = new ArrayList<Apple>();
        startScreen();
    }

    //function to check if player is in starting screen
    public void startScreen(){
        //when in starting screen wait until player starts the game
        if(start){
            System.out.println("Welcome to snake game!");
        } else {
            //start the game when player presses space
            start();
        }
    }

    //starts the game
    public void start() {
        int rndLuku = rnd.nextInt(4); //randomizes the snake's starting direction
        if(rndLuku == 0) { //right
            up = false;
            down = false;
            right = true;
            left = false;
        }
        if(rndLuku == 1) { //down
            up = false;
            down = true;
            right = false;
            left = false;
        }
        if(rndLuku == 2) { //left
            up = false;
            down = false;
            right = false;
            left = true;
        }
        if(rndLuku == 3) { //up
            up = true;
            down = false;
            right = false;
            left = false;
        }
        running = true;
        thread = new Thread((Runnable) this, "Game loop"); //not familiar how threads work in this
        thread.start();
    }

    //runs the game
    public void run() {
        while(running) {
            update();
            repaint();
        }
    }

    //stops the game
    public void stop() {
        System.out.println("Game over!");
        running = false;
        repaint(); //repaint once again to ensure drawing the game over screen
        try {
            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //initialize a new game when player loses and starts over
    public void newGame(){
        System.out.println("Starting a new game...");
        xCrd = 40; //x-location of snake's head
        yCrd = 40; //y-location of snake's head
        size = 500; //snake's size
        ticks = 0;
        Screen();
    }

    // draw the game board
    public void paint(Graphics g) {
        //when opening game, display start screen
        if(start && !running){
            g.setColor(Color.black);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.setColor(Color.white);
            g.setFont(new Font("arial", Font.BOLD, 50));
            g.drawString("SNAKE GAME", 235, 390);
            g.setFont(new Font("arial", Font.BOLD, 20));
            g.drawString("press space to start", 310, 440);
            g.drawString("press escape to exit", 310, 470);
        }
        //when the game is running draw the board and entities
        else if(running == true){
            g.clearRect(0, 0, WIDTH, HEIGHT); //removes tail, moves snake

            //black background
            g.setColor(Color.black);
            g.fillRect(0, 0, WIDTH, HEIGHT);

            //gray tiles
            g.setColor(Color.GRAY);
            for(int i = 0; i < WIDTH / 10; i++) {
                g.drawLine(i*10, 0, i*10, HEIGHT);
            }
            for(int i = 0; i < HEIGHT / 10; i++) {
                g.drawLine(0, i*10, WIDTH, i*10);
            }
            /*g.setColor(Color.red);
            g.fillRect(0, 0, 10, 800);*/
            //snake
            for(int i = 0; i < snake.size(); i++) {
                snake.get(i).draw(g);
            }
            //apple
            for(int i = 0; i<apples.size(); i++) {
                apples.get(i).draw(g);
            }
        //when game stops running display game over screen
        } else {
            g.setColor(Color.black);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.setColor(Color.white);
            g.setFont(new Font("arial", Font.BOLD, 50));
            g.drawString("GAME OVER", 250, 390);
            g.setFont(new Font("arial", Font.BOLD, 20));
            g.drawString("press space to restart", 300, 440);
            g.drawString("press escape to exit", 310, 470);
        }
    }

    //checks if the snake collides with itself or the wall
    public void checkCollision(){
        //this slows down the game if it's directly inside update(), checks snake's self collision
        for(int i = 0; i<snake.size(); i++) {
            if(xCrd == snake.get(i).getxCrd() && yCrd == snake.get(i).getyCrd()) {
                if(i != snake.size() -1) {
                    stop();
                }
            }
        }
        //this ends the game when the snake hits a wall
        /*
        if(xCrd < 0 || xCrd > 79 || yCrd < 0 || yCrd > 79) {
            stop();
        }
        */
        //this rather makes the snake to continue from the other side of the board when snake hits a wall
        if(xCrd < 1 && left) {
            xCrd = 80;
        }
        if(yCrd < 1 && up) {
            yCrd = 80;
        }
        if(xCrd > 78 && right) {
            xCrd = -1;
        }
        if(yCrd > 78 && down) {
            yCrd = -1;
        }

    }

    //update gameboard entities
    public void update() {
        //adds apples when the game starts or when an apple is eaten
        if(apples.size() == 0) {
            int xCrdApple = rnd.nextInt(79);
            int yCrdApple = rnd.nextInt(79);

            apple = new Apple(xCrdApple, yCrdApple, 10);
            apples.add(apple);
        }
        //if snake eats an apple, remove it from the board
        for(int i = 0; i<apples.size(); i++) {
            if(xCrd == apples.get(i).getxCrd() && yCrd == apples.get(i).getyCrd()) {
                size++;
                apples.remove(i);
                i--;
            }
        }
        ticks++;
        if(ticks > 1000000) { //changes game speed, not sure how
            checkCollision(); //checks collision before moving
            //here we move update the snake's direction
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

    //listens to key presses to change the snake's direction, start or close the game
    private class Key implements KeyListener{
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_RIGHT && !left) { //change direction to right
                up = false;
                down = false;
                right = true;
            }
            if(key == KeyEvent.VK_LEFT && !right) { //change direction to left
                up = false;
                down = false;
                left = true;
            }
            if(key == KeyEvent.VK_UP && !down) { //change direction to up
                left = false;
                right = false;
                up = true;
            }
            if(key == KeyEvent.VK_DOWN && !up) { //change direction to down
                left = false;
                right = false;
                down = true;
            }
            //when in starting screen, space starts the game
            if(start){
                if(key == KeyEvent.VK_SPACE){
                    start = false;
                }
            }
            //when game ends, space starts a new game
            if(running == false){
                if(key == KeyEvent.VK_SPACE){
                    newGame();
                }
            }
            //exits game when escape button is pressed
            if(key == KeyEvent.VK_ESCAPE){
                System.out.println("Exiting game...");
               System.exit(0);
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

