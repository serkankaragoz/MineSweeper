import javax.swing.*;
import java.awt.event.*;

public class MineSweeperPanel extends JPanel implements MouseListener, ActionListener {

    // 1 byte = b7 b6 b5 b4 b3 b2 b1 b0
    // b7: revealing status
    //
    private final int BOX_SIZE;

    private final int BOX_ROWS;
    private final int BOX_COLUMNS;

    private final int FIELD_WIDTH; // = BOX_SIZE * BOX_COLUMNS;
    private final int FIELD_HEIGHT; // = BOX_SIZE * BOX_ROWS;


    private byte[][] mineField;

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse clicking detected");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse clicking detected");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse clicking detected");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Mouse clicking detected");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Mouse clicking detected");
    }

    public MineSweeperPanel(){
        this(50, 10, 20);
    }

    public MineSweeperPanel(int boxSize, int rowAmount, int colAmount){
        this.addMouseListener(new MyMouseAdapter());
        BOX_SIZE = boxSize;
        BOX_ROWS = rowAmount;
        BOX_COLUMNS = colAmount;
        FIELD_WIDTH = BOX_SIZE * BOX_COLUMNS;
        FIELD_HEIGHT = BOX_SIZE * BOX_ROWS;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
    }

    public int getTimeElapsed(){
        return -1;
    }

    public int getRemainingMines(){
        return -1;
    }

    public class MyMouseAdapter extends MouseAdapter{

        @Override
        public void mousePressed(MouseEvent e) {
            System.out.println("Mouse clicked on: (" + e.getX() + ", " + e.getY() + ") with button: " + e.getButton());
        }

        /*
        @Override
        public void mouseReleased(MouseEvent e) {
            System.out.println("Mouse released on: (" + e.getX() + ", " + e.getY() + ") with button: " + e.getButton());
        }
        */
    }

}
