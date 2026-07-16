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

    public static boolean isValidInput(int value) {
        return value > 0 && value <= 9;
    }

    public static boolean isBoardComplete(int[][] board) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == 0) return false;
            }
        }
        return true;
    }

}
