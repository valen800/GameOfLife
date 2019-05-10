
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.*;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alu54279423k
 */
public class Board extends JPanel{
    
    private Timer timer;
    private Snake snake;
    private Food food;
    private int deltaTime;
    private MyKeyAdapter keyAdepter;
    private int numNodes = 4;
    private ScoreBoard score;
    private boolean turning = false;
    private int ccFoodSpecial = 0;
    
    public void setScoreBoard(ScoreBoard scoreBoard) {
        this.score = scoreBoard;
    }
    
    class MyKeyAdapter extends KeyAdapter {
        
        @Override
        public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(snake.getDirection() != Direction.RIGHT && !turning) {
                        turning = true;
                        snake.setDirection(Direction.LEFT);
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (snake.getDirection() != Direction.LEFT && !turning) {
                        turning = true;
                        snake.setDirection(Direction.RIGHT);
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (snake.getDirection() != Direction.DOWN && !turning) {
                        turning = true;
                        snake.setDirection(Direction.UP);
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (snake.getDirection() != Direction.UP && !turning) {
                        turning = true;
                        snake.setDirection(Direction.DOWN);
                    }
                    break;
                case KeyEvent.VK_P:
                    pauseGame();
                    break;
                default:
                    break;
                }
            repaint();
        }
    }
    
    public Board() {
        super();
        snake = new Snake(numNodes);
        food = new Food(snake, numNodes);
        
        keyAdepter = new MyKeyAdapter();
        addKeyListener(keyAdepter);
        setFocusable(true);
        
        deltaTime = 150;
        timer = new Timer(deltaTime, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                snake.movementSnake();
                SnakeBodyDetected();
                BoardEgdeDetected();
                FoodDetected();
                repaint();
                turning = false;
            }
        });
        timer.start();
    }
    
    public void FoodDetected() {
        int[] positionsHead = {snake.getRowHead(), snake.getColHead()};
        int[] positionsFood = {food.getRow(),food.getCol()};
        
        int newRowFood = (int) (Math.random() * (Config.numRows - 5)+5);
        int newColFood = (int) (Math.random() * (Config.numCols - 5)+5);
        
        if(positionsHead[0] == positionsFood[0] && positionsHead[1] == positionsFood[1]) {
            if (ccFoodSpecial == 20) {
                food.setRow(newRowFood);
                food.setCol(newColFood);
                
                addNodes(5);
                
                ccFoodSpecial = 0;
                repaint();
            } else {
                food.setRow(newRowFood);
                food.setCol(newColFood);
                
                addNodes(2);
               
                score.incrementScore();
                ccFoodSpecial++;
                repaint();
            }
        }
    }
    
    private void addNodes(int node) {
        if (node > 0) {
            for(int i = 0; i<node; i++) {
                snake.getListNodes().add(snake.getListNodes().get(snake.getListNodes().size() - 1));
            }
        }
    }
    
    public void SnakeBodyDetected() {
        int[] positionsHead = {snake.getRowHead(), snake.getColHead()};
        
        for (int i=1; i<snake.getListNodes().size() -1; i++) {
            if(positionsHead[0] == snake.getListNodes().get(i).getRow() 
                    && positionsHead[1] == snake.getListNodes().get(i).getCol()) {
                gameOver();
                System.out.println("Board.SnakeBodyDetected()");
            }
        }
    }
    
    public void BoardEgdeDetected() {
        int[] positionsHead = {snake.getRowHead(), snake.getColHead()};
        for (int row=0; row<Config.numRows; row++) {
            if(positionsHead[0] == row && positionsHead[1] == (Config.numCols - Config.numCols) - 1) {
                gameOver();
                System.out.println("Board.EgdeBoardDetected()");
            }
        }
        for (int col=0; col<Config.numCols; col++) {
            if(positionsHead[0] == Config.numRows && positionsHead[1] == col) {
                gameOver();
                System.out.println("Board.EgdeBoardDetected()");
            }
        }
        for (int row=0; row<Config.numRows; row++) {
            if(positionsHead[0] == row && positionsHead[1] == Config.numCols) {
                gameOver();
                System.out.println("Board.EgdeBoardDetected()");
            }
        }
        for (int col=0; col<Config.numRows; col++) {
            if(positionsHead[0] == (Config.numRows - Config.numRows)-1 && positionsHead[1] == col) {
                gameOver();
                System.out.println("Board.EgdeBoardDetected()");
            }
        }
    }
    
    public void gameOver() {
        timer.stop();
        JOptionPane.showMessageDialog(null, "Game Over, press Ok to continue");
        reset();
        timer.restart();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        paintBoard(g2d);
        snake.paintSnake(g2d, getSquareWidth(), getSquareHeight());
        if(ccFoodSpecial == 20) {
            food.paintFood(g2d, getSquareWidth(), getSquareHeight(), Color.BLUE);
        } else {
            food.paintFood(g2d, getSquareWidth(), getSquareHeight(), Color.RED);
        }
    }
    
    public int getSquareWidth() {
        return getWidth() / Config.numCols;
    }
    
    public int getSquareHeight() {
        return getHeight() / Config.numRows;
    }
    
    public void paintBoard(Graphics2D g2d) {
        for (int row = 0; row<Config.numRows; row++) {
            for (int col = 0; col<Config.numCols; col++) {
                drawSquare(g2d, getSquareWidth(), getSquareHeight(), col, row, Color.GRAY);
            }
        }
    }
    
    public static void drawSquare(Graphics2D g, int squareWidth, int squareHeight,
            int col, int row, Color color) {
        
        int x = col * squareWidth;
        int y = row * squareHeight;
        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth - 2, squareHeight - 2);
        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight - 1, x, y);
        g.drawLine(x, y, x + squareWidth - 1, y);
        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight - 1, x + squareWidth - 1, y + squareHeight - 1);
        g.drawLine(x + squareWidth - 1, y + squareHeight - 1, x + squareWidth - 1, y + 1);
    }
    
    public void pauseGame() {
        timer.stop();
        JOptionPane.showMessageDialog(null, "Pause, press OK");
        timer.start();
    }
    
    private void reset() {
        snake = new Snake(numNodes);
        food = new Food(snake, numNodes);
        
        keyAdepter = new MyKeyAdapter();
        addKeyListener(keyAdepter);
        setFocusable(true);
        
        this.score.resetScore();
        deltaTime = 250;
        ccFoodSpecial = 0;
        
    }
    
}
