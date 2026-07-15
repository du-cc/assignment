package main;

import java.util.Random;

public class GameUtils {
    public static int[][] generateEmptyBoard() {
        return new int[9][9];
    }

    public static long generateNewSeed() {
        return new Random().nextLong();
    }

    public static int[][] copyBoard(int[][] source) {
        int[][] clone = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(source[i], 0, clone[i], 0, 9);
        }
        return clone;
    }
}
