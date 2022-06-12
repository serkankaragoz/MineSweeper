import javax.swing.*;
import java.awt.*;

public class MineSweeperFrame extends JFrame{
    public MineSweeperFrame(){
        this.setLayout(null);
        MineSweeperPanel p = new MineSweeperPanel();
        p.setLayout(null);
        p.setSize(100, 100);
        p.setBounds(50,50, 300,300);
        // p.paint();
        p.setBackground(Color.GREEN);


        this.add(p);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();


        this.setBounds(200, 400, 400, 400);
        this.setResizable(false);
        this.setVisible(true);
    }

    /*
    public MineSweeperFrame(int boxSize, int rowAmount, int colAmount){
        this.setLayout(null);
        MineSweeperPanel p = new MineSweeperPanel();
        //p.setLayout(null);
        p.setSize(100, 100);
        p.setBounds(50,50, 300,300);
        // p.paint();
        p.setBackground(Color.GREEN);


        this.add(p);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();


        this.setBounds(200, 400, 600, 600);
        this.setResizable(false);
        this.setVisible(true);
    }
    */
}
