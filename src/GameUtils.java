import java.util.Random;

public class GameUtils {
    public static int[][] generateEmptyBoard() {
        return new int[9][9];
    }

    public static long generateNewSeed() {
        return new Random().nextLong();
    }
}
