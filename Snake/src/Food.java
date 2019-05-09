
import java.awt.Color;
import java.awt.Graphics2D;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alu54279423k
 */
public class Food extends Node {

    
    public Food(boolean special, Snake snake, int numNodes){
        super(0, 0);
        int row = (int) (Math.random() * (Config.numRows - 5)+5);
        int col = (int) (Math.random() * (Config.numCols - 5)+5);;
        setRow(row);
        setCol(col);
    }
    
    public void paintFood(Graphics2D g, int squareWidth, int squareHeight) {
        Board.drawSquare(g, squareWidth, squareHeight, getCol(),getRow() , Color.red);
    }
    
            
}
