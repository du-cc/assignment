package main;

import java.util.*;

public class SudokuGenerator {
    private final Random random;
    private int[][] board;
    private long seed;
    private int numPrefilled;

    public int getNumPrefilled() {
        return numPrefilled;
    }

    public SudokuGenerator() {
        this.random = new Random();
    }

    public SudokuGenerator(long seed) {
        this.random = new Random(seed);
        this.seed = seed;
    }

    public long getSeed() {
        return seed;
    }

    public enum Difficulty {
        EASY(40, 45),
        MEDIUM(28, 35),
        HARD(20, 25),

        // DEBUGGING PURPOSES ONLY
        FILLED(81, 81),
        ONE_TO_FILLED(80, 80),
        EMPTY(0, 0);

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

        @Override
        public String toString() {
            return this.name();
        }
    }

    public int[][] generatePuzzle(Difficulty diff) {
        generateCompleteBoard();

        // DEBUG
        if (diff.equals(Difficulty.EMPTY)) {
            board = GameUtils.generateEmptyBoard();
            return board;
        }
        if (diff.equals(Difficulty.FILLED) || diff.equals(Difficulty.ONE_TO_FILLED)) {
            removeCells(diff.getMax());
            return board;
        }

        removeCells(this.random.nextInt(diff.getMin(), diff.getMax()));
        this.numPrefilled = filledCells.size();
        return board;
    }

    /**
     * Returns a randomly generated sudoku board. Generated using randomized backtracking algorithm.
     */
    public void generateCompleteBoard() {
        // I hate u mr bug
        reset();
        board = GameUtils.generateEmptyBoard();
        fillBoard();
    }

    public void removeCells(int count) {
        int toRemove = 81 - count;
        List<Integer> candidates = new ArrayList<>(filledCells); // clone to shuffle and not link
        Collections.shuffle(candidates, this.random);

        int removed = 0;
        for (int idx : candidates) {
            if (removed >= toRemove) break;

            int r = idx / 9, c = idx % 9;
            int val = board[r][c];
            if (val == 0) continue;

            unplace(r, c, val);
            int nSolutions = countSolutions(2);
            if (nSolutions == 1) {
                removed++;
            } else {
                place(r, c, val); // revert
            }
        }
    }

    // Helpers
    private final int[] rowUsed = new int[9];
    private final int[] colUsed = new int[9];
    private final int[] boxUsed = new int[9];
    // note hashset cuz its object based, not like list all shit index
    private final HashSet<Integer> emptyCells = new HashSet<>();
    private final HashSet<Integer> filledCells = new HashSet<>();

    /**
     * Clear all for init
     */
    private void reset() {
        Arrays.fill(rowUsed, 0);
        Arrays.fill(colUsed, 0);
        Arrays.fill(boxUsed, 0);
        emptyCells.clear();
        filledCells.clear();
        for (int i = 0; i < 81; i++) emptyCells.add(i);
    }

    /**
     * Finds the empty cell with the fewest possible candidates.
     * @return {r, c} of the best empty cell, or {@code null} if the board is complete.
     */
    private int[] findBestCell() {
        int bestR = -1, bestC = -1, bestN = 10;
        for (int cell : emptyCells) {
            int r = cell / 9, c = cell % 9;
            int used = rowUsed[r] | colUsed[c] | boxUsed[(r / 3) * 3 + (c / 3)];
            int available = 0x1FF & ~used;
            int n_available = Integer.bitCount(available);
            if (n_available < bestN) {
                bestR = r;
                bestC = c;
                bestN = n_available;
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
            // AND with mask to determine if a bit available, then add that candidate
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
    private void place(int r, int c, int digit) {
        int bit = 1 << (digit - 1);
        // inject bit
        // Eg: 1011001(ORI) + 0000100(MASK) = 1011101(MOD)
        board[r][c] = digit;
        rowUsed[r] |= bit;
        colUsed[c] |= bit;
        boxUsed[(r / 3) * 3 + (c / 3)] |= bit;
        emptyCells.remove(r * 9 + c);
        filledCells.add(r * 9 + c);
    }

    /**
     * Removes a digit from the board and clears it from all three tracking masks (backtrack).
     */
    private void unplace(int r, int c, int digit) {
        int bit = 1 << (digit - 1);
        // AND with NOT to revert bit
        // 1011101(MOD) + 1111011(NOT MASK) = 1011001(ORI)
        board[r][c] = 0;
        rowUsed[r] &= ~bit;
        colUsed[c] &= ~bit;
        boxUsed[(r / 3) * 3 + (c / 3)] &= ~bit;
        emptyCells.add(r * 9 + c);
        filledCells.remove(r * 9 + c);
    }

    /**
     * Fills the board using randomized backtracking. Stops as soon as one complete solution is found.
     * @return {@code true} when the board has been filled.
     */
    private boolean fillBoard() {
        int[] cell = findBestCell();
        if (cell == null) {
            return true; // board complete
        }
        int r = cell[0], c = cell[1];
        int mask = availableMask(r, c);
        if (mask == 0) {
            return false; // dead end
        }

        for (int digit : extractShuffledDigits(mask)) {
            place(r, c, digit);
            // try next recursion with placed
            if (fillBoard()) {
                return true;
            }
            // NOOO FAILED
            unplace(r, c, digit);
        }
        return false;
    }

    /**
     * Counts how many solutions the board has, stopping early once {@code limit} is reached.
     * @param limit    As name suggests.
     * @return Number of solutions found (capped at {@code limit}).
     */
    private int countSolutions(int limit) {
        int[] cell = findBestCell();
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
            place(r, c, digit);
            // same with fill, test with placed
            total += countSolutions(limit);
            unplace(r, c, digit);
            if (total >= limit) {
                return total; // limit hit
            }
        }
        return total;
    }
}