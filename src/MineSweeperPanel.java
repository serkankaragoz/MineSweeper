import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MineSweeperPanel extends JPanel{

    /*
    *      1 byte = b7 b6 b5 b4 b3 b2 b1 b0
    *      b7: revealing status (0 for not revealed, 1 for revealed)
    *      b6: mine status (0 for not mine, 1 for mine)
    *      b5: flag status (0 for not flag, 1 for flag)
    *      b4:
    *      b3, b2, b1, b0: binary value of surrounding mines
    */


    public final int BOX_SIZE;

    public final int BOX_ROWS;
    public final int BOX_COLUMNS;
    public final int MINE_AMOUNT = 140;

    public final int FIELD_WIDTH; // = BOX_SIZE * BOX_COLUMNS;
    public final int FIELD_HEIGHT; // = BOX_SIZE * BOX_ROWS;
    private static boolean initialized = false;

    private final Image dirtIcon;
    private final Image bombIcon;
    private final Image flagIcon;
    private final Image fieldIcon;

    /*

    // return the p+1 th rightmost bit
    public static int getBit(byte n, byte p)
    {
        int mask = 1 << p;
        return (n & mask) >> p;
    }

    public static int setBit(byte n, byte p, byte b)
    {
        int mask = 1 << p;
        return (n & ~mask) |
                ((b << p) & mask);
    }
    */



    private static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static int[] partiallyShuffle(int k, int excludedIndex, int n){
        // returns k non repeating number with range of [0,n)
        if(k >= n || excludedIndex >= n){
            throw new ArithmeticException("excludedIndex and k argument must be smaller than n");
        }

        if(k < 1) return new int[]{};

        ArrayList<Integer> arr = new ArrayList<>();
        int[] newArr = new int[k];
        for (int i = 0; i < n; i++) {
            if(i != excludedIndex){
                arr.add(i);
            }
        }

        Collections.shuffle(arr);

        int temp = arr.get(n-2);
        int rnd = new Random().nextInt(n-1);
        arr.set(n-2, arr.get(rnd));
        arr.set(rnd, temp);


        for(int i = 0; i < k; i++){
            newArr[i] = arr.get(i);
        }

        return newArr;

    }


    private final MouseAdapter squareListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            Rectangle temp = ((JButton)(e.getSource())).getBounds();
            int x = (int)temp.getX()/BOX_SIZE;
            int y = (int)temp.getY()/BOX_SIZE;
            //mineButtons[y][x].setIcon(new ImageIcon(bombIcon));

            if(initialized){

                return;
            }



            System.out.println("MINE_AMOUNT: " + MINE_AMOUNT);
            System.out.println("excludedIndex: " + (x + y*BOX_COLUMNS));
            System.out.println("n: " + (BOX_ROWS*BOX_COLUMNS));


            int[] bombedFields = partiallyShuffle(MINE_AMOUNT, x + y*BOX_COLUMNS,BOX_ROWS*BOX_COLUMNS);

            for(int i : bombedFields){
                x = i % BOX_COLUMNS;
                y = i / BOX_COLUMNS;
                mineButtons[y][x].setIcon(new ImageIcon(bombIcon));
            }



            initialized = true;

        }
    };

    public static final Color[] numberColors = new Color[]{
            Color.WHITE,
            Color.BLUE,
            Color.GREEN,
            Color.RED,
            new Color(0x6A0DAD), // PURPLE
            new Color(0x800000), // MAROON
            new Color(0x30D5C8), // TURQUOISE
            Color.BLACK,
            Color.GRAY
    };


    private byte[][] mineField;
    private JButton[][] mineButtons;


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
        mineField = new byte[BOX_ROWS][BOX_COLUMNS];
        mineButtons = new JButton[BOX_ROWS][BOX_COLUMNS];

        dirtIcon = ImageIO.read(new File("Testing\\block.png")).getScaledInstance(BOX_SIZE, BOX_SIZE, Image.SCALE_SMOOTH);
        bombIcon = ImageIO.read(new File("Testing\\bomb.png")).getScaledInstance(BOX_SIZE, BOX_SIZE, Image.SCALE_SMOOTH);
        flagIcon = ImageIO.read(new File("Testing/1200px-Minesweeper_flag.svg.png")).getScaledInstance(BOX_SIZE, BOX_SIZE, Image.SCALE_SMOOTH);
        fieldIcon = ImageIO.read(new File("Testing/1200px-Minesweeper_field.svg.png")).getScaledInstance(BOX_SIZE, BOX_SIZE, Image.SCALE_SMOOTH);


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
            b.setBackground(Color.LIGHT_GRAY);

            //b.setPreferredSize(new Dimension(p.BOX_SIZE, p.BOX_SIZE));
            b.addMouseListener(squareListener);
            add(mineButtons[i / BOX_COLUMNS][i % BOX_COLUMNS] = b);

        }


        setBackground(Color.GREEN);

    }

    /*
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
    }*/

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
