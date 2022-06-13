import javax.swing.*;
import java.awt.event.*;

public class MineSweeperPanel extends JPanel implements ActionListener {

    // 1 byte = b7 b6 b5 b4 b3 b2 b1 b0
    // b7: revealing status
    //
    public final int BOX_SIZE;

    public final int BOX_ROWS;
    public final int BOX_COLUMNS;

    public final int FIELD_WIDTH; // = BOX_SIZE * BOX_COLUMNS;
    public final int FIELD_HEIGHT; // = BOX_SIZE * BOX_ROWS;


    private byte[][] mineField;


    public MineSweeperPanel(){
        this(20, 20, 50);
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
    }

}
