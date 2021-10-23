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

    protected void createNewGrid(BoardGrid newGrid) {
        this.grid = newGrid;
    }

    protected void enableGrid(boolean enabled) {
        this.grid.setEnabled(enabled);
    }

    protected void goToNextLevel(BoardGrid newGrid) {
        grid = newGrid;
    }

}
