import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    // TODO: not implemented
    public int[][] generatePuzzle(Difficulty diff) {
        return new int[9][9];
    }

    /**
     * Returns a randomly generated sudoku board. Generated using randomized backtracking algorithm.
     */
    public int[][] generateCompleteBoard() {
        int[][] board = new int[9][9];

        // Tracks what numbers used
        int[] rowUsed = new int[9];
        int[] colUsed = new int[9];
        int[] boxUsed = new int[9];

        fillBoard(board, rowUsed, colUsed, boxUsed);

        return board;
    }

    // TODO: not implemented
    public int[][] removeCells(int[][] board, int count) {
        return new int[9][9];
    }

    // Helpers
    /**
     * Uses the argument instance given to fill up a Sudoku board. Fills uses randomized backtracking algorithm.
     * @param board     Integer array for Sudoku board (Integer)
     * @param rowUsed   Bits representing used numbers in a row. {@code 1} if used, {@code 0} if not used.
     * @param colUsed   Bits representing used numbers in a column. {@code 1} if used, {@code 0} if not used.
     * @param boxUsed   Bits representing used numbers in a box. {@code 1} if used, {@code 0} if not used.
     * @return Boolean (Recursively) depending on whether the cell can be filled.
     */
    private boolean fillBoard(int[][] board, int[] rowUsed, int[] colUsed, int[] boxUsed) {
        // find best cell (least candidate)
        int bestR = -1, bestC = -1, bestCount = 10;

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == 0) {
                    // digits used (unavailable)
                    int used = rowUsed[r] | colUsed[c] | boxUsed[(r / 3) * 3 + (c / 3)];
                    // how much candidate available
                    int mask = 0x1FF & ~used; // NOT, flip
                    int count = Integer.bitCount(mask);

                    if (count < bestCount) {
                        bestR = r;
                        bestC = c;
                        bestCount = count;
                    }
                }
            }
        }


        // completed
        if (bestR == -1) {
            return true;
        }

        // get candidates
        int used = rowUsed[bestR] | colUsed[bestC] | boxUsed[(bestR / 3) * 3 + (bestC / 3)];
        int available = 0x1FF & ~used;
        // no available candidate
        if (available == 0) {
            return false;
        }

        // shuffle candidate
        List<Integer> candidates = new ArrayList<>();
        for (int d = 0; d < 9; d++) {
            // bit shift
            // 1 << d shift the 1 bit to d pos
            // AND with mask to determine if bit available then add that candidate
            if ((available & (1 << d)) != 0) {
                candidates.add(d + 1);
            }
        }
        // finally shuffle the list
        Collections.shuffle(candidates, this.random);

        // try inserting each candidate
        for (int digit : candidates) {
            // bitshift
            int bit = 1 << (digit - 1);

            // inject bit
            // Eg: 1011001(ORI) + 0000100(MASK) = 1011101(MOD)
            board[bestR][bestC] = digit;
            rowUsed[bestR] |= bit;
            colUsed[bestC] |= bit;
            boxUsed[(bestR / 3) * 3 + (bestC / 3)] |= bit;

            // try next recursion with injected
            if (fillBoard(board, rowUsed, colUsed, boxUsed)) {
                return true; // success!
            }

            // backtrack
            // AND with NOT to revert bit
            // 1011101(MOD) + 1111011(NOTMASK) = 1011001(ORI)
            board[bestR][bestC] = 0;
            rowUsed[bestR] &= ~bit;
            colUsed[bestC] &= ~bit;
            boxUsed[(bestR / 3) * 3 + (bestC / 3)] &= ~bit;
        }

        return false;
    }
}
