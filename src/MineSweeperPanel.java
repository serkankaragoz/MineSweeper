import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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

    private static final byte revealStatusByte = 7;
    private static final byte mineStatusByte = 6;
    private static final byte flagStatusByte = 5;


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

    private byte[][] mineField;
    private JButton[][] mineButtons;



    // return the p+1 th rightmost bit
    public static boolean getBit(byte n, byte p)
    {
        int mask = 1 << p;
        return ((n & mask) >> p) == 1;
    }

    // sets the p+1 th rightmost bit and returns the new byte
    public static byte setBit(byte n, byte p, boolean b)
    {
        byte fin = b ? (byte)1 : (byte)0;
        int mask = 1 << p;
        return (byte)((n & ~mask) |
                ((fin << p) & mask));
    }

    private boolean getRevealStatus(int x, int y){
        return getBit(mineField[y][x], revealStatusByte);
    }
    private void setRevealStatus(int x, int y, boolean status){
        mineField[y][x] = setBit(mineField[y][x], revealStatusByte, status);
    }

    private boolean getMineStatus(int x, int y){
        return getBit(mineField[y][x], mineStatusByte);
    }
    private void setMineStatus(int x, int y, boolean status){
        mineField[y][x] = setBit(mineField[y][x], mineStatusByte, status);
    }

    private boolean getFlagStatus(int x, int y){
        return getBit(mineField[y][x], flagStatusByte);
    }
    private void setFlagStatus(int x, int y, boolean status){
        mineField[y][x] = setBit(mineField[y][x], flagStatusByte, status);
    }

    private void increaseSurroundingMineCounts(int x, int y){

        if(!getMineStatus(x, y)) return;

        for (int i = Math.max(0, y-1); i < Math.min(BOX_ROWS, y+2); i++) {
            for (int j = Math.max(0, x-1); j < Math.min(BOX_COLUMNS, x+2); j++) {
                if(! getMineStatus(j, i)){
                    mineField[i][j] = (byte) (mineField[i][j] + 1);
                }
            }
        }
    }

    private int[] partiallyShuffle(int k, int excludedIndex, int n){
        // returns k non repeating number with range of [0,n)
        if(k >= n || excludedIndex >= n){
            throw new ArithmeticException("excludedIndex and k argument must be smaller than n");
        }

        if(k < 1) return new int[]{};

        ArrayList<Integer> arr = new ArrayList<>();
        HashSet<Integer> numberSet = new HashSet<>();

        int[] reservedRange = new int[]{};
        for (int i = 0; i < n; i++) {
            numberSet.add(i);
        }

        int x = excludedIndex % BOX_COLUMNS;
        int y = excludedIndex / BOX_COLUMNS;


        boolean decrease = (k >= n);
        k = k >= n ? n : k;

        for (int i = Math.max(0, y-1); i < Math.min(BOX_ROWS, y+2); i++) {
            for (int j = Math.max(0, x-1); j < Math.min(BOX_COLUMNS, x+2); j++) {
                numberSet.remove(i*BOX_COLUMNS + j);
                if(decrease){ k--;}
            }
        }



        arr.addAll(numberSet);
        Collections.shuffle(arr);
        numberSet = null;


        int temp = arr.get(arr.size()-1);
        int rnd = new Random().nextInt(arr.size());
        arr.set(arr.size()-1, arr.get(rnd));
        arr.set(rnd, temp);

        int[] newArr = new int[Math.min(k, arr.size())];


        for(int i = 0; i < Math.min(k, arr.size()); i++){
            newArr[i] = arr.get(i);
        }
        System.out.println("Size of random shuffled array: " + newArr.length);

        return newArr;

    }


    private final MouseAdapter squareListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            Rectangle temp = ((JButton)(e.getSource())).getBounds();
            int x = (int)temp.getX()/BOX_SIZE;
            int y = (int)temp.getY()/BOX_SIZE;
            int tempX, tempY;

            if(!initialized){




                int[] bombedFields = partiallyShuffle(MINE_AMOUNT, x + y*BOX_COLUMNS,BOX_ROWS*BOX_COLUMNS);


                for(int i = 0;i < bombedFields.length; i++){
                    tempX = bombedFields[i] % BOX_COLUMNS;
                    tempY = bombedFields[i] / BOX_COLUMNS;
                    mineButtons[tempY][tempX].setIcon(new ImageIcon(bombIcon));
                    setMineStatus(tempX, tempY, true);
                }

                for(int i = 0;i < bombedFields.length; i++){
                    tempX = bombedFields[i] % BOX_COLUMNS;
                    tempY = bombedFields[i] / BOX_COLUMNS;
                    increaseSurroundingMineCounts(tempX, tempY);
                }

                // y coordinates
                for (int i = 0; i < BOX_ROWS; i++) {
                    // x coordinates
                    for (int j = 0; j < BOX_COLUMNS; j++) {
                        if(mineField[i][j] % 16 > 0 && mineField[i][j] % 16 < 9 && !getMineStatus(j, i)){
                            mineButtons[i][j].setIcon(null);
                            mineButtons[i][j].setForeground(numberColors[mineField[i][j] % 15]);
                            mineButtons[i][j].setFont(new Font("Arial",Font.BOLD, BOX_SIZE/2));
                            mineButtons[i][j].setText(String.valueOf(mineField[i][j] % 16));
                            setRevealStatus(j, i, true);
                        }
                    }
                }

                initialized = true;
            }


            System.out.println(Integer.toBinaryString(mineField[y][x] & 0xFF));

            if(!getRevealStatus(x, y)) {
                switch (e.getButton()) {
                    // left click
                    case 1: {

                        if(!getFlagStatus(x, y)){
                            mineButtons[y][x].setIcon(null);
                            setRevealStatus(x, y, true);
                        }

                        break;
                    }
                    // right click
                    case 3: {
                        if (getFlagStatus(x, y)) {
                            mineButtons[y][x].setIcon(new ImageIcon(fieldIcon));
                            setFlagStatus(x, y, false);
                        } else {
                            mineButtons[y][x].setIcon(new ImageIcon(flagIcon));
                            setFlagStatus(x, y, true);
                        }


                    }
                }
            }

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
            Color.GRAY,
            Color.cyan
    };


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

        dirtIcon = ImageIO.read(new File("assets/block.png")).getScaledInstance(BOX_SIZE, BOX_SIZE, Image.SCALE_SMOOTH);
        bombIcon = ImageIO.read(new File("assets/bomb.png")).getScaledInstance(BOX_SIZE, BOX_SIZE, Image.SCALE_SMOOTH);
        flagIcon = ImageIO.read(new File("assets/1200px-Minesweeper_flag.svg.png")).getScaledInstance(BOX_SIZE, BOX_SIZE, Image.SCALE_SMOOTH);
        fieldIcon = ImageIO.read(new File("assets/1200px-Minesweeper_field.svg.png")).getScaledInstance(BOX_SIZE, BOX_SIZE, Image.SCALE_SMOOTH);


        setLayout(new GridLayout(BOX_ROWS, BOX_COLUMNS));

        System.out.println("p.BOX_ROWS: " + BOX_ROWS + " p.BOX_COLUMNS: " + BOX_COLUMNS);


        for(int i = 0; i < BOX_ROWS * BOX_COLUMNS;i++){

            JButton b = new JButton(new ImageIcon(fieldIcon));
            b.setBackground(Color.LIGHT_GRAY);

            //b.setPreferredSize(new Dimension(p.BOX_SIZE, p.BOX_SIZE));
            b.addMouseListener(squareListener);
            add(mineButtons[i / BOX_COLUMNS][i % BOX_COLUMNS] = b);

        }


        setBackground(Color.GREEN);

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
