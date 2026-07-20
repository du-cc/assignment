package main;

import java.util.*;

public class SudokuGame {
    static void main(String[] args) {
        selectMode();
    }

    public static void selectMode() {
        boolean inputIsValid = true;
        int input;

        while (true) {
            clearConsole();

            // title (ASCII art)
            System.out.println(header);
            // menu item
            System.out.println(ConsoleColors.colorize("1.", ConsoleColors.BLUE_BOLD) + " Start Game");
            System.out.println(ConsoleColors.colorize("2.", ConsoleColors.BLUE_BOLD) + " Load Game");
            System.out.println(ConsoleColors.colorize("3.", ConsoleColors.BLUE_BOLD) + " " + ConsoleColors.colorize("Exit", ConsoleColors.WHITE));

            // input
            Scanner inputScanner = inputInit(inputIsValid);
            if (inputScanner.hasNextInt()) {
                input = inputScanner.nextInt();
                System.out.println(ConsoleColors.colorize("─".repeat(80), inputIsValid ? ConsoleColors.BLACK_BRIGHT : ConsoleColors.RED));

                // 1. start game
                if (input == 1) {
                    selectDifficulty();
                    break;
                }

                // 2. load game
                if (input == 2) {
                    loadGame();
                }

                // 3. quit
                if (input == 3) {
                    System.out.println("Thanks for playing!");
                    System.exit(0);
                    break;
                }
            }
            inputIsValid = false;
        }
    }

    public static void selectDifficulty() {
        boolean inputIsValid = true;
        int input;
        while (true) {
            clearConsole();

            // menu item
            System.out.println(header);
            System.out.println(ConsoleColors.colorize("Select Difficulty", ConsoleColors.CYAN_UNDERLINED));

            System.out.println(ConsoleColors.colorize("1.", ConsoleColors.BLUE_BOLD) + " " + ConsoleColors.colorize("Easy", ConsoleColors.GREEN_BOLD_BRIGHT));
            System.out.println(ConsoleColors.colorize("2.", ConsoleColors.BLUE_BOLD) + " " + ConsoleColors.colorize("Medium", ConsoleColors.YELLOW_BOLD_BRIGHT));
            System.out.println(ConsoleColors.colorize("3.", ConsoleColors.BLUE_BOLD) + " " + ConsoleColors.colorize("Hard", ConsoleColors.RED_BOLD_BRIGHT));
            System.out.println(ConsoleColors.colorize("4.", ConsoleColors.BLUE_BOLD) + " " + ConsoleColors.colorize("Back", ConsoleColors.WHITE));

            // input
            Scanner inputScanner = inputInit(inputIsValid);

            if (inputScanner.hasNextInt()) {
                input = inputScanner.nextInt();
                System.out.println(ConsoleColors.colorize("─".repeat(80), inputIsValid ? ConsoleColors.BLACK_BRIGHT : ConsoleColors.RED));

                if (input >= 1 && input <= 3) {
                    startGame(SudokuGenerator.Difficulty.values()[input - 1]);
                    break;
                }
                if (input == 4) {
                    selectMode();
                    break;
                }
            }
            inputIsValid = false;

        }
    }

    public static void loadGame() {
        String message = null;
        ConsoleColors messageColor = null;

        while (true) {
            clearConsole();
            System.out.println(header);
            System.out.println(ConsoleColors.colorize("Load game", ConsoleColors.YELLOW) + "\n");
            boolean inputIsValid = true;
            Scanner inputScanner;
            if (message != null && messageColor == null) messageColor = ConsoleColors.RED;
            inputScanner = inputInit(inputIsValid, "Enter your save string (m to return): ", message, messageColor);
            message = null;
            messageColor = null;
            String input = inputScanner.nextLine();

            // quit
            if (input.equals("m")) {
                selectMode();
            }

            GameData gameData = new GameData();
            boolean success = gameData.importData(input);
            if (!success) {
                message = "Invalid save string!";
                continue;
            }

            startGame(gameData.getDifficulty(), gameData);
        }
    }

    public static void startGame(SudokuGenerator.Difficulty difficulty) {
        startGame(difficulty, null);
    }

    public static void startGame(SudokuGenerator.Difficulty difficulty, GameData gameData) {
        SudokuBoard sudoku = null;
        SudokuGenerator generator = null;
        long seed;

        // load board based on new/save
        if (gameData == null) {
            sudoku = new SudokuBoard();
            seed = GameUtils.generateNewSeed();
            generator = new SudokuGenerator(seed);
            sudoku.loadBoard(generator.generatePuzzle(difficulty));

            // DEBUG
//        sudoku.loadBoard(generator.generatePuzzle(SudokuGenerator.Difficulty.EMPTY));

        } else {
            sudoku = gameData.loadBoard();
            seed = gameData.getSeed();
            difficulty = gameData.getDifficulty();
        }

        int[][] board = sudoku.getBoard();

        // environment variables
        int[] highlightPos = new int[2];
        highlightPos[0] = -1;
        highlightPos[1] = -1;

        String message = null;
        ConsoleColors messageColor = null;
        while (true) {
            clearConsole();

            // board display

            // determine text color
            ConsoleColors diffColor = ConsoleColors.BLUE_BOLD_BRIGHT;
            if (difficulty.toString().equals("EASY")) diffColor = ConsoleColors.GREEN_BOLD_BRIGHT;
            if (difficulty.toString().equals("MEDIUM")) diffColor = ConsoleColors.YELLOW_BOLD_BRIGHT;
            if (difficulty.toString().equals("HARD")) diffColor = ConsoleColors.RED_BOLD_BRIGHT;


//        System.out.println(ConsoleColors.colorize("Difficulty: ", ConsoleColors.WHITE) + ConsoleColors.colorize(String.valueOf(difficulty), diffColor));
//        System.out.println(ConsoleColors.colorize("Seed: ", ConsoleColors.WHITE) + seed + "\n");

            String left = header + "\n" +
                    ConsoleColors.colorize(" Difficulty: ", ConsoleColors.WHITE) + ConsoleColors.colorize(String.valueOf(difficulty), diffColor) + "\n" +
                    ConsoleColors.colorize(" Seed: ", ConsoleColors.WHITE) + seed + "\n\n" +
                    ConsoleColors.colorize(" Moves: ", ConsoleColors.WHITE) + "67" + "\n\n\n\n" +
                    " cmds " + ConsoleColors.colorize("for list of commands.", ConsoleColors.WHITE);


//          highlight
            if (highlightPos[0] != -1 && highlightPos[1] != -1) {
                printSideBySide(left, sudoku.toString(false, true, highlightPos));
                highlightPos[0] = -1;
                highlightPos[1] = -1;
            } else {
                printSideBySide(left, sudoku.toString(false, true));
            }


            boolean inputIsValid = true;
            Scanner inputScanner;
            if (message != null && messageColor == null) messageColor = ConsoleColors.RED;
            inputScanner = inputInit(inputIsValid, "> ", message, messageColor);
            message = null;
            messageColor = null;
            String input = inputScanner.nextLine();

            // Commands
            // input <row> <col> <value>
            // reset
            // check
            // solve
            //
            if (input.equals("cmds")) {
                clearConsole();
                System.out.println(getCommands());
                System.out.println("\n" + ConsoleColors.colorize("press ENTER to continue", ConsoleColors.BLACK_BRIGHT));
                Scanner continueScanner = new Scanner(System.in);
                continueScanner.nextLine();
            }

            if (input.equals("menu") || input.equals("m")) {
                // TODO: EXPORT
                selectMode();
            }

            if (input.equals("quit") || input.equals("q")) {
                // TODO: EXPORT
                System.exit(0);
            }

            if (input.equals("refresh") || input.equals("r")) {
                startGame(difficulty);
            }

            if (input.equals("reset") || input.equals("t")) {
                sudoku.resetBoard();
            }

            if (input.equals("check") || input.equals("c")) {
                if (GameUtils.isBoardComplete(board)) {
                    message = "Board completed! :D";
                    messageColor = ConsoleColors.GREEN;
                } else {
                    message = "Board isn't completed yet. :(";
                    messageColor = ConsoleColors.YELLOW;
                }
            }

            if (input.equals("export") || input.equals("e")) {
                gameData = new GameData();
                String dataString = gameData.exportData(sudoku, generator, difficulty);
                message = "Your save string (copy and save them in a notepad)\n" + dataString;
                messageColor = ConsoleColors.GREEN;
            }

            if (input.contains("input") || input.contains("i")) {
                // syntax check
                String[] args = input.split(" ");
                if (args.length != 4) {
                    inputIsValid = false;
                    message = "Numbers of arguments must equal 3.";
                    continue;
                }
                // for "i" command. more check
                if (!args[0].equals("i")) continue;
                for (int i = 1; i < args.length; i++) {
                    if (!args[i].matches("^\\d+$")) {
                        inputIsValid = false;
                        message = "Arguments must be an integer.";
                        break;
                    }
                }

                if (!inputIsValid) continue;

                // other checks
                /*
                Priority:
                2. range 1-9
                3. editable
                3. move
                 */
                int row = Integer.parseInt(args[1]) - 1;
                int col = Integer.parseInt(args[2]) - 1;
                int val = Integer.parseInt(args[3]);

                // input range check
                for (int i = 1; i < args.length; i++) {
                    // special case for value (0 to delete)
                    if (i == 3 && args[i].equals("0")) continue;
                    if (!GameUtils.isValidInput(Integer.parseInt(args[i]))) {
                        inputIsValid = false;
                        message = "Arguments must be in range of 1-9";
                        break;
                    }
                }

                if (!inputIsValid) continue;

                // gameplay check
                // move check
                if (!SudokuValidator.isValidMove(board, row, col, val)) {
                    inputIsValid = false;
                    message = "Invalid move!";
                }

                // special case for value (0 to delete)
                if (!inputIsValid && val == 0) {
                    inputIsValid = true;
                    message = null;
                }

                // editable check
                if (!sudoku.isCellEditable(row, col)) {
                    inputIsValid = false;
                    message = "Cell is not editable.";
                }


                if (!inputIsValid) continue;

                sudoku.setValue(row, col, val);
                highlightPos[0] = row;
                highlightPos[1] = col;
            }
        }
    }

    private static String getCommands() {

        return ConsoleColors.CYAN_UNDERLINED + "Commands\n\n" + ConsoleColors.RESET +
                "╭ input/i " + ConsoleColors.colorize("<row> <column> <value>", ConsoleColors.YELLOW) +
                ConsoleColors.colorize("\n├ Inputs value into the board.", ConsoleColors.WHITE) +
                ConsoleColors.colorize("\n┆", ConsoleColors.BLACK_BRIGHT) +
                "\n├ check/c" +
                ConsoleColors.colorize("\n├ Checks if the board is completed.", ConsoleColors.WHITE) +
                ConsoleColors.colorize("\n┆", ConsoleColors.BLACK_BRIGHT) +
                "\n├ export/e" +
                ConsoleColors.colorize("\n├ Exports data of this board into a string.", ConsoleColors.WHITE) +
                ConsoleColors.colorize("\n┆", ConsoleColors.BLACK_BRIGHT) +
                "\n├ refresh/r" +
                ConsoleColors.colorize("\n├ Refreshes the board with another random seed.", ConsoleColors.WHITE) +
                ConsoleColors.colorize("\n┆", ConsoleColors.BLACK_BRIGHT) +
                "\n├ reset/t" +
                ConsoleColors.colorize("\n├ Resets the board to originally prefilled.", ConsoleColors.WHITE) +
                ConsoleColors.colorize("\n┆", ConsoleColors.BLACK_BRIGHT) +
                "\n├ menu/m" +
                ConsoleColors.colorize("\n├ Returns to main menu.", ConsoleColors.WHITE) +
                ConsoleColors.colorize("\n┆", ConsoleColors.BLACK_BRIGHT) +
                "\n├ quit/q" +
                ConsoleColors.colorize("\n├ Quits this game.", ConsoleColors.WHITE) +
                ConsoleColors.colorize("\n┆", ConsoleColors.BLACK_BRIGHT) +
                "\n├ cmds" +
                ConsoleColors.colorize("\n╰ Shows this list of commands.", ConsoleColors.WHITE);
    }


    // Helper
    private static final String header = (ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "_" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "_" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.RESET + "\n" +
            ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + "_" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "_" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.RESET + "\n" +
            ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "_" + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + "|" + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + "." + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + "." + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + "'" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "|" + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.RESET + "\n" +
            ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "_" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "_" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "_" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "|" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "_" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "_" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "," + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "|" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "_" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.RESET + "\n" +
            ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RESET
    );

    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static Scanner inputInit(boolean valid) {
        return inputInit(valid, "Select an option: ", null, null);
    }

    private static Scanner inputInit(boolean valid, String prompt) {
        return inputInit(valid, prompt, null, null);
    }

    private static Scanner inputInit(boolean valid, String prompt, String message) {
        return inputInit(valid, "Select an option: ", prompt, ConsoleColors.RED);
    }

    private static Scanner inputInit(boolean valid, String prompt, String message, ConsoleColors color) {
        // top border
        if (message == null) {
            if (valid) System.out.print(ConsoleColors.BLACK_BRIGHT);
            if (!valid) System.out.print(ConsoleColors.RED);
        } else {
            System.out.print(color);
        }

        System.out.print("─".repeat(80) + ConsoleColors.RESET + "\n");

        // invalid
        if (!valid) {
            System.out.println(" " + ConsoleColors.colorize("Invalid input!", ConsoleColors.RED_BRIGHT));
        }

        // custom message
        if (message != null) {
            System.out.println(" " + ConsoleColors.colorize(message, ConsoleColors.valueOf(color.name() + "_BRIGHT")));
        }

        // input
        Scanner scanner = new Scanner(System.in);
        System.out.print(" " + ConsoleColors.colorize(prompt, ConsoleColors.WHITE));
        return scanner;
    }


    private static void printSideBySide(String left, String right) {
        String[] leftLines = left.split("\n");
        String[] rightLines = right.split("\n");
        int printLines = Math.max(leftLines.length, rightLines.length);

        // Find widest line on the left to know how much padding to use
        int leftWidth = 0;
        for (String l : leftLines) {
            leftWidth = Math.max(leftWidth, stripAnsi(l).length());
        }

        for (int i = 0; i < printLines; i++) {
            String l = i < leftLines.length ? leftLines[i] : "";
            String r = i < rightLines.length ? rightLines[i] : "";

            int visibleLen = stripAnsi(l).length();
            int padding = leftWidth - visibleLen;

            System.out.print(l);
            System.out.print(" ".repeat(Math.max(padding, 0)));
            System.out.print("   ".repeat(5)); // additional pad
            System.out.println(r);
        }
    }

    private static String stripAnsi(String input) {
        return input.replaceAll("\u001B\\[[;\\d]*m", "");
    }

}

