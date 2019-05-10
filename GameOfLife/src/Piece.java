
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class Piece extends Board{
    
    private int numRows;
    private int numCols;
    private MainFrame main;
    private Board board;
    
    public Piece() {
        super();
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                
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
    }
    
    public void setBoard(Board board) {
        this.board = board;
    }
    
    public void setMainFrame(MainFrame main) {
        this.main = main;
    }
    
    private int getSquareWidth() {
        return getWidth() / numCols;
    }
    
    private int getSquareHeight() {
        return getHeight() / numRows;
    }
}
