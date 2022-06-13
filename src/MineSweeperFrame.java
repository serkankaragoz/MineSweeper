import javax.swing.*;
import java.awt.*;

public class MineSweeperFrame extends JFrame{
    public MineSweeperFrame(){

        MineSweeperPanel p = new MineSweeperPanel();
        this.setLayout(null);
        this.add(p);

        p.setLayout(null);
        p.setBackground(Color.GREEN);

        p.setBounds(p.BOX_SIZE,2*p.BOX_SIZE, p.FIELD_WIDTH,p.FIELD_HEIGHT);


        //p.setBounds(1,1, p.FIELD_WIDTH,p.FIELD_HEIGHT);


        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        this.setResizable(false);
        // The 17 and 40 numbers to make the minesweeper field symmetric since
        this.setBounds(200, 400, p.FIELD_WIDTH + 2 * p.BOX_SIZE + 17, p.FIELD_HEIGHT + 3 * p.BOX_SIZE + 40);
        System.out.println("Width: " + this.getBounds() + " Height: " + this.getAlignmentY());

        //this.setBounds(200, 400, 200, 200);


        this.setVisible(true);
    }
}
