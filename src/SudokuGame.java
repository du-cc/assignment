public class SudokuGame {
    public static void main(String[] args) {
        SudokuBoard sudoku = new SudokuBoard();
        SudokuGenerator sudokuGenerator = new SudokuGenerator(GameUtils.generateNewSeed());
        sudoku.loadBoard(sudokuGenerator.generateCompleteBoard());
        sudoku.displayBoard();

    }


};