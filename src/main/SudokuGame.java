package main;

public class SudokuGame {
    public static void main(String[] args) {
        SudokuBoard sudoku = new SudokuBoard();
        SudokuGenerator sudokuGenerator = new SudokuGenerator(1);
        sudoku.loadBoard(sudokuGenerator.generatePuzzle(SudokuGenerator.Difficulty.EASY));
        sudoku.displayBoard();
    }


};