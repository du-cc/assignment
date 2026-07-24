package main;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

public class GameData {

    private long seed;
    private SudokuGenerator.Difficulty difficulty;
    private int moves;
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

    /**
     * Returns {@code True} or {@code False} depending on if the data has successfully loaded.
     *
     * @param dataEncoded - Base64 encoded {@code String} type variable that contains game data.
     *                    Format:
     *                    {@code seed|difficulty|moves|values inputted by user}
     */
    public String importData(String dataEncoded) {
        String data;
        try {
            data = new String(Base64.getDecoder().decode(dataEncoded), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            return "FALSE|Failed to decode data.";
        }
        // regex check
        if (!data.matches("^-?\\d+(\\|[A-Za-z_]+(\\|\\d+)?(\\|\\d{3}(?:,\\d{3})*)?)?$")) {
            return "FALSE|Invalid string format.";
        }

        String[] dataSplit = data.split("\\|");

        if (dataSplit.length > 4 || dataSplit.length < 2) {
            return "FALSE|Invalid data length.";
        }

        // difficulty check
        boolean diffValid = false;
        for (int i = 0; i < SudokuGenerator.Difficulty.values().length; i++) {
            if (dataSplit[1].equals(SudokuGenerator.Difficulty.values()[i].name().toUpperCase())) {
                diffValid = true;
                break;
            }
        }
        if (!diffValid) return "FALSE|Invalid difficulty value.";

        this.seed = Long.parseLong(dataSplit[0]);
        this.difficulty = SudokuGenerator.Difficulty.valueOf(dataSplit[1]);
        this.moves = 0;

        if (dataSplit.length >= 3) {
            this.moves = Integer.parseInt(dataSplit[2]);
        }

        if (dataSplit.length >= 4) {
            String userInputs = dataSplit[3];
            String[] inputDataSplit = userInputs.split(",");
            // store into inputdata array
            this.inputData.addAll(Arrays.asList(inputDataSplit));
        }


        return "TRUE";
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


    public String exportData(SudokuBoard sudoku, SudokuGenerator generator, SudokuGenerator.Difficulty difficulty, int moves) {
        this.seed = generator.getSeed();
        this.difficulty = difficulty;

        int[][] board = GameUtils.copyBoard(sudoku.getBoard());

        String dataString;

        // add entries (userinput data)
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudoku.isCellEditable(row, col)) {
                    int val = board[row][col];
                    if (val != 0) {
                        this.inputData.add(row + Integer.toString(col) + val);
                    }
                }
            }
        }

        // parse
        StringBuilder inputDataStr = new StringBuilder();
        for (String data : inputData) {
            inputDataStr.append(data).append(",");
        }

        dataString = this.seed + "|" + difficulty.name() + "|" + moves;
        // if contains user input
        if (!inputDataStr.isEmpty()) {
            inputDataStr.deleteCharAt(inputDataStr.length() - 1);
            dataString += "|" + inputDataStr;
        }

        return Base64.getEncoder().encodeToString(dataString.getBytes(StandardCharsets.UTF_8)).replace("=", "");
    }
}

