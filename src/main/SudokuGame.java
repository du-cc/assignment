package main;

import java.util.Scanner;

public class SudokuGame {
    static void main(String[] args) {
        boolean inputIsValid = true;
        int input;

        while (true) {
            clearConsole();

            // title (ASCII art)
            System.out.println(
                    ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.RESET + "\n" +
                            ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "_" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "_" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.RESET + "\n" +
                            ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + "_" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "_" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.RESET + "\n" +
                            ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "_" + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + "|" + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + "." + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + "." + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + "'" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "|" + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.RESET + "\n" +
                            ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "_" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "_" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "_" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "|" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "_" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "_" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "|" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "," + ConsoleColors.CYAN_BOLD + "_" + ConsoleColors.BLUE_BOLD + "|" + ConsoleColors.PURPLE_BOLD + "_" + ConsoleColors.RED_BOLD + "_" + ConsoleColors.YELLOW_BOLD + "_" + ConsoleColors.GREEN_BOLD + "|" + ConsoleColors.RESET + "\n" +
                            ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RED_BOLD + " " + ConsoleColors.YELLOW_BOLD + " " + ConsoleColors.GREEN_BOLD + " " + ConsoleColors.CYAN_BOLD + " " + ConsoleColors.BLUE_BOLD + " " + ConsoleColors.PURPLE_BOLD + " " + ConsoleColors.RESET
            );

            // menu item
            System.out.println(ConsoleColors.colorize("1.", ConsoleColors.BLUE_BOLD) + " Start Game");
            System.out.println(ConsoleColors.colorize("2.", ConsoleColors.BLUE_BOLD) + " Load Game");
            System.out.println(ConsoleColors.colorize("3.", ConsoleColors.BLUE_BOLD) + " Exit");

            // input
            Scanner inputScanner = inputInit(inputIsValid);
            if (inputScanner.hasNextInt()) {
                input = inputScanner.nextInt();
                System.out.println(ConsoleColors.colorize("╰" + "─".repeat(79), inputIsValid ? ConsoleColors.BLACK_BRIGHT : ConsoleColors.RED));

                // 1. start game
                if (input == 1) {
                    startGame();
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

    public static void startGame() {
        boolean inputIsValid = true;
        int input;
        while (true) {
            clearConsole();

            System.out.println("  __                      _      _   _                   \n" +
                    " (_   _  |  _   _ _|_    | \\ o _|_ _|_ o  _     | _|_    \n" +
                    " __) (/_ | (/_ (_  |_    |_/ |  |   |  | (_ |_| |  |_ \\/ \n" +
                    "                                                      /  ");

            // menu item
            System.out.println(ConsoleColors.colorize("1.", ConsoleColors.BLUE_BOLD) + " " + ConsoleColors.colorize("Easy", ConsoleColors.GREEN_BOLD_BRIGHT));
            System.out.println(ConsoleColors.colorize("2.", ConsoleColors.BLUE_BOLD) + " " + ConsoleColors.colorize("Medium", ConsoleColors.YELLOW_BOLD_BRIGHT));
            System.out.println(ConsoleColors.colorize("3.", ConsoleColors.BLUE_BOLD) + " " + ConsoleColors.colorize("Hard", ConsoleColors.RED_BOLD_BRIGHT));


            // input
            Scanner inputScanner = inputInit(inputIsValid);

            if (inputScanner.hasNextInt()) {
                input = inputScanner.nextInt();
                System.out.println(ConsoleColors.colorize("╰" + "─".repeat(79), inputIsValid ? ConsoleColors.BLACK_BRIGHT : ConsoleColors.RED));
                break;
            } else {
                inputIsValid = false;
            }
        }
    }

    // Helper
    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static Scanner inputInit(boolean valid) {
        // top border
        System.out.println(ConsoleColors.colorize("╭" + "─".repeat(79), valid ? ConsoleColors.BLACK_BRIGHT : ConsoleColors.RED));

// invalid message row (only shown when invalid)
        if (!valid) {
            System.out.println(ConsoleColors.colorize("│ ", ConsoleColors.RED) + ConsoleColors.colorize("Invalid input!", ConsoleColors.RED_BRIGHT));
        }

// input row
        Scanner scanner = new Scanner(System.in);
        System.out.print(ConsoleColors.colorize("│ ", valid ? ConsoleColors.BLACK_BRIGHT : ConsoleColors.RED) + ConsoleColors.colorize("Select an option: ", ConsoleColors.WHITE));
        return scanner;
    }

}