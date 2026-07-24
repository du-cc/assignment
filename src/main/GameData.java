package main;


import java.util.ArrayList;
import java.util.Arrays;

public class GameData {

    private long seed;
    private SudokuGenerator.Difficulty difficulty;
    private String userInputs;
    private ArrayList<String> inputData = new ArrayList<>();

    public long getSeed() {
        return this.seed;
    }

    public SudokuGenerator.Difficulty getDifficulty() {
        return this.difficulty;
    }

    /**
     * Returns {@code True} or {@code False} depending on if the data has successfully loaded.
     *
     * @param data - {@code String} type variable that contains game data.
     *             Format:
     *             {@code seed|difficulty|values inputted by user}
     */
    public boolean importData(String data) {

        // regex check
        // ^-?\d+(\|[A-Za-z]+(\|\d{3}(?:,\d{3})*)?)?$
        if (!data.matches("^-?\\d+(\\|[A-Za-z]+(\\|\\d{3}(?:,\\d{3})*)?)?$")) {
            return false;
        }

        String[] dataSplit = data.split("\\|");

        this.seed = Long.parseLong(dataSplit[0]);
        if (dataSplit.length >= 2) {
            this.difficulty = SudokuGenerator.Difficulty.valueOf(dataSplit[1]);
        }
        if (dataSplit.length >= 3) {
            this.userInputs = dataSplit[2];
            String[] inputDataSplit = userInputs.split(",");
            // store into inputdata array
            this.inputData.addAll(Arrays.asList(inputDataSplit));
        }
        if (dataSplit.length > 3 || dataSplit.length == 1) {
            return false;
        }

        return true;
    }


    public SudokuBoard loadBoard() {
        // generate a same board using seed
        SudokuBoard sudoku = new SudokuBoard();
        SudokuGenerator generator = new SudokuGenerator(this.seed);
        sudoku.loadBoard(generator.generatePuzzle(this.difficulty));

        // place all values
        for (String data : this.inputData) {
            String[] dataSplit = data.split("");
            sudoku.setValue(Integer.parseInt(dataSplit[0]), Integer.parseInt(dataSplit[1]), Integer.parseInt(dataSplit[2]));
        }

        // return board
        return sudoku;
    }



    public String exportData(SudokuBoard sudoku, SudokuGenerator generator, SudokuGenerator.Difficulty difficulty) {
        this.seed = generator.getSeed();
        this.difficulty = difficulty;

        int[][] board = GameUtils.copyBoard(sudoku.getBoard());
        int[][] originalBoard = GameUtils.copyBoard(sudoku.getOriginalBoard());

        String dataString;

        // add entries (userinput data)
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudoku.isCellEditable(row, col)) {
                    int val = board[row][col];
                    if (val != 0 ) {
                        this.inputData.add(Integer.toString(row) + Integer.toString(col) + Integer.toString(val));
                    }
                }
            }
        }

        // parse
        StringBuilder inputDataStr = new StringBuilder();
        for (String data : inputData) {
            inputDataStr.append(data).append(",");
        }

        dataString = Long.toString(this.seed) + "|" + difficulty.name();
                // if contains user input
        if(!inputDataStr.isEmpty()) {
            inputDataStr.deleteCharAt(inputDataStr.length() - 1);
            dataString += "|" + inputDataStr.toString();
        }


        return dataString;
    }
}

