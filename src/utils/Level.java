package utils;

import utils.helpers.LevelType;

public class Level {

    private final LevelType levelType;

    private int MAX_MOVEMENTS;
    private int STANDARD_CANDY_CRUSHED,
            SPECIAL_CANDY_CRUSHED,
            SEVERAL_CRUSHES_BONUS;

    private int NUM_COLUMNS,
            NUMBER_ROWS;

    private int LEVEL_GOAL;

    public Level(LevelType type) {
        this.levelType = type;

        setUpLevelCharacteristics();
    }

    // TODO: set up all level's characteristics
    private void setUpLevelCharacteristics() {
        switch (levelType) {
            case RECTANGLE: {
                STANDARD_CANDY_CRUSHED = 0;
                SPECIAL_CANDY_CRUSHED = 0;
                SEVERAL_CRUSHES_BONUS = 0;
                MAX_MOVEMENTS = 0;
                NUM_COLUMNS = 0;
                NUMBER_ROWS = 0;
                LEVEL_GOAL = 0;

                break;
            }
            case SQUARE: {
                STANDARD_CANDY_CRUSHED = 40;
                SPECIAL_CANDY_CRUSHED = 80;
                SEVERAL_CRUSHES_BONUS = 120;
                MAX_MOVEMENTS = 1;
                NUM_COLUMNS = 8;
                NUMBER_ROWS = 8;
                LEVEL_GOAL = 3500;

                break;
            }
            case CROSS: {
                STANDARD_CANDY_CRUSHED = 1;
                SPECIAL_CANDY_CRUSHED = 1;
                SEVERAL_CRUSHES_BONUS = 1;
                MAX_MOVEMENTS = 1;
                NUM_COLUMNS = 1;
                NUMBER_ROWS = 1;
                LEVEL_GOAL = 1;

                break;
            }
        }
    }

    public LevelType getLevelType() {
        return levelType;
    }

    public int getMaxMovements() {
        return MAX_MOVEMENTS;
    }

    public void setMaxMovements(int maxMovements) {
        this.MAX_MOVEMENTS = maxMovements;
    }

    public int getStandardCandyCrushed() {
        return STANDARD_CANDY_CRUSHED;
    }

    public void setStandardCandyCrushed(int standardCandyCrushed) {
        this.STANDARD_CANDY_CRUSHED = standardCandyCrushed;
    }

    public int getSpecialCandyCrushed() {
        return SPECIAL_CANDY_CRUSHED;
    }

    public void setSpecialCandyCrushed(int specialCandyCrushed) {
        this.SPECIAL_CANDY_CRUSHED = specialCandyCrushed;
    }

    public int getSeveralCrushesBonus() {
        return SEVERAL_CRUSHES_BONUS;
    }

    public void setSeveralCrushesBonus(int severalCrushesBonus) {
        this.SEVERAL_CRUSHES_BONUS = severalCrushesBonus;
    }

    public int getNumColumns() {
        return NUM_COLUMNS;
    }

    public void setNumColumns(int NUM_COLUMNS) {
        this.NUM_COLUMNS = NUM_COLUMNS;
    }

    public int getNumberRows() {
        return NUMBER_ROWS;
    }

    public void setNumberRows(int NUMBER_ROWS) {
        this.NUMBER_ROWS = NUMBER_ROWS;
    }

    public int getLevelGoal() {
        return LEVEL_GOAL;
    }

    public void setLevelGoal(int goal) {
        this.LEVEL_GOAL = goal;
    }

}
