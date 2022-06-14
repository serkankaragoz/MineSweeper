import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MineSweeperFrame extends JFrame{

    public void setBoundsAtMiddle(JFrame frame, int width, int height){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = size.width;
        int screenHeight = size.height;
        frame.setBounds((screenWidth-width)/2, (screenHeight-height)/2, width, height);
    }

    public MineSweeperFrame() throws IOException{



        MineSweeperPanel p = new MineSweeperPanel();
        this.setLayout(null);
        this.add(p);

        p.setBounds(p.BOX_SIZE,2*p.BOX_SIZE, p.FIELD_WIDTH,p.FIELD_HEIGHT);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        this.setResizable(false);
        // The 17 and 40 numbers to make the minesweeper field symmetric since


        int width = p.FIELD_WIDTH + 2 * p.BOX_SIZE + 17;
        int height = p.FIELD_HEIGHT + 3 * p.BOX_SIZE + 40;

        setBoundsAtMiddle(this, width, height);

        System.out.println("Width: " + this.getBounds() + " Height: " + this.getAlignmentY());

        //this.setBounds(200, 400, 200, 200);


        this.setVisible(true);
    }
}
