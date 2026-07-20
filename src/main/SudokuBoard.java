package main;

public class SudokuBoard {

    private int[][] board;
    private int[][] originalBoard;

    public SudokuBoard() {
        this.board = new int[9][9];
        this.originalBoard = new int[9][9];
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
//        String header = "   1 2 3   4 5 6   7 8 9  ";
        String border = "+-------+-------+-------+";
//        output.append(colored ? ConsoleColors.colorize(header, ConsoleColors.WHITE) : header).append("\n");
        // row
        for (int row = 0; row < 9; row++) {
            // border
            if (row % 3 == 0 && !raw) {
                output.append(colored ? ConsoleColors.colorize(border, ConsoleColors.WHITE) : border).append("\n");
            }

            StringBuilder line = new StringBuilder();
            // column
            for (int col = 0; col < 9; col++) {
//                // number border
//                if (col == 0) {
//                    line.append(ConsoleColors.colorize(String.valueOf(row + 1), ConsoleColors.WHITE));
//                }
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






    public void displayBoard() {
        System.out.println(toString(false, true));
    }

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
