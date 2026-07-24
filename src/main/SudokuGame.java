package main;

import java.util.Scanner;

public class SudokuGame {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final int DIVIDER_WIDTH = 80;

    private static final String HEADER = (ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "_" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "_" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.RESET + "\n" +
            ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + "_" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "_" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.RESET + "\n" +
            ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "_" + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + "|" + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + "." + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + "." + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + "'" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "|" + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.RESET + "\n" +
            ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "_" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "_" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "_" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "|" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "_" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "_" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "," + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "|" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "_" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.RESET + "\n" +
            ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RESET
    );


    public static void main(String[] args) {
        selectMode();
    }


    public static void selectMode() {
        boolean inputIsValid = true;

        while (true) {
            clearConsole();

            System.out.println(HEADER);
            System.out.println(ConsoleColors.colorize("1.", ConsoleColors.BLUE_BOLD) + " Start Game");
            System.out.println(ConsoleColors.colorize("2.", ConsoleColors.BLUE_BOLD) + " Load Game");
            System.out.println(ConsoleColors.colorize("3.", ConsoleColors.BLUE_BOLD) + " " + ConsoleColors.colorize("Exit", ConsoleColors.WHITE));

            Scanner inputScanner = prompt(inputIsValid);
            String line = inputScanner.nextLine();

            try {
                int input = Integer.parseInt(line.trim());
                System.out.println(ConsoleColors.colorize("─".repeat(DIVIDER_WIDTH), inputIsValid ? ConsoleColors.BLACK_BRIGHT : ConsoleColors.RED));

                switch (input) {
                    case 1 -> {
                        selectDifficulty();
                        return;
                    }
                    case 2 -> {
                        loadGame();
                        return;
                    }
                    case 3 -> {
                        System.out.println("Thanks for playing!");
                        System.exit(0);
                        return;
                    }
                    default -> inputIsValid = false;
                }
            } catch (NumberFormatException e) {
                inputIsValid = false;
            }
        }
    }

    public static void selectDifficulty() {
        boolean inputIsValid = true;

        while (true) {
            clearConsole();

            System.out.println(HEADER);
            System.out.println(ConsoleColors.colorize("Select Difficulty", ConsoleColors.CYAN_UNDERLINED));
            System.out.println(ConsoleColors.colorize("1.", ConsoleColors.BLUE_BOLD) + " " + ConsoleColors.colorize("Easy", ConsoleColors.GREEN_BOLD_BRIGHT));
            System.out.println(ConsoleColors.colorize("2.", ConsoleColors.BLUE_BOLD) + " " + ConsoleColors.colorize("Medium", ConsoleColors.YELLOW_BOLD_BRIGHT));
            System.out.println(ConsoleColors.colorize("3.", ConsoleColors.BLUE_BOLD) + " " + ConsoleColors.colorize("Hard", ConsoleColors.RED_BOLD_BRIGHT));
            System.out.println(ConsoleColors.colorize("4.", ConsoleColors.BLUE_BOLD) + " " + ConsoleColors.colorize("Back", ConsoleColors.WHITE));

            Scanner inputScanner = prompt(inputIsValid);
            String line = inputScanner.nextLine();

            try {
                int input = Integer.parseInt(line.trim());
                System.out.println(ConsoleColors.colorize("─".repeat(DIVIDER_WIDTH), inputIsValid ? ConsoleColors.BLACK_BRIGHT : ConsoleColors.RED));

                if (input >= 1 && input <= 3) {
                    startGame(SudokuGenerator.Difficulty.values()[input - 1]);
                    return;
                }
                if (input == 4) {
                    selectMode();
                    return;
                }
            } catch (NumberFormatException e) {
                // fall through to inputIsValid = false below
            }
            inputIsValid = false;
        }
    }

    public static void loadGame() {
        String message = null;

        while (true) {
            clearConsole();
            System.out.println(HEADER);
            System.out.println(ConsoleColors.colorize("Load game", ConsoleColors.YELLOW) + "\n");

            ConsoleColors messageColor = message != null ? ConsoleColors.RED : null;

            Scanner inputScanner = prompt(true, "Enter your save string (m to return): ", message, messageColor);

            String input = inputScanner.nextLine();
            if (input.equals("m")) {
                selectMode();
                return;
            }

            GameData saveData = new GameData();
            if (!saveData.importData(input)) {
                message = "Invalid save string!";
                continue;
            }

            startGame(saveData);
            return;
        }
    }


// game loop
    private static SudokuBoard sudoku;
    private static SudokuGenerator generator;
    private static long seed;
    private static SudokuGenerator.Difficulty difficulty;
    private static final int[] highlightPos = {-1, -1};

    public static void startGame(SudokuGenerator.Difficulty difficulty) {
        seed = GameUtils.generateNewSeed();
        generator = new SudokuGenerator(seed);
        sudoku = new SudokuBoard();
        sudoku.loadBoard(generator.generatePuzzle(difficulty));
        SudokuGame.difficulty = difficulty;

        gameLoop();
    }

    public static void startGame(GameData saveData) {
        sudoku = saveData.loadBoard();
        seed = saveData.getSeed();
        difficulty = saveData.getDifficulty();
        generator = new SudokuGenerator(seed);

        gameLoop();
    }

    private static void gameLoop() {
        String pendingMessage = null;
        ConsoleColors pendingColor = null;

        while (true) {
            clearConsole();
            renderBoard();

            ConsoleColors promptColor = pendingMessage != null && pendingColor == null
                    ? ConsoleColors.RED
                    : pendingColor;
            Scanner inputScanner = prompt(true, "> ", pendingMessage, promptColor);

            String input = inputScanner.nextLine();
            pendingMessage = null;
            pendingColor = null;

            switch (input) {
                case "cmds" -> {
                    clearConsole();

                    // help render
                    String[][] entries = {
                            {"input/i " + ConsoleColors.colorize("<row> <column> <value>", ConsoleColors.YELLOW), "Inputs value into the board."},
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
                    SCANNER.nextLine();
                }
                case "menu", "m" -> selectMode(); // does not return
                case "quit", "q" -> System.exit(0);
                case "refresh", "r" -> startGame(difficulty); // recurses; does not return
                case "reset", "t" -> sudoku.resetBoard();
                case "check", "c" -> {
                    boolean complete = GameUtils.isBoardComplete(sudoku.getBoard());
                    pendingMessage = complete ? "Board completed! :D" : "Board isn't completed yet. :(";
                    pendingColor = complete ? ConsoleColors.GREEN : ConsoleColors.YELLOW;
                }
                case "export", "e" -> {
                    GameData saveData = new GameData();
                    String dataString = saveData.exportData(sudoku, generator, difficulty);
                    pendingMessage = "Your save string (copy and save them in a notepad)\n" + dataString;
                    pendingColor = ConsoleColors.GREEN;
                }
                default -> {
                    if (input.contains("input") || input.contains("i")) {
                        pendingMessage = commandHandler(input);
                        // no color set here: matches original, defaults to RED via the null-color fallback above
                    }
                }
            }
        }
    }


    private static String commandHandler(String input) {
        String[] args = input.split(" ");
        if (args.length != 4) {
            return "Numbers of arguments must equal 3.";
        }
        if (!args[0].equals("i")) {
            return null; // silently ignored, matching original behavior
        }

        for (int i = 1; i < args.length; i++) {
            if (!args[i].matches("^\\d+$")) {
                return "Arguments must be an integer.";
            }
        }

        int row = Integer.parseInt(args[1]) - 1;
        int col = Integer.parseInt(args[2]) - 1;
        int val = Integer.parseInt(args[3]);

        // Range check (0 is allowed for value only, meaning "clear cell").
        for (int i = 1; i < args.length; i++) {
            if (i == 3 && args[i].equals("0")) continue;
            if (!GameUtils.isValidInput(Integer.parseInt(args[i]))) {
                return "Arguments must be in range of 1-9";
            }
        }

        // The move check and the editable check both always run (the
        // editable check is not skipped just because the move was already
        // invalid), so if a cell is both an invalid move AND not editable,
        // "Cell is not editable." is what's shown, overwriting "Invalid move!".
        boolean inputValid = true;
        String message = null;

        if (!SudokuValidator.isValidMove(sudoku.getBoard(), row, col, val)) {
            inputValid = false;
            message = "Invalid move!";
        }

        // Special case: 0 (clear cell) is always allowed through the move check.
        if (!inputValid && val == 0) {
            inputValid = true;
            message = null;
        }

        if (!sudoku.isCellEditable(row, col)) {
            inputValid = false;
            message = "Cell is not editable.";
        }

        if (!inputValid) {
            return message;
        }

        sudoku.setValue(row, col, val);
        highlightPos[0] = row;
        highlightPos[1] = col;
        return null;
    }

    private static void renderBoard() {
        ConsoleColors diffColor = switch (difficulty) {
            case EASY -> ConsoleColors.GREEN_BOLD_BRIGHT;
            case MEDIUM -> ConsoleColors.YELLOW_BOLD_BRIGHT;
            case HARD -> ConsoleColors.RED_BOLD_BRIGHT;
            default -> ConsoleColors.BLUE_BOLD_BRIGHT;
        };

        String left = HEADER + "\n" +
                ConsoleColors.colorize(" Difficulty: ", ConsoleColors.WHITE) + ConsoleColors.colorize(String.valueOf(difficulty), diffColor) + "\n" +
                ConsoleColors.colorize(" Seed: ", ConsoleColors.WHITE) + seed + "\n\n" +
                ConsoleColors.colorize(" Moves: ", ConsoleColors.WHITE) + "67" + "\n\n\n\n" +
                " cmds " + ConsoleColors.colorize("for list of commands.", ConsoleColors.WHITE);

        if (highlightPos[0] != -1 && highlightPos[1] != -1) {
            printSideBySide(left, sudoku.toString(false, true, highlightPos));
            highlightPos[0] = -1;
            highlightPos[1] = -1;
        } else {
            printSideBySide(left, sudoku.toString(false, true));
        }
    }

    // ------------------------------------------------------------------
    // Console rendering helpers
    // ------------------------------------------------------------------

    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static Scanner prompt(boolean inputWasValid) {
        return prompt(inputWasValid, "Select an option: ", null, null);
    }

    private static Scanner prompt(boolean inputWasValid, String promptText, String message, ConsoleColors messageColor) {
        ConsoleColors dividerColor;
        if (message != null) {
            dividerColor = messageColor;
        } else {
            dividerColor = inputWasValid ? ConsoleColors.BLACK_BRIGHT : ConsoleColors.RED;
        }
        System.out.print(dividerColor + "─".repeat(DIVIDER_WIDTH) + ConsoleColors.RESET + "\n");

        if (!inputWasValid) {
            System.out.println(" " + ConsoleColors.colorize("Invalid input!", ConsoleColors.RED_BRIGHT));
        }
        if (message != null) {
            ConsoleColors brightColor = ConsoleColors.valueOf(messageColor.name() + "_BRIGHT");
            System.out.println(" " + ConsoleColors.colorize(message, brightColor));
        }

        System.out.print(" " + ConsoleColors.colorize(promptText, ConsoleColors.WHITE));
        return SCANNER;
    }


    private static void printSideBySide(String left, String right) {
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

            out.append(l)
                    .append(" ".repeat(Math.max(padding, 0)))
                    .append("   ".repeat(5)) // gap between the two columns
                    .append(r)
                    .append("\n");
        }
        System.out.print(out);
    }

    private static String stripAnsi(String input) {
        return input.replaceAll("\u001B\\[[;\\d]*m", "");
    }
}