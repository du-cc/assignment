package main;

public class SudokuValidator {
    public static boolean checkRow(int[][] board, int row, int value) {
        for (int i = 0; i < 9; i++) if (board[row][i] == value) return true;
        return false;
    }
    public static boolean checkColumn(int[][] board, int col, int value) {
        for (int i = 0; i < 9; i++) if (board[i][col] == value) return true;
        return false;
    }
    public static boolean checkSubGrid(int[][] board, int row, int col, int value) {
        int boxIndex = (row / 3) * 3, bc = (col / 3) * 3;
        for (int r = boxIndex; r < boxIndex + 3; r++)
            for (int c = bc; c < bc + 3; c++)
                if (board[r][c] == value) return true;
        return false;
    }
    public static boolean isValidMove(int[][] board, int row, int col, int value) {
        return !checkRow(board, row, value) && !checkColumn(board, col, value) && !checkSubGrid(board, row, col, value);
    }
}
