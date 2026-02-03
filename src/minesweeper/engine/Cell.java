package minesweeper.engine;

public class Cell {
    private boolean hasMine;
    private boolean revealed;
    private boolean flagged;
    private int adjacentMines;

    public boolean hasMine() {
        return hasMine;
    }

    public void setMine(boolean hasMine) {
        this.hasMine = hasMine;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean value) {
        this.revealed = value;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void toggleFlag() {
        this.flagged = !this.flagged;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    public void setAdjacentMines(int value) {
        this.adjacentMines = value;
    }   
}
