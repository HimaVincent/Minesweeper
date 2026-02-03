package minesweeper.engine;

public class GameConfig {
    public final int rows;
    public final int cols;
    public final int mines;

    public GameConfig(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
    }

    public static GameConfig defaultConfig() {
        return new GameConfig(10,10 , 20);
    }
}
