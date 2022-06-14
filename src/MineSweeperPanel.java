import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class MineSweeperPanel extends JPanel implements ActionListener {

    // 1 byte = b7 b6 b5 b4 b3 b2 b1 b0
    // b7: revealing status (0 for not revealed, 1 for revealed)
    // b6: mine status (0 for not mine, 1 for mine)
    // b5: flag status (0 for not flag, 1 for flag)
    // b4:
    // b3, b2, b1, b0: binary value of surrounding mines
    public final int BOX_SIZE;

    public final int BOX_ROWS;
    public final int BOX_COLUMNS;

    public final int FIELD_WIDTH; // = BOX_SIZE * BOX_COLUMNS;
    public final int FIELD_HEIGHT; // = BOX_SIZE * BOX_ROWS;
    private static boolean initialized = false;

    private final Image dirtIcon;
    private final Image bombIcon;
    private final Image flagIcon;
    private final Image fieldIcon;




    private static final MouseAdapter squareListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            System.out.println("X coordinate of clicked button: " + ((JButton)(e.getSource())).getBounds().getX());
        }
    };

    public static final Color[] numberColors = new Color[]{
            Color.WHITE,
            Color.BLUE,
            Color.GREEN,
            Color.RED,
            new Color(0x6A0DAD), // PURPLE
            new Color(0x800000), // MAROON
            new Color(0x30D5C8), //TURQUOISE
            Color.BLACK,
            Color.GRAY
    };


    private byte[][] mineField;


    public MineSweeperPanel() throws IOException {
        this(60, 10, 15);
    }

    public MineSweeperPanel(int boxSize, int rowAmount, int colAmount) throws IOException {


        this.addMouseListener(new MyMouseAdapter());
        BOX_SIZE = boxSize;
        BOX_ROWS = rowAmount;
        BOX_COLUMNS = colAmount;
        FIELD_WIDTH = BOX_SIZE * BOX_COLUMNS;
        FIELD_HEIGHT = BOX_SIZE * BOX_ROWS;

        dirtIcon = ImageIO.read(new File("assets\\block.png")).getScaledInstance(BOX_SIZE, BOX_SIZE, Image.SCALE_SMOOTH);
        bombIcon = ImageIO.read(new File("assets\\bomb.png")).getScaledInstance(BOX_SIZE, BOX_SIZE, Image.SCALE_SMOOTH);
        flagIcon = ImageIO.read(new File("assets/1200px-Minesweeper_flag.svg.png")).getScaledInstance(BOX_SIZE, BOX_SIZE, Image.SCALE_SMOOTH);
        fieldIcon = ImageIO.read(new File("assets/1200px-Minesweeper_field.svg.png")).getScaledInstance(BOX_SIZE, BOX_SIZE, Image.SCALE_SMOOTH);


        setLayout(new GridLayout(BOX_ROWS, BOX_COLUMNS));

        System.out.println("p.BOX_ROWS: " + BOX_ROWS + " p.BOX_COLUMNS: " + BOX_COLUMNS);


        for(int i = 0; i < BOX_ROWS * BOX_COLUMNS;i++){
            /*
            JButton b = new JButton();
            b.setText("1");
            b.setBackground(Color.LIGHT_GRAY); //  exposed field
            b.setFont(new Font("Arial",Font.PLAIN, BOX_SIZE));
            b.setMargin(new Insets(0, 0, 0, 0));
            */
            JButton b = new JButton(new ImageIcon(fieldIcon));
            b.setBackground(Color.WHITE);

            //b.setPreferredSize(new Dimension(p.BOX_SIZE, p.BOX_SIZE));
            b.addMouseListener(squareListener);
            add(b);

        }


        setBackground(Color.GREEN);

        mineField = new byte[BOX_ROWS][BOX_COLUMNS];


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
