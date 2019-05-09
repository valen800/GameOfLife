
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alu54279423k
 */
public class Snake {
    
    private List<Node> body;
    private Direction direction = Direction.RIGHT;
    private int remainGrow = 4;
    long tInit;
    
    public Snake(int numNodes) {
        body = new ArrayList<>();
        for(int i=1; i <= numNodes; i++) {
            body.add(new Node((Config.numRows/2), (Config.numCols/2) - i));
        }
    }
    
    public void movementSnake() {
        tInit = System.currentTimeMillis();
        float t;
        t = (System.currentTimeMillis() - tInit)/100.0f;
            Toolkit.getDefaultToolkit().sync();
        Node head = body.get(0);
        int[] positions = {head.getRow(),head.getCol()};
        Direction direction = this.direction;
        switch (direction) {
            case DOWN:
                head.setRow(head.getRow() +1);
                body.set(0, head);
                break;
            case UP:
                head.setRow(head.getRow() -1);
                body.set(0, head);
                break;
            case LEFT:
                head.setCol(head.getCol() -1);
                body.set(0, head);
                break;
            case RIGHT:
                head.setCol(head.getCol() +1);
                body.set(0, head);
                break;
            default:
                break;
        }
        Node node = new Node(positions[0], positions[1]);
        Node nextNode = body.get(1);
        body.set(1, node);
        
        for (int i=2; i < body.size(); i++) {
            positions[0] = nextNode.getRow();
            positions[1] = nextNode.getCol();
            node = new Node(positions[0], positions[1]);
            nextNode = body.get(i);
            body.set(i, node);
        }
    }
    
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    
    public Direction getDirection() {
        return this.direction;
    }
    
    public void paintSnake(Graphics2D g, int squareWidth, int squareHeight) {
        boolean headColor = false;
        for (Node node: body) {
            if(!headColor) {
                Board.drawSquare(g, squareWidth, squareHeight, node.getCol(),node.getRow() , Color.yellow);
                headColor = true;
            } else {
                Board.drawSquare(g, squareWidth, squareHeight, node.getCol(),node.getRow() , Color.green);
            }
        }
    }
    
    public int getRowHead() {
        return body.get(0).getRow();
    }
    
    public int getColHead() {
        return body.get(0).getCol();
    }
    
    public List<Node> getListNodes() {
        return body;
    }
            
}
