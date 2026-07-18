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

    public static void startGame(SudokuGenerator.Difficulty difficulty) {
            long seed = GameUtils.generateNewSeed();
            SudokuBoard sudoku = new SudokuBoard();
            SudokuGenerator generator = new SudokuGenerator(seed);

            sudoku.loadBoard(generator.generatePuzzle(difficulty));
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


            printSideBySide(left, sudoku.toString(true));


            boolean inputIsValid = true;
            Scanner inputScanner = inputInit(inputIsValid, "> ");
            String input = inputScanner.nextLine();

            // Commands
            // input <row> <col> <value>
            // reset
            // check
            // solve
            //
            if (input.equals("cmds")) {
                showCommands();
                System.out.println("\n\n" + ConsoleColors.colorize("press ENTER to continue", ConsoleColors.BLACK_BRIGHT));
                Scanner continueScanner = new Scanner(System.in);
                continueScanner.nextLine();
            }

            if (input.equals("return")) {
                selectMode();
            }

            if (input.equals("exit")) {
                // TODO: EXPORT

            }
        }
    }

    public static void showCommands() {
        // construct command list
        clearConsole();
        System.out.println(ConsoleColors.CYAN_UNDERLINED + "Commands\n" + ConsoleColors.RESET);

        System.out.println("╭ input " + ConsoleColors.colorize("<row> <column> <value>", ConsoleColors.YELLOW));
        System.out.println(ConsoleColors.colorize("├ Inputs value into the board.", ConsoleColors.WHITE));
        System.out.println(ConsoleColors.colorize("┆", ConsoleColors.BLACK_BRIGHT));
        System.out.println("├ check");
        System.out.println(ConsoleColors.colorize("├ Checks if the board is completed.", ConsoleColors.WHITE));
        System.out.println(ConsoleColors.colorize("┆", ConsoleColors.BLACK_BRIGHT));
        System.out.println("├ export");
        System.out.println(ConsoleColors.colorize("├ Exports data of this board into a string.", ConsoleColors.WHITE));
        System.out.println(ConsoleColors.colorize("┆", ConsoleColors.BLACK_BRIGHT));
        System.out.println("├ return");
        System.out.println(ConsoleColors.colorize("├ Returns to main menu.", ConsoleColors.WHITE));
        System.out.println(ConsoleColors.colorize("┆", ConsoleColors.BLACK_BRIGHT));
        System.out.println("├ exit");
        System.out.println(ConsoleColors.colorize("├ Quits this game.", ConsoleColors.WHITE));
        System.out.println(ConsoleColors.colorize("┆", ConsoleColors.BLACK_BRIGHT));
        System.out.println("├ cmds");
        System.out.println(ConsoleColors.colorize("╰ Shows this list of commands.", ConsoleColors.WHITE));
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
        System.out.println(ConsoleColors.colorize("─".repeat(80), valid ? ConsoleColors.BLACK_BRIGHT : ConsoleColors.RED));

        // invalid
        if (!valid) {
            System.out.println(ConsoleColors.colorize(" ", ConsoleColors.RED) + ConsoleColors.colorize("Invalid input!", ConsoleColors.RED_BRIGHT));
        }

        // input
        Scanner scanner = new Scanner(System.in);
        System.out.print(ConsoleColors.colorize(" ", valid ? ConsoleColors.BLACK_BRIGHT : ConsoleColors.RED) + ConsoleColors.colorize(prompt, ConsoleColors.WHITE));
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

