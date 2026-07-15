import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SudokuGenerator {
    private final Random random;

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
        removeCells(board, this.random.nextInt(diff.min, diff.max));
        return board;
    }

    /**
     * Returns a randomly generated sudoku board. Generated using randomized backtracking algorithm.
     */
    public int[][] generateCompleteBoard() {
        int[][] board = new int[9][9];

        fillBoard(board);

        return board;
    }

    public void removeCells(int[][] board, int count) {
        for (int i = 0; i < count; i++) {
            int randomR = this.random.nextInt(0,8);
            int randomC = this.random.nextInt(0,8);

            if (board[randomR][randomC] == 0) {
                i--;
                continue;
            }

            int val = board[randomR][randomC];
            unplace(board, randomR, randomC, val);

            int nSolutions = countSolutions(board, 2);
            if (nSolutions != 1) {
                place(board, randomR, randomC, val);
                i--;
            }

        }

    }

    // Helpers
    private final int[] rowUsed = new int[9];
    private final int[] colUsed = new int[9];
    private final int[] boxUsed = new int[9];


    /**
     * Finds the empty cell with the fewest possible candidates.
     * @param board    Integer array for Sudoku board
     * @return {r, c} of the best empty cell, or {@code null} if the board is complete.
     */
    private int[] findBestCell(int[][] board) {
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
    private int availableMask(int r, int c) {
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
    private void place(int[][] board, int r, int c, int digit) {
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
    private void unplace(int[][] board, int r, int c, int digit) {
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
     * @return {@code true} when the board has been filled.
     */
    private boolean fillBoard(int[][] board) {
        int[] cell = findBestCell(board);
        if (cell == null) {
            return true; // board complete
        }
        int r = cell[0], c = cell[1];
        int mask = availableMask(r, c);
        if (mask == 0) {
            return false; // dead end
        }

        for (int digit : extractShuffledDigits(mask)) {
            place(board, r, c, digit);
            // try next recursion with placed
            if (fillBoard(board)) {
                return true;
            }
            // NOOO FAILED
            unplace(board, r, c, digit);
        }
        return false;
    }

    /**
     * Counts how many solutions the board has, stopping early once {@code limit} is reached.
     * @param board    Integer array for Sudoku board
     * @param limit    As name suggests.
     * @return Number of solutions found (capped at {@code limit}).
     */
    private int countSolutions(int[][] board, int limit) {
        int[] cell = findBestCell(board);
        if (cell == null) {
            return 1; // 1 solution
        }
        int r = cell[0], c = cell[1];
        int available = availableMask(r, c);
        if (available == 0) {
            return 0; // no solution
        }

        int total = 0;
        for (int digit : extractShuffledDigits(available)) {
            place(board, r, c, digit);
            // same with fill, test with placed
            total += countSolutions(board, limit);
            unplace(board, r, c, digit);
            if (total >= limit) {
                return total; // limit hit
            }
        }
        return total;
    }
}
