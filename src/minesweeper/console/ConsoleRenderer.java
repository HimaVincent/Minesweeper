package minesweeper.console;

import minesweeper.engine.Board;
import minesweeper.engine.Cell;
import minesweeper.engine.GameState;

public class ConsoleRenderer {
    private static final String RESET = "\u001B[0m";
    private static final String ONE   = "\u001B[38;5;33m";
    private static final String TWO   = "\u001B[38;5;2m";
    private static final String THREE = "\u001B[38;5;3m";
    private static final String FOUR  = "\u001B[38;5;4m";
    private static final String FIVE  = "\u001B[38;5;5m";
    private static final String SIX   = "\u001B[38;5;6m";
    private static final String SEVEN = "\u001B[38;5;130m";
    private static final String EIGHT = "\u001B[38;5;8m";
    private static final String FLAG = "\u001B[41m";
    private static final String MINE = "\u001B[38;5;196m";


    public void render(Board board, GameState state) {

        System.out.println();
        System.out.print("    "); 
        for (int col = 1; col <= board.getCols(); col++) {
            System.out.printf("%-4s", col); 
        }
        System.out.println();

  
        System.out.print("   "); 
        printBorderLine(board.getCols());
        for (int r = 0; r < board.getRows(); r++) {
            System.out.printf("%2d ", (r + 1));
            System.out.print("|");
            for (int c = 0; c < board.getCols(); c++) {
                Cell cell = board.getCell(r, c);
                String sym = symbolFor(cell, state);
                System.out.print(" " + sym + " |");
                }
            System.out.println();
            System.out.print("   ");
            printBorderLine(board.getCols());
        }
    }


    private void printBorderLine(int cols) {

        System.out.print("+");
        for (int i = 0; i < cols; i++) {
            System.out.print("---+");
        }
        System.out.println();
    }



    private String symbolFor(Cell cell, GameState state) {

        if (state == GameState.LOST && cell.hasMine()) {
            return MINE + "*" + RESET;
        }

        if (cell.isFlagged() && !cell.isRevealed()) {
            return FLAG + "F" + RESET;
        }

        if (!cell.isRevealed()) {
            return "░";
        }

        int n = cell.getAdjacentMines();
        if (n == 0) return " ";
        return switch (n) {
            case 1 -> ONE   + "1" + RESET;
            case 2 -> TWO   + "2" + RESET;
            case 3 -> THREE + "3" + RESET;
            case 4 -> FOUR  + "4" + RESET;
            case 5 -> FIVE  + "5" + RESET;
            case 6 -> SIX   + "6" + RESET;
            case 7 -> SEVEN + "7" + RESET;
            case 8 -> EIGHT + "8" + RESET;
            default -> " ";
        };
    }
}
