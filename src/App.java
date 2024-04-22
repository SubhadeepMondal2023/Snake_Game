import javax.swing.*; // for creating window 

public class App {
    public static void main(String[] args) throws Exception {
            int boardWidth = 600;
            int boardHeight = boardWidth;

            JFrame frame = new JFrame("SnakeGame(Snake->White, Food->Red)_by Subhadeep_Mondal(T91/IT/224148)");
            frame.setVisible(true);
            frame.setSize(boardWidth, boardHeight);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);
            frame.add(snakeGame);
            frame.pack();
            snakeGame.requestFocus();
    }
}
