package minesweeper.console;

import java.util.Scanner;

import minesweeper.engine.GameConfig;
import minesweeper.engine.MoveResult;
import minesweeper.engine.Action;
import minesweeper.engine.Game;


public class ConsoleApp {

    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String RESET = "\u001B[0m";

    private static void printBoom() {
        System.out.println(RED);
        System.out.println("██████╗  ██████╗  ██████╗ ███╗   ███╗");
        System.out.println("██╔══██╗██╔═══██╗██╔═══██╗████╗ ████║");
        System.out.println("██████╔╝██║   ██║██║   ██║██╔████╔██║");
        System.out.println("██╔══██╗██║   ██║██║   ██║██║╚██╔╝██║");
        System.out.println("██████╔╝╚██████╔╝╚██████╔╝██║ ╚═╝ ██║");
        System.out.println("╚═════╝  ╚═════╝  ╚═════╝ ╚═╝     ╚═╝");
        System.out.println();
        System.out.println("    OOPS  YOU  HIT   A   MINE !!!");
        System.out.println(RESET);
    }

    private static void printWin() {
        System.out.println(GREEN);
        System.out.println("██╗   ██╗  █████╗  ██╗   ██╗");
        System.out.println("╚██╗ ██╔╝ ██╔══██╗ ╚██╗ ██╔╝");
        System.out.println(" ╚████╔╝  ███████║  ╚████╔╝ ");
        System.out.println("  ╚██╔╝   ██╔══██║   ╚██╔╝  ");
        System.out.println("   ██║    ██║  ██║    ██║   ");
        System.out.println("   ╚═╝    ╚═╝  ╚═╝    ╚═╝   ");
        System.out.println();
        System.out.println("        YAY  YOU  WON !!!");
        System.out.println(RESET);
    }

    private static int askForNumber(Scanner scanner, String prompt, int min, int max) {

        while(true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
               throw new RuntimeException("QUIT");
            }

            try{
                int value = Integer.parseInt(input);

                if (value < min || value > max) {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                    continue;
                } 
                    return value;
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    private static GameConfig askUserForConfig(Scanner scanner) {

        try {
            System.out.println();
            System.out.println("Game Setup (Q to quit)");

            int rows = askForNumber(scanner, "Enter number of rows (2-20): ", 2, 20);
            int cols = askForNumber(scanner, "Enter number of columns (2-20): ", 2,20);

            int cells = rows * cols;
            int maxByDensity = (int) Math.floor(cells * 0.25);
            int maxMines = Math.max(1, Math.min(maxByDensity, cells - 1));
            int mines = askForNumber(scanner, "Enter number of mines (1-" + maxMines + "): ", 1, maxMines);

            return new GameConfig(rows, cols, mines);

        } catch (RuntimeException e) {
            if ("QUIT".equals(e.getMessage())) {
                return null;
            }
            throw e;
        }
}
    

    public static void main(String[] args) {
       
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;

        while (playAgain) {
            GameConfig config = askUserForConfig(scanner);
            if (config == null) {
                System.out.println("Bye!");
                break; 
            }
            Game game = new Game(config);
            ConsoleRenderer renderer = new ConsoleRenderer();
                
            for (int i = 0; i < 3; i++) System.out.println();
            System.out.println("Enter: row,col to reveal OR row,col,F to flag/unflag (1-10). Q to quit.");

            renderer.render(game.getBoard(), game.getState());
            boolean quitToDesktop = false;

            while (true) {
                System.out.println();
                System.out.println("Enter move (row,col or row,col,F): ");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("q")) {
                    System.out.println("Bye!");
                    quitToDesktop = true;
                    break;
                }

                String[] parts = input.split(",");
                if (parts.length != 2 && parts.length !=3) {
                    System.out.println("Invalid format. Use row,col or row,col,F");
                    continue;
                }

                int row;
                int col;

                try {
                        row = Integer.parseInt(parts[0].trim());
                        col = Integer.parseInt(parts[1].trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Row and column must be numbers.");
                        continue;
                    }

                int r = row - 1;
                int c = col - 1;

                Action action = Action.REVEAL;

                if (parts.length == 3) {
                    String suffix = parts[2].trim();
                    if (!suffix.equalsIgnoreCase("f")) {
                        System.out.println("Invalid suffix. Only F is supported.");
                        continue;
                    }
                    action = Action.TOGGLE_FLAG;
                }

                MoveResult result = game.makeMove(action, r, c);
                String message = null;
                switch (result) {
                    case OK : 
                        break;

                    case ALREADY_REVEALED : 
                        message = "Already revealed.";
                        break; 
                            
                    case CELL_FLAGGED :
                        message = "That cell is flagged. Use row,col,F to unflag.";
                        break;

                    case OUT_OF_BOUNDS :
                        message = "Out of bounds. Use 1 -10.";
                        break;

                    case LOST : 
                        message = "__BOOM__";
                        break;

                    case WON : 
                        message = "__WIN__";
                        break;

                }
                
                for (int i = 0; i < 3; i++) System.out.println();

                renderer.render(game.getBoard(), game.getState());
                if (message != null) {
                    System.out.println();
                    switch (message) {
                        case "__BOOM__":
                            printBoom();
                            break;

                        case "__WIN__":
                            printWin();
                            break;

                        default:
                            System.out.println(message);
                    }
                }


                if (result == MoveResult.LOST || result == MoveResult.WON) {
                    break;
                }   
            }

            if (quitToDesktop) {
               break; 
            }

            System.out.println();
            System.out.print("Play again? (y/n): ");
            String answer = scanner.nextLine().trim().toLowerCase();
            playAgain = answer.equals("y");
        }

        scanner.close();
        System.out.println("Thanks for playing!");
    }
}
