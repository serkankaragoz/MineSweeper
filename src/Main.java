import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here

        try {
            new MineSweeperFrame();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
