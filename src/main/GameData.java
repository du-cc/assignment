package main;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

public class GameData {

    private SudokuGenerator generator;
    private long seed;
    private SudokuGenerator.Difficulty difficulty;
    private int moves;
    private boolean isRandomMode;
    private final ArrayList<String> inputData = new ArrayList<>();

    public long getSeed() {
        return this.seed;
    }

    public SudokuGenerator.Difficulty getDifficulty() {
        return this.difficulty;
    }

    public int getMoves() {
        return this.moves;
    }

    public boolean isRandomMode() {
        return isRandomMode;
    }

    public SudokuGenerator getGenerator() {
        return generator;
    }


    /**
     * Returns {@code True} or {@code False} depending on if the data has successfully loaded.
     *
     * @param dataEncoded - Base64 encoded {@code String} type variable that contains game data.
     *                    Format:
     *                    {@code random mode?|difficulty|seed|moves|values inputted by user}
     */
    public void importData(String dataEncoded) {
        String data;
        try {
            data = new String(Base64.getDecoder().decode(dataEncoded), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Base64: " + e.getMessage());
        }
        // regex check
        if (!data.matches("^(?:true|false)\\|[A-Za-z_]+\\|-?\\d+\\|\\d+(\\|\\d{3}(?:,\\d{3})*)?$")) {
            throw new IllegalArgumentException("Invalid data format");
//            return "FALSE|Invalid data format.";
        }

        String[] dataSplit = data.split("\\|");

        if (dataSplit.length > 5 || dataSplit.length < 4) {
            throw new IllegalArgumentException("Invalid data length");
//            return "FALSE|Invalid data length.";
        }

        // difficulty check
        boolean diffValid = false;
        for (int i = 0; i < SudokuGenerator.Difficulty.values().length; i++) {
            if (dataSplit[1].equals(SudokuGenerator.Difficulty.values()[i].name().toUpperCase())) {
                diffValid = true;
                break;
            }
        }
        if (!diffValid) throw new IllegalArgumentException("Invalid difficulty value");

        this.isRandomMode = Boolean.parseBoolean(dataSplit[0]);
        this.difficulty = SudokuGenerator.Difficulty.valueOf(dataSplit[1]);
        try {
            this.seed = Long.parseLong(dataSplit[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid seed value");
        }

        this.moves = Integer.parseInt(dataSplit[3]);

        if (dataSplit.length == 5) {
            String userInputs = dataSplit[4];
            String[] inputDataSplit = userInputs.split(",");
            // store into inputdata array
            this.inputData.addAll(Arrays.asList(inputDataSplit));
        }
    }


    public SudokuBoard loadBoard() {
        // generate a same board using seed
        SudokuBoard sudoku = new SudokuBoard();
        SudokuGenerator generator = new SudokuGenerator(this.seed);
        sudoku.loadBoard(generator.generatePuzzle(this.difficulty));
        this.generator = generator;
        // place all values
        for (String data : this.inputData) {
            String[] dataSplit = data.split("");
            sudoku.setValue(Integer.parseInt(dataSplit[0]), Integer.parseInt(dataSplit[1]), Integer.parseInt(dataSplit[2]));
        }

        // return board
        return sudoku;
    }


    public static String exportData(SudokuGame game) {
        SudokuBoard sudoku = game.getSudoku();
        int[][] board = GameUtils.copyBoard(sudoku.getBoard());

        String dataString;
        ArrayList<String> inputData = new ArrayList<>();

        // add entries (userinput data)
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudoku.isCellEditable(row, col)) {
                    int val = board[row][col];
                    if (val != 0) {
                        inputData.add(row + Integer.toString(col) + val);
                    }
                }
            }
        }

        // parse
        StringBuilder inputDataStr = new StringBuilder();
        for (String data : inputData) {
            inputDataStr.append(data).append(",");
        }

        dataString = game.isRandomMode() + "|" + game.getDifficulty().name() + "|" + game.getGenerator().getSeed() + "|" + game.getMoves();
        // if contains user input
        if (!inputDataStr.isEmpty()) {
            inputDataStr.deleteCharAt(inputDataStr.length() - 1);
            dataString += "|" + inputDataStr;
        }

        return Base64.getEncoder().encodeToString(dataString.getBytes(StandardCharsets.UTF_8)).replace("=", "");
    }
}

