package minesweeper.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    // 1. fields
    private final GameConfig config;
    private final Board board;
    private GameState state;

    // used for win check
    private int revealedSafeCells = 0;

    // 2. constructor
    public Game(GameConfig config) {
        this.config = config;
        this.board = new Board(config.rows, config.cols);
        this.state= GameState.NOT_STARTED;

        placeMines();
        calculateAdjacentCounts();
    }

    // 3. public methods
    public Board getBoard() {
        return board;
    }


    public GameState getState() {
        return state;
    }

    // one public entry point for moves
    public MoveResult makeMove(Action action, int r, int c) {
        if (!board.inBounds(r, c)) {
            return MoveResult.OUT_OF_BOUNDS;
        }

        Cell cell = board.getCell(r, c);

        if (action == Action.TOGGLE_FLAG) {
            return toggleFlag(cell);
        }

        return revealCell(r, c);
    }


    private MoveResult revealCell(int r, int c) {
        Cell cell = board.getCell(r, c);

        if (cell.isRevealed()) {
            return MoveResult.ALREADY_REVEALED;
        }

        if (cell.isFlagged()) {
            return MoveResult.CELL_FLAGGED;
        }

        cell.setRevealed(true);

        if (cell.hasMine()) {
            state = GameState.LOST;
            return MoveResult.LOST;
        }

        // safe cell revealed
        revealedSafeCells++;

        // cascade if zero
        if (cell.getAdjacentMines() == 0) {
            cascadeRevealZeros(r, c);
        }

         // win check after cascade (because cascade reveals many cells)
        int totalCells = config.rows * config.cols;
        int safeCells = totalCells - config.mines;

        if (revealedSafeCells == safeCells) {
            state = GameState.WON;
            return MoveResult.WON;
        }

        return MoveResult.OK;
    }


    private void cascadeRevealZeros(int startR, int startC) {
        java.util.ArrayDeque<int[]> queue = new java.util.ArrayDeque<>();
        queue.add(new int[]{startR, startC});

        while(!queue.isEmpty()) {
            int[] pos = queue.remove();
            int r = pos[0];
            int c = pos[1];

            // Look at all 8 neighbours
            for (int dr = -1; dr<=1; dr++) {
                for (int dc = -1; dc<=1; dc++) {

                    if (dr == 0 && dc == 0) continue;

                    int nr = r + dr;
                    int nc = c + dc;
                    if (!board.inBounds(nr, nc)) continue;

                    Cell neighbour = board.getCell(nr, nc);

                    // Don't reveal flagged cells
                    if (neighbour.isFlagged()) continue;

                    // Don't reveal mines during cascade
                    if (neighbour.hasMine()) continue;

                    // If already revealed, skip (prevents loops + double counting)
                    if (neighbour.isRevealed()) continue;

                    // Reveal this neighbour
                    neighbour.setRevealed(true);
                    revealedSafeCells++;

                    // If this neighbour is also zero, it should expand further
                    if (neighbour.getAdjacentMines() == 0) {
                        queue.add(new int[]{nr, nc});
                    } 
                }
            }
        }

    }

    
    private MoveResult toggleFlag(Cell cell) {
        if (cell.isRevealed()) {
            return MoveResult.ALREADY_REVEALED;
        }

        cell.toggleFlag();
        return MoveResult.OK;
    }

    //private helpers
    private void placeMines() {
        List<int[]> positions = new ArrayList<>();

        for(int r = 0; r < config.rows; r++){
            for(int c = 0; c < config.cols; c++) {
                positions.add(new int[]{r,c});
            }
        }

        Collections.shuffle(positions);

        for(int i = 0; i < config.mines; i++) {
            int[] pos = positions.get(i);
            int r = pos[0];
            int c = pos[1];

            board.getCell(r,c).setMine(true);
        }
    }


    private void calculateAdjacentCounts() {
        for( int r = 0; r < config.rows; r++) {
            for( int c = 0; c < config.cols; c++) {
                Cell cell = board.getCell(r,c);

                if(cell.hasMine()) {
                    cell.setAdjacentMines(0);
                    continue;
                }

                int count = 0;

                for(int dr = -1; dr <= 1; dr++) {
                    for(int dc = -1; dc <= 1; dc++) {

                        if(dr == 0 && dc == 0) continue;

                        int nr = r + dr;
                        int nc = c + dc;

                        if(!board.inBounds(nr, nc)) continue;

                        if(board.getCell(nr, nc).hasMine()) {
                            count++;
                        }
                    }
                }

                cell.setAdjacentMines(count);
            }
        }
    } 
}
