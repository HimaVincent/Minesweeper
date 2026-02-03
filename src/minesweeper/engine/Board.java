package minesweeper.engine;

public class Board {
    private final int rows;
    private final int cols;
    private final Cell[][] grid;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new Cell[rows][cols];

        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < cols; c++) {
                grid[r][c] = new Cell();
            }
        }
    }

    public int  getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
    
    public boolean inBounds(int r, int c) {
        return r >= 0 && r < rows && c>= 0 && c < cols; 
    }

    public Cell getCell(int r, int c) {
        return grid[r][c];
    }
}
