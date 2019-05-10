
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;


public class Board extends JPanel {
    
    private boolean[][] board;
    private boolean[][] boardTemp;
    private int numCols;
    private int numRows;
    private Timer timer;
    private int deltaTime;
    
    public Board() {
        super();
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    int mouseX = me.getX();
                    int mouseY = me.getY();
                    
                    int row = mouseY / getSquareHeight();
                    int col = mouseX / getSquareWidth();
                    
                    if(!board[row][col]) {
                        board[row][col] = true;
                    } else {
                        board[row][col] = false;
                    }
                    repaint();
                    System.out.println("Mouse clicked");
                }
            }
            @Override
            public void mousePressed(MouseEvent me) {
                
            }
            @Override
            public void mouseReleased(MouseEvent me) {
                
            }
            @Override
            public void mouseEntered(MouseEvent me) {
                
            }
            @Override
            public void mouseExited(MouseEvent me) {
                
            }
        });
        
        numCols = 100;
        numRows = 100;
        board = new boolean[numRows][numCols];
        boardTemp = new boolean[numRows][numCols];
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col <numCols; col++) {
                board[row][col] = false;
                boardTemp[row][col] = false;
            }
        }
        fillRandomly(0.2f);
        deltaTime = 200;
        timer = new Timer(deltaTime, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                tick();
            }
        });
        timer.start();
        timer.stop();
    }
    
    private void tick() {
        for(int row = 0; row<numRows; row++) {
            for (int col = 0; col<numCols; col++) {
                    applyRules(row, col);
            }
        }
        for (int i = 0; i<numRows; i++) {
            for (int j=0; j<numCols; j++) {
                board[i][j] = boardTemp[i][j];
                //System.out.println(boardTemp[i][j]);
            }
        } 
        repaint();
    }
    
    public void fillRandomly(float ratio) {
        int numCells = (int) (numCols * numRows * ratio);
        int cc = 0;
        Random random = new Random();
        while(cc < numCells) {
            int row = random.nextInt(numRows);
            int col = random.nextInt(numCols);
            if (!board[row][col]) {
                board[row][col] = true;
                cc++;
            }
        }
        repaint();
    }
    
    private int getSquareWidth() {
        return getWidth() / numCols;
    }
    
    private int getSquareHeight() {
        return getHeight() / numRows;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int row = 0; row < numRows; row++) {
            for(int col = 0; col < numCols; col++) {
                drawSquare(g, row, col, getSquareWidth(), getSquareHeight());
            }
        }
    }
    
    public void drawSquare(Graphics g, int row, int col, 
                                    int squareWidth, int squareHeight) {
        int x = col * squareWidth;
        int y = row * squareHeight;
        if (board[row][col]) {
            Color color =  new Color(204, 102, 204);
            g.setColor(color);
            g.fillRect(x + 1, y + 1, squareWidth - 2,
                squareHeight - 2);
            g.setColor(color.brighter());
            g.drawLine(x, y + squareHeight - 1, x, y);
            g.drawLine(x, y, x + squareWidth - 1, y);
            g.setColor(color.darker());
            g.drawLine(x + 1, y + squareHeight - 1,
                x + squareWidth - 1, y + squareHeight - 1);
            g.drawLine(x + squareWidth - 1,
                y + squareHeight - 1,
                x + squareWidth - 1, y + 1);
        } else {
            g.setColor(Color.LIGHT_GRAY);
            g.drawRect(x, y, squareWidth, squareHeight);
        }
        
    }

    private void applyRules(int row, int col) {
        //First Rule
        int numNeighbours = getNumNeighbours(row, col);
        if (board[row][col]) {
            if (numNeighbours < 2) {
                boardTemp[row][col] = false;
            }
        }
        //Second Rule
        if (board[row][col]) {
            if (numNeighbours == 2 || numNeighbours == 3) {
                boardTemp[row][col] = true;
            }
        }
        //Third rule
        if (board[row][col]) {
            if (numNeighbours>3) {
                boardTemp[row][col] = false;
            }
        }
        //Forth rule
        if (!board[row][col]) {
            if (numNeighbours == 3) {
                boardTemp[row][col] = true;
            }
        }
    }
    
    private int getNumNeighbours(int row, int col) {
        int counter = 0;
        for(int i = row -1; i <= row + 1; i++) {
            for (int j = col -1; j<= col + 1; j++) {
                if (i >= 0 && i <= numRows-1 && j>=0 && j<= numCols-1) {
                    if (row == i && col == j) {
                    
                    } else {
                        if(board[i][j]) {
                            counter++;
                        }
                    }
                }
            }
        }
        return counter;
    }
    
    public void timerStart() {
        timer.start();
    }
    
    public void timerStop() {
        timer.stop();
    }
    
    public void resetGame() {
        numCols = 100;
        numRows = 80;
        board = new boolean[numRows][numCols];
        boardTemp = new boolean[numRows][numCols];
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col <numCols; col++) {
                board[row][col] = false;
                boardTemp[row][col] = false;
            }
        }
        fillRandomly(0.2f);
        deltaTime = 50;
        timerStop();
        repaint();
    }
    
    public void emptyBoard() {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col <numCols; col++) {
                board[row][col] = false;
                boardTemp[row][col] = false;
            }
        }
        repaint();
    }
    
    public void speedSlider(int deltaTime) {
         this.deltaTime = deltaTime * 20;
         timer.setDelay(deltaTime);
        
    }
    
    public void sliderSize(int size) {
        if (size > 10 || size < 100) {
            numCols = size;
            numRows = size;
        }
        repaint();
    }
    
    public void setCellAlive(int row, int col) {
        boardTemp[row][col] = true;
        board[row][col] = true;
    }
}
