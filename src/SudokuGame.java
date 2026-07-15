public class SudokuGame {
    public static void main(String[] args) {
        SudokuBoard sudoku = new SudokuBoard();
        SudokuGenerator sudokuGenerator = new SudokuGenerator(GameUtils.generateNewSeed());
        sudoku.loadBoard(sudokuGenerator.generatePuzzle(SudokuGenerator.Difficulty.HARD));
        sudoku.displayBoard();
        SudokuBoard lol = new SudokuBoard();
        SudokuGenerator loll = new SudokuGenerator(GameUtils.generateNewSeed());
        lol.loadBoard(loll.generatePuzzle(SudokuGenerator.Difficulty.HARD));
        lol.displayBoard();
    }


};