package main;

import java.util.Objects;
import java.util.Scanner;

public class SudokuGame {
    //  TODO: TURN IT OFF IN PROD
    private final static boolean DEBUG = true;
    private final static int INPUT_BORDER_WIDTH = 80;

    private final static String HEADER = (ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "_" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "_" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.RESET + "\n" +
            ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + "_" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "_" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.RESET + "\n" +
            ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "_" + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + "|" + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + "." + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + "." + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + "'" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "|" + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.RESET + "\n" +
            ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "_" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "_" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "_" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "|" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "_" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "_" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "," + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "|" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "_" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.RESET + "\n" +
            ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RESET
    );

    private final Scanner scanner = new Scanner(System.in);
    private SudokuBoard sudoku;
    private SudokuGenerator generator;
    private long seed;
    private SudokuGenerator.Difficulty difficulty;
    private final int[] highlightPos = {-1, -1};
    private int moves = 0;
    private boolean isRandomMode = false;

    public SudokuBoard getSudoku() {
        return sudoku;
    }

    public SudokuGenerator getGenerator() {
        return generator;
    }

    public SudokuGenerator.Difficulty getDifficulty() {
        return difficulty;
    }

    public int getMoves() {
        return moves;
    }

    public boolean isRandomMode() {
        return isRandomMode;
    }

    //  main runner
    public static void main(String[] args) {
        new SudokuGame().selectMode();
    }

    //  start/load/exit
    public void selectMode() {
        boolean inputIsValid = true;

        while (true) {
            clearConsole();

            System.out.println(HEADER);
            if (sudoku != null)
                System.out.println(ConsoleColors.colorize("0.", ConsoleColors.CYAN_BOLD) + " Continue game");
            System.out.println(ConsoleColors.colorize("1.", ConsoleColors.BLUE_BOLD) + " Start Game (Random)");
            System.out.println(ConsoleColors.colorize("2.", ConsoleColors.BLUE_BOLD) + " Start Game (Predefined)");
            System.out.println(ConsoleColors.colorize("3.", ConsoleColors.BLUE_BOLD) + " Load Game");
            System.out.println(ConsoleColors.colorize("4.", ConsoleColors.BLUE_BOLD) + " " + ConsoleColors.colorize("Exit", ConsoleColors.WHITE));

            String input = prompt(inputIsValid).toLowerCase();

            System.out.println(ConsoleColors.colorize("─".repeat(INPUT_BORDER_WIDTH), inputIsValid ? ConsoleColors.BLACK_BRIGHT : ConsoleColors.RED));

            switch (input) {
                case "0" -> {
                    if (sudoku != null) {
                        gameLoop();
                    } else {
                        inputIsValid = false;
                    }
                }
                case "1", "2" -> {
                    this.isRandomMode = input.equals("1");
                    selectDifficulty();
                    return;
                }
                case "3" -> {
                    loadGame();
                    return;
                }
                case "4" -> {
                    System.out.println("Thanks for playing!");
                    System.exit(0);
                    return;
                }
                default -> inputIsValid = false;
            }

        }
    }

    //  1. start game
    public void selectDifficulty() {
        boolean inputIsValid = true;

        while (true) {
            clearConsole();

            System.out.println(HEADER);
            System.out.println(ConsoleColors.colorize("Select Difficulty", ConsoleColors.CYAN_UNDERLINED));
            System.out.println(ConsoleColors.colorize("1.", ConsoleColors.BLUE_BOLD) + " " + ConsoleColors.colorize("Easy", ConsoleColors.GREEN_BOLD_BRIGHT));
            System.out.println(ConsoleColors.colorize("2.", ConsoleColors.BLUE_BOLD) + " " + ConsoleColors.colorize("Medium", ConsoleColors.YELLOW_BOLD_BRIGHT));
            System.out.println(ConsoleColors.colorize("3.", ConsoleColors.BLUE_BOLD) + " " + ConsoleColors.colorize("Hard", ConsoleColors.RED_BOLD_BRIGHT));
            System.out.println(ConsoleColors.colorize("4.", ConsoleColors.BLUE_BOLD) + " " + ConsoleColors.colorize("Back", ConsoleColors.WHITE));
            // DEBUG
            if (DEBUG) {
                System.out.println(ConsoleColors.colorize("DEBUG", ConsoleColors.YELLOW_UNDERLINED));
                System.out.println(ConsoleColors.colorize("5.", ConsoleColors.YELLOW_BOLD) + " " + ConsoleColors.colorize("Filled", ConsoleColors.WHITE));
                System.out.println(ConsoleColors.colorize("6.", ConsoleColors.YELLOW_BOLD) + " " + ConsoleColors.colorize("One more to filled", ConsoleColors.WHITE));
                System.out.println(ConsoleColors.colorize("7.", ConsoleColors.YELLOW_BOLD) + " " + ConsoleColors.colorize("Empty", ConsoleColors.WHITE));
            }

            String inputStr = prompt(inputIsValid).toLowerCase();

            try {
                int input = Integer.parseInt(inputStr);
                System.out.println(ConsoleColors.colorize("─".repeat(INPUT_BORDER_WIDTH), inputIsValid ? ConsoleColors.BLACK_BRIGHT : ConsoleColors.RED));

                if (input >= 1 && input <= 3) {
                    startGame(SudokuGenerator.Difficulty.values()[input - 1]);
                    return;
                }
                if (input == 4) {
                    selectMode();
                    return;
                }

                // DEBUG
                if (input >= 5 && input <= 7 && DEBUG) {
                    startGame(SudokuGenerator.Difficulty.values()[input - 2]);
                    return;
                }
            } catch (NumberFormatException e) {
            }
            inputIsValid = false;
        }
    }

    // 2. load game
    public void loadGame() {
        String message = null;

        while (true) {
            clearConsole();
            System.out.println(HEADER);
            System.out.println(ConsoleColors.colorize("Load game", ConsoleColors.YELLOW) + "\n");

            ConsoleColors messageColor = message != null ? ConsoleColors.RED : null;

            String input = prompt(true, "Enter your save string (m to return): ", message, messageColor);

            if (input.equals("m")) {
                selectMode();
                return;
            }

            GameData saveData = new GameData();

            try {
                saveData.importData(input);
            } catch (IllegalArgumentException e) {
                message = e.getMessage();
                continue;
            }

            try {
                startGame(saveData);
            } catch (Exception e) {
                message = "Error while loading board: " + e.getMessage();
                continue;
            }
            return;
        }
    }


    // new game
    public void startGame(SudokuGenerator.Difficulty difficulty) {
        if (isRandomMode) {
            this.seed = GameUtils.generateNewSeed();
        } else {
            this.seed = 69420;
        }
        this.moves = 0;
        this.generator = new SudokuGenerator(seed);
        this.sudoku = new SudokuBoard();
        this.sudoku.loadBoard(generator.generatePuzzle(difficulty));
        this.difficulty = difficulty;

        gameLoop();
    }

    // load game
    public void startGame(GameData saveData) {
        this.isRandomMode = saveData.isRandomMode();
        this.sudoku = saveData.loadBoard();
        this.seed = saveData.getSeed();
        this.difficulty = saveData.getDifficulty();
        this.moves = saveData.getMoves();
        this.generator = saveData.getGenerator();

        gameLoop();
    }

    private void gameLoop() {
        String pendingMessage = null;
        ConsoleColors pendingColor = null;

        while (true) {
            clearConsole();
            renderBoard();

            // default red err msg color
            ConsoleColors promptColor = pendingMessage != null && pendingColor == null
                    ? ConsoleColors.RED
                    : pendingColor;

            String input = prompt(true, "> ", pendingMessage, promptColor).toLowerCase();

            pendingMessage = null;
            pendingColor = null;

            switch (input) {
                case "cmds" -> {
                    clearConsole();

                    // help render
                    String[][] entries = {
                            {"input/i " + ConsoleColors.colorize("<row><column><value>", ConsoleColors.YELLOW), "Inputs value into the board. (eg: i123)"},
                            {"check/c", "Checks if the board is completed."},
                            {"export/e", "Exports data of this board into a string."},
                            {"refresh/r", "Refreshes the board with another random seed."},
                            {"reset/t", "Resets the board to originally prefilled."},
                            {"menu/m", "Returns to main menu."},
                            {"quit/q", "Quits this game."},
                            {"cmds", "Shows this list of commands."},
                    };

                    StringBuilder sb = new StringBuilder();
                    sb.append(ConsoleColors.CYAN_UNDERLINED).append("Commands\n\n").append(ConsoleColors.RESET);

                    for (int i = 0; i < entries.length; i++) {
                        boolean isFirst = i == 0;
                        boolean isLast = i == entries.length - 1;

                        sb.append(isFirst ? "╭ " : "├ ").append(entries[i][0]);
                        sb.append(ConsoleColors.colorize("\n" + (isLast ? "╰" : "├") + " " + entries[i][1], ConsoleColors.WHITE));

                        if (!isLast) {
                            sb.append(ConsoleColors.colorize("\n┆", ConsoleColors.BLACK_BRIGHT)).append("\n");
                        }
                    }
                    System.out.println(sb);


                    System.out.println("\n" + ConsoleColors.colorize("press ENTER to continue", ConsoleColors.BLACK_BRIGHT));
                    Scanner enterScanner = new Scanner(System.in);
                    enterScanner.nextLine();
                }
                case "menu", "m" -> {
                    boolean confirm = promptConfirmation(ConsoleColors.colorize("Don't forget to export first!", ConsoleColors.RED_BOLD_BRIGHT) + "\n Are you sure you want to return to menu? (y/N): ");
                    if (confirm) selectMode();
                }
                case "quit", "q" -> {
                    boolean confirm = promptConfirmation(ConsoleColors.colorize("Don't forget to export first!", ConsoleColors.RED_BOLD_BRIGHT) + "\n Are you sure you want to quit? (y/N): ");
                    if (confirm) System.exit(0);
                }
                case "refresh", "r" -> startGame(difficulty); // recurses; does not return
                case "reset", "t" -> sudoku.resetBoard();
                case "check", "c" -> {
                    boolean complete = GameUtils.isBoardComplete(sudoku.getBoard());
                    pendingMessage = complete ? "Board completed! :D" : "Board isn't completed yet. :(";
                    pendingColor = complete ? ConsoleColors.GREEN : ConsoleColors.YELLOW;
                }
                case "export", "e" -> {
                    GameData saveData = new GameData();
                    String dataString = saveData.exportData(this);
                    pendingMessage = "Your save string (copy and save them in a notepad)\n" + dataString;
                    pendingColor = ConsoleColors.GREEN;
                }
                default -> {
                    if (input.contains("input") || input.contains("i")) {
                        pendingMessage = inputHandler(input);
                        // special case for board complete
                        if (Objects.equals(pendingMessage, "board_complete")) {
                            pendingMessage = "You have completed this board!! :D";
                            pendingColor = ConsoleColors.GREEN;
                        }
                    }
                }
            }
        }
    }

    // for i/input cmds only
    private String inputHandler(String input) {
        // arg split
        String[] args = input.split("");
        // basic pass validation
        // arg count
        if (args.length != 4) {
            return "Numbers of arguments must equal 3.";
        }

        // integer check
        for (int i = 1; i < args.length; i++) {
            if (!args[i].matches("^\\d+$")) {
                return "Arguments must be an integer.";
            }
        }

        // secondary check before passing to class
        int row = Integer.parseInt(args[1]) - 1;
        int col = Integer.parseInt(args[2]) - 1;
        int val = Integer.parseInt(args[3]);

        // in range check
        for (int i = 1; i < args.length; i++) {
            if (i == 3 && args[i].equals("0")) continue; // clear cell
            if (!GameUtils.isValidInput(Integer.parseInt(args[i]))) {
                return "Arguments must be in range of 1-9";
            }
        }

        boolean inputValid = true;
        String message = null;

        // advance class check
        // move check
        if (!SudokuValidator.isValidMove(sudoku.getBoard(), row, col, val)) {
            inputValid = false;
            message = "Invalid move!";
        }

        // for remove cell (val=0), edge case input = value in cell
        if (!inputValid && val == 0 || val == sudoku.getBoard()[row][col]) {
            inputValid = true;
            message = null;
        }

        // editable check
        if (!sudoku.isCellEditable(row, col)) {
            inputValid = false;
            message = "Cell is not editable.";
        }

        // FAIL
        if (!inputValid) {
            return message;
        }

        // PASS
        sudoku.setValue(row, col, val);
        highlightPos[0] = row;
        highlightPos[1] = col;
        moves += 1;

        // complete check
        if (GameUtils.isBoardComplete(sudoku.getBoard())) {
            return "board_complete";
        }
        return null;
    }

    // interface rendering (frontend)
    private void renderBoard() {
        // difficulty color
        ConsoleColors diffColor = switch (difficulty) {
            case EASY -> ConsoleColors.GREEN_BOLD_BRIGHT;
            case MEDIUM -> ConsoleColors.YELLOW_BOLD_BRIGHT;
            case HARD -> ConsoleColors.RED_BOLD_BRIGHT;
            default -> ConsoleColors.BLUE_BOLD_BRIGHT;
        };

        String left = HEADER + "\n" +
                ConsoleColors.colorize(" Difficulty: ", ConsoleColors.WHITE) + ConsoleColors.colorize(difficulty + (this.isRandomMode ? "" : " (Predefined)"), diffColor) + "\n" +
                ConsoleColors.colorize(" Seed: ", ConsoleColors.WHITE) + seed + "\n\n" +
                ConsoleColors.colorize(" Moves: ", ConsoleColors.WHITE) + moves + "\n" +
                ConsoleColors.colorize(" Filled: ", ConsoleColors.WHITE) + (sudoku.getFilled() + generator.getNumPrefilled()) + ConsoleColors.colorize("/" + 81, ConsoleColors.BLACK_BRIGHT) + "\n\n\n" +
                " cmds " + ConsoleColors.colorize("for list of commands.", ConsoleColors.WHITE);

        if (highlightPos[0] != -1 && highlightPos[1] != -1) {
            // last move (last input cell)
            printSideBySide(left, sudoku.toString(false, true, highlightPos));
            highlightPos[0] = -1;
            highlightPos[1] = -1;
        } else {
            printSideBySide(left, sudoku.toString(false, true));
        }
    }


    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private String prompt(boolean inputWasValid) {
        return prompt(inputWasValid, "Select an option: ", null, null);
    }

    private String prompt(boolean inputWasValid, String promptText) {
        return prompt(inputWasValid, promptText, null, null);
    }

    private String prompt(boolean inputWasValid, String promptText, String message, ConsoleColors messageColor) {
        ConsoleColors borderColor;
        // for color
        if (message != null) {
            borderColor = messageColor;
        } else {
            borderColor = inputWasValid ? ConsoleColors.BLACK_BRIGHT : ConsoleColors.RED;
        }
        System.out.print(borderColor + "─".repeat(INPUT_BORDER_WIDTH) + ConsoleColors.RESET + "\n");

        // for invalid
        if (!inputWasValid) {
            System.out.println(" " + ConsoleColors.colorize("Invalid input!", ConsoleColors.RED_BRIGHT));
        }
        // message color
        if (message != null) {
            ConsoleColors brightColor = ConsoleColors.valueOf(messageColor.name() + "_BRIGHT");
            System.out.println(" " + ConsoleColors.colorize(message, brightColor));
        }

        System.out.print(" " + ConsoleColors.colorize(promptText, ConsoleColors.WHITE));

        return scanner.nextLine().trim();
    }

    private boolean promptConfirmation(String prompt) {
        boolean isInputValid = true;
        while (true) {
            String input = prompt(isInputValid, prompt != null ? prompt : "Are you sure? (y/N): ").toLowerCase();
            switch (input) {
                case "y", "yes" -> {
                    return true;
                }
                case "n", "no", "" -> {
                    return false;
                }
                default -> {
                }
            }
        }
    }


    private void printSideBySide(String left, String right) {
        String[] leftLines = left.split("\n");
        String[] rightLines = right.split("\n");
        int printLines = Math.max(leftLines.length, rightLines.length);

        int leftWidth = 0;
        for (String line : leftLines) {
            leftWidth = Math.max(leftWidth, stripAnsi(line).length());
        }

        StringBuilder out = new StringBuilder();
        for (int i = 0; i < printLines; i++) {
            String l = i < leftLines.length ? leftLines[i] : "";
            String r = i < rightLines.length ? rightLines[i] : "";

            int padding = leftWidth - stripAnsi(l).length();

            out.append(l).repeat(" ", Math.max(padding, 0)).repeat("   ", 5) // gap between the two columns
                    .append(r)
                    .append("\n");
        }
        System.out.print(out);
    }

    private String stripAnsi(String input) {
        return input.replaceAll("\u001B\\[[;\\d]*m", "");
    }
}