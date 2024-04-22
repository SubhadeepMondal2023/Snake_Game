import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; // to store segments of the snake's body 
import java.util.Random; // will be used to place our food at random positions(X, Y)
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    private class Tile {
        int x;
        int y;
        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    int boardWidth;
    int boardHeight;
    int tileSize = 25;


    //Snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    //Food
    Tile food;
    Random random;

    // logic of the game
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;


    SnakeGame(int boardWidth, int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true); // makes snakeGame listen for keyPressses

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();


        food = new Tile(10, 10); 
        random = new Random();
        placeFood();

        gameLoop = new Timer(100, this);
        gameLoop.start(); 

        velocityX = 0;
        velocityY = 0;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        // Grid( makes it easier to visualize )
        for(int i=0; i<boardWidth/tileSize; i++){
            // (x1, y1, x2, y2)
            // vertical lines
            g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
            // horizontal lines
            g.drawLine(0, i*tileSize, boardWidth, i*tileSize);
        }


        // Food
        g.setColor(Color.red);
        //g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
        g.fillOval(food.x * tileSize, food.y * tileSize, tileSize, tileSize);

        // For the snake head
        g.setColor(Color.white);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, false);

        // For the snake body
        for(int i=0; i<snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
        }

        // Score
        g.setFont(new Font ("Arial", Font.PLAIN, 16));
        if(gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over: "+ String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
        else{
            g.drawString("Score: "+ String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
    }

    public void placeFood(){
        food.x = random.nextInt(boardWidth/ tileSize); //600 / 25 = 24, so x randomly generated b/w 0 -> 24
        food.y = random.nextInt(boardHeight/ tileSize); //600 / 25 = 24, y also randomly generated b/w 0 -> 24

    }

    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x ==  tile2.x && tile1.y == tile2.y;
    }

    public void move(){
        // eat food
        if(collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        // Snake Body
        for(int i = snakeBody.size()-1; i>=0; i--){
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }

            else{
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        // Snake Head   
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;


        // Game Over Conditions
        for (int i = 0; i < snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            // collide with the snake head
            if(collision(snakeHead, snakePart)){
                gameOver = true;
            }
        }

        if(snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth || snakeHead.y * tileSize < 0 ||  snakeHead.y * tileSize > boardHeight){
            gameOver = true;
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        move(); // does the motion of the snake
        repaint(); // draws the board again
        if(gameOver){
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1){
            velocityX = 0;
            velocityY = -1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1){
            velocityX = 0;
            velocityY = 1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1){
            velocityX = -1;
            velocityY = 0;
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1){
            velocityX = 1;
            velocityY = 0;
        }
    }


    // unused methods
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
