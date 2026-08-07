package main;

public class SudokuBoard {

    private int[][] board;
    private int[][] originalBoard;
    private int filled;

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

    public int getFilled() {
        return filled;
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

            // column
            for (int col = 0; col < 9; col++) {
                // number border
                if (col % 3 == 0) {
                    output.append(colored ? ConsoleColors.colorize("| ", ConsoleColors.WHITE) : "| ");
                }

                int val = this.board[row][col];
                if (colored && pos != null && pos[0] == row && pos[1] == col) {
                    output.append(ConsoleColors.BLUE_BACKGROUND);
                }

                // predefined?
                if (originalBoard[row][col] == 0) output.append(ConsoleColors.GREEN);

                output.append(val == 0 ? ". " : val + " ");

                if (colored) output.append(ConsoleColors.RESET);
            }
            output.append(colored ? ConsoleColors.colorize("|", ConsoleColors.WHITE) : "|").append("\n");
        }

        if (!raw) output.append(colored ? ConsoleColors.colorize(border, ConsoleColors.WHITE) : border);

        return output.toString();
    }


    // UNUSED: i just did my own renderer.... Needed for side by side printing.
    public void displayBoard() {
        System.out.println(toString(false, true));
    }

    public void setValue(int row, int col, int value) {
        if (row < 0 || row >= 9 || col < 0 || col >= 9) {
            throw new IndexOutOfBoundsException("row/col must be between 0 and 8");
        }
        if (!isCellEditable(row, col)) {
            throw new IllegalArgumentException("cell " + row + ", " + col + " is not editable ");
        }
        if (value != this.board[row][col]) {
            if (value == 0) {
                this.filled -= 1;
            } else {
                this.filled += 1;
            }
        }
        this.board[row][col] = value;
    }

    public void resetBoard() {
        this.board = GameUtils.copyBoard(this.originalBoard);
        this.filled = 0;
    }

    public void loadBoard(int[][] newBoard) {
        if (newBoard.length != 9 || newBoard[0].length != 9) {
            throw new IndexOutOfBoundsException("invalid board size");
        }

        this.board = GameUtils.copyBoard(newBoard);
        this.originalBoard = GameUtils.copyBoard(newBoard);
    }

    public boolean isCellEditable(int row, int col) {
        return originalBoard[row][col] == 0;
    }


}
