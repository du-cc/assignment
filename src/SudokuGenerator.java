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
        int[][] board = generateCompleteBoard();
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
     * Finds the empty cell with the fewest possible candidates.
     * @param board    Integer array for Sudoku board
     * @param rowUsed  Bits representing used numbers in a row
     * @param colUsed  Bits representing used numbers in a column
     * @param boxUsed  Bits representing used numbers in a box
     * @return {r, c} of the best empty cell, or {@code null} if the board is complete.
     */
    private int[] findBestCell(int[][] board, int[] rowUsed, int[] colUsed, int[] boxUsed) {
        int bestR = -1, bestC = -1, bestN = 10;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == 0) {
                    int used = rowUsed[r] | colUsed[c] | boxUsed[(r / 3) * 3 + (c / 3)];
                    int available = 0x1FF & ~used;
                    int n_available = Integer.bitCount(available);
                    if (n_available < bestN) {
                        bestR = r;
                        bestC = c;
                        bestN = n_available;
                    }
                }
            }
        }
        if (bestR == -1) return null;
        return new int[]{bestR, bestC};
    }

    /**
     * Computes the bitmask of possible candidates for a given cell.
     */
    private int availableMask(int r, int c, int[] rowUsed, int[] colUsed, int[] boxUsed) {
        int used = rowUsed[r] | colUsed[c] | boxUsed[(r / 3) * 3 + (c / 3)];
        return 0x1FF & ~used;
    }

    /**
     * Turns candidate bitmask into a shuffled list of digits 1-9.
     */
    private List<Integer> extractShuffledDigits(int mask) {
        List<Integer> candidates = new ArrayList<>();
        for (int d = 0; d < 9; d++) {
            // bit shift: 1 << d shifts the 1 bit to d pos
            // AND with mask to determine if bit available, then add that candidate
            if ((mask & (1 << d)) != 0) {
                candidates.add(d + 1);
            }
        }
        Collections.shuffle(candidates, this.random);
        return candidates;
    }

    /**
     * Places a digit on the board and marks it used in all three tracking masks.
     */
    private void place(int[][] board, int[] rowUsed, int[] colUsed, int[] boxUsed, int r, int c, int digit) {
        int bit = 1 << (digit - 1);
        // inject bit
        // Eg: 1011001(ORI) + 0000100(MASK) = 1011101(MOD)
        board[r][c] = digit;
        rowUsed[r] |= bit;
        colUsed[c] |= bit;
        boxUsed[(r / 3) * 3 + (c / 3)] |= bit;
    }

    /**
     * Removes a digit from the board and clears it from all three tracking masks (backtrack).
     */
    private void unplace(int[][] board, int[] rowUsed, int[] colUsed, int[] boxUsed, int r, int c, int digit) {
        int bit = 1 << (digit - 1);
        // AND with NOT to revert bit
        // 1011101(MOD) + 1111011(NOTMASK) = 1011001(ORI)
        board[r][c] = 0;
        rowUsed[r] &= ~bit;
        colUsed[c] &= ~bit;
        boxUsed[(r / 3) * 3 + (c / 3)] &= ~bit;
    }

    /**
     * Fills the board using randomized backtracking. Stops as soon as one complete solution is found.
     * @param board    Integer array for Sudoku board
     * @param rowUsed  Bits representing used numbers in a row
     * @param colUsed  Bits representing used numbers in a column
     * @param boxUsed  Bits representing used numbers in a box
     * @return {@code true} when the board has been filled.
     */
    private boolean fillBoard(int[][] board, int[] rowUsed, int[] colUsed, int[] boxUsed) {
        int[] cell = findBestCell(board, rowUsed, colUsed, boxUsed);
        if (cell == null) {
            return true; // board complete
        }
        int r = cell[0], c = cell[1];
        int mask = availableMask(r, c, rowUsed, colUsed, boxUsed);
        if (mask == 0) {
            return false; // dead end
        }

        for (int digit : extractShuffledDigits(mask)) {
            place(board, rowUsed, colUsed, boxUsed, r, c, digit);
            // try next recursion with placed
            if (fillBoard(board, rowUsed, colUsed, boxUsed)) {
                return true;
            }
            // NOOO FAILED
            unplace(board, rowUsed, colUsed, boxUsed, r, c, digit);
        }
        return false;
    }

    /**
     * Counts how many solutions the board has, stopping early once {@code limit} is reached.
     * @param board    Integer array for Sudoku board
     * @param rowUsed  Bits representing used numbers in a row
     * @param colUsed  Bits representing used numbers in a column
     * @param boxUsed  Bits representing used numbers in a box
     * @param limit    Stop searching once this many solutions have been found.
     * @return Number of solutions found (capped at {@code limit}).
     */
    private int countSolutions(int[][] board, int[] rowUsed, int[] colUsed, int[] boxUsed, int limit) {
        int[] cell = findBestCell(board, rowUsed, colUsed, boxUsed);
        if (cell == null) {
            return 1; // 1 solution
        }
        int r = cell[0], c = cell[1];
        int available = availableMask(r, c, rowUsed, colUsed, boxUsed);
        if (available == 0) {
            return 0; // no solution
        }

        int total = 0;
        for (int digit : extractShuffledDigits(available)) {
            place(board, rowUsed, colUsed, boxUsed, r, c, digit);
            // same with fill, test with placed
            total += countSolutions(board, rowUsed, colUsed, boxUsed, limit);
            unplace(board, rowUsed, colUsed, boxUsed, r, c, digit);
            if (total >= limit) {
                return total; // limit hit
            }
        }
        return total;
    }
}
