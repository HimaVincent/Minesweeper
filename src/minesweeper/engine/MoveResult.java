package minesweeper.engine;

public enum MoveResult {
        OK,
        ALREADY_REVEALED,
        CELL_FLAGGED,
        LOST,
        WON,
        OUT_OF_BOUNDS
}
