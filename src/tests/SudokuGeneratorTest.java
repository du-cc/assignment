package tests;

import main.SudokuBoard;
import main.SudokuGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static com.github.stefanbirkner.systemlambda.SystemLambda.*;

class SudokuGeneratorTest {

    SudokuBoard board;
    SudokuGenerator generator;
    int[][] puzzle;

    @BeforeEach
    void init() {
        board = new SudokuBoard();
        generator = new SudokuGenerator(1);
        puzzle = generator.generatePuzzle(SudokuGenerator.Difficulty.EASY);
        board.loadBoard(puzzle);
    }

    @Test
    void test_changeEditableCell() throws Exception {
        board.setValue(0, 2, 1);
        String result = tapSystemOut(() -> {
            board.displayBoard();
        });
        assertEquals("\u001B[0;37m+-------+-------+-------+\u001B[0m\n" +
                "\u001B[0;37m| \u001B[0m6 3 1 \u001B[0;37m| \u001B[0m4 . . \u001B[0;37m| \u001B[0m. 5 . \u001B[0;37m|\u001B[0m\n" +
                "\u001B[0;37m| \u001B[0m. 4 5 \u001B[0;37m| \u001B[0m. 6 2 \u001B[0;37m| \u001B[0m. 3 1 \u001B[0;37m|\u001B[0m\n" +
                "\u001B[0;37m| \u001B[0m. 8 . \u001B[0;37m| \u001B[0m. . 3 \u001B[0;37m| \u001B[0m. . 6 \u001B[0;37m|\u001B[0m\n" +
                "\u001B[0;37m+-------+-------+-------+\u001B[0m\n" +
                "\u001B[0;37m| \u001B[0m5 6 3 \u001B[0;37m| \u001B[0m. 1 . \u001B[0;37m| \u001B[0m2 9 . \u001B[0;37m|\u001B[0m\n" +
                "\u001B[0;37m| \u001B[0m2 1 . \u001B[0;37m| \u001B[0m3 . 9 \u001B[0;37m| \u001B[0m. . 5 \u001B[0;37m|\u001B[0m\n" +
                "\u001B[0;37m| \u001B[0m4 7 9 \u001B[0;37m| \u001B[0m. . . \u001B[0;37m| \u001B[0m. . . \u001B[0;37m|\u001B[0m\n" +
                "\u001B[0;37m+-------+-------+-------+\u001B[0m\n" +
                "\u001B[0;37m| \u001B[0m7 . . \u001B[0;37m| \u001B[0m6 8 5 \u001B[0;37m| \u001B[0m3 4 . \u001B[0;37m|\u001B[0m\n" +
                "\u001B[0;37m| \u001B[0m8 2 . \u001B[0;37m| \u001B[0m. . 4 \u001B[0;37m| \u001B[0m5 1 . \u001B[0;37m|\u001B[0m\n" +
                "\u001B[0;37m| \u001B[0m. 5 . \u001B[0;37m| \u001B[0m1 . . \u001B[0;37m| \u001B[0m8 6 9 \u001B[0;37m|\u001B[0m\n" +
                "\u001B[0;37m+-------+-------+-------+\u001B[0m\n", result);
    }

    @Test
    void test_changeUneditableCell() {
        assertThrows(IllegalArgumentException.class, () -> {
            board.setValue(0, 0, 1);
        });
    }
}
