import java.util.Random;

public class SudokuGenerator {
    private Random random;

    public SudokuGenerator() {
        this.random = new Random();
    }

    public SudokuGenerator(long seed) {
        this.random = new Random(seed);
    }

    public enum Difficulty {
        EASY(40, 45),
        MEDIUM(28, 35),
        HARD(20, 25);

        private final int min;
        private final int max;

        Difficulty(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }
    }


    public int[][] generatePuzzle(Difficulty diff) {

    }

    /**
     * Returns a randomly generated sudoku board. Generated using randomized backtracking algorithm.
     */
    public int[][] generateCompleteBoard() {
        int[][] board = new int[9][9];
        // Possible digits on each cell
        int[][] candidates = new int[9][9];

        // Tracks what numbers used
        int[] rowUsed = new int[9];
        int[] colUsed = new int[9];
        int[] boxUsed = new int[9];

        // row and col to fill
        int row = this.random.nextInt(0,8);
        int col = this.random.nextInt(0,8);
        int val = this.random.nextInt(1,9);



    }

    public int[][] removeCells(int[][] board, int count) {

    }

    // Helpers
    private int boxIndex(int r, int c) {
        return (r / 3) * 3 + (c / 3);
    }
}
