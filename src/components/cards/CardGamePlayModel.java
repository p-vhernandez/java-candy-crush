package components.cards;

import components.grid.BoardGrid;
import utils.Level;

public class CardGamePlayModel {

    private Level level;

    private BoardGrid grid;

    public CardGamePlayModel() {
    }

    protected Level getLevel() {
        return level;
    }

    protected void setLevel(Level level) {
        this.level = level;
    }

    protected BoardGrid getGrid() {
        return grid;
    }

    protected void setGrid(BoardGrid grid) {
        this.grid = grid;
    }

    protected void createNewGrid() {
        this.grid = new BoardGrid(level);
    }

    protected void enableGrid(boolean enabled) {
        this.grid.setEnabled(enabled);
    }

    protected void goToNextLevel() {
        Level nextLevel = new Level(level.getDifficulty() + 5);
        grid = null;
        grid = new BoardGrid(nextLevel);
        //grid.setLevel(nextLevel);
        //grid.switchLevels();
    }

}
