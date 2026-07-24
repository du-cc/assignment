package main;

public class SudokuBoard {

    private int[][] board;
    private int[][] originalBoard;

    public SudokuBoard() {
        this.board = new int[9][9];
        this.originalBoard = new int[9][9];
    }

    public int[][] getBoard() {
        return this.board;
    }

    public int[][] getOriginalBoard() {
        return this.originalBoard;
    }

    @Override
    public String toString() {
        return toString(true, false, null);
    }

    public String toString(boolean raw) {
        return toString(raw, false, null);
    }

    public String toString(boolean raw, boolean colored) {
        return toString(raw, colored, null);
    }

    public String toString(boolean raw, boolean colored, int[] pos) {
        StringBuilder output = new StringBuilder();
        String border = "+-------+-------+-------+";
        // row
        for (int row = 0; row < 9; row++) {
            // border
            if (row % 3 == 0 && !raw) {
                output.append(colored ? ConsoleColors.colorize(border, ConsoleColors.WHITE) : border).append("\n");
            }

            StringBuilder line = new StringBuilder();
            // column
            for (int col = 0; col < 9; col++) {
                // number border
                // border
                if (col % 3 == 0) {
                    line.append(colored ? ConsoleColors.colorize("| ", ConsoleColors.WHITE) : "| ");
                }

                int val = this.board[row][col];
                if (pos != null) if (pos[0] == row && pos[1] == col) line.append(ConsoleColors.BLUE_BACKGROUND);
                line.append(val == 0 ? ". " : val + " ");
                line.append(ConsoleColors.RESET);
            }
            line.append(colored ? ConsoleColors.colorize("|", ConsoleColors.WHITE) : "|").append("\n");
            output.append(line);
        }

        if (!raw) output.append(colored ? ConsoleColors.colorize(border, ConsoleColors.WHITE) : border);

        return output.toString();
    }


    // UNUSED: i just do my own renderer....
//    public void displayBoard() {
//        System.out.println(toString(false, true));
//    }

    public void setValue(int row, int col, int value) {
        if (row < 0 || row >= 9 || col < 0 || col >= 9) {
            throw new IndexOutOfBoundsException("row/col must be between 0 and 8");
        }
        if (!isCellEditable(row, col)) {
            throw new IllegalArgumentException("cell is not editable");
        }
        this.board[row][col] = value;
    }

    public void resetBoard() {
        this.board = GameUtils.copyBoard(this.originalBoard);
    }

    public void loadBoard(int[][] newBoard) {
        if (newBoard.length != 9 || newBoard[0].length != 9) {
            throw new IndexOutOfBoundsException("row/col must be between 0 and 8");
        }

        this.board = GameUtils.copyBoard(newBoard);
        this.originalBoard = GameUtils.copyBoard(newBoard);
    }

    public boolean isCellEditable(int row, int col) {
        return originalBoard[row][col] == 0;
    }


}
