public class SudokuBoard {

    private int[][] board;
    private int[][] originalBoard;

    public SudokuBoard() {
        this.board = new int[9][9];
        this.originalBoard = new int[9][9];
    }

    public void displayBoard() {
        // row
        for (int row = 0; row < 9; row++) {
            // border
            if (row % 3 == 0) {
                System.out.println(ConsoleColors.colorize("+-------+-------+-------+", ConsoleColors.WHITE));
            }

            StringBuilder line = new StringBuilder();
            // column
            for (int col = 0; col < 9; col++) {

                // border
                if (col % 3 == 0) {
                    line.append(ConsoleColors.colorize("| ", ConsoleColors.WHITE));
                }

                int val = this.board[row][col];
                line.append(val == 0 ? ". " : val + " ");
            }
            line.append(ConsoleColors.colorize("|", ConsoleColors.WHITE));
            System.out.println(line);
        }
        System.out.println(ConsoleColors.colorize("+-------+-------+-------+", ConsoleColors.WHITE));
    }

    public void setValue(int row, int col, int value) {
        if (row < 0 || row >= 9 || col < 0 || col >= 9) {
            throw new IndexOutOfBoundsException("row/col must be between 0 and 8");
        }
        this.board[row][col] = value;
    }

    public void resetBoard() {
        this.board = clone(this.originalBoard);
    }

    public SudokuBoard loadBoard(int[][] newBoard) {
        if (newBoard.length != 9 || newBoard[0].length != 9) {
            throw new IndexOutOfBoundsException("row/col must be between 0 and 8");
        }

        this.board = clone(newBoard);
        this.originalBoard = clone(newBoard);
        return this;
    }


    private int[][] clone(int[][] source) {
        int[][] clone = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(source[i], 0, clone[i], 0, 9);
        }
        return clone;
    }
}
