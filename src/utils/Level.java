package utils;

import utils.helpers.LevelType;

public class Level {

    private final int difficulty;

    private int MAX_MOVEMENTS;
    private int STANDARD_CANDY_CRUSHED,
            SPECIAL_CANDY_CRUSHED,
            SEVERAL_CRUSHES_BONUS;

    private int NUM_COLUMNS,
            NUM_ROWS;

    private int LEVEL_GOAL;

    private LevelType LEVEL_TYPE;

    public Level(int difficulty) {
        this.difficulty = difficulty;

        setUpLevelCharacteristics();
    }

    // TODO: set up all level's characteristics
    private void setUpLevelCharacteristics() {
        switch (difficulty) {
            case 1: {
                LEVEL_TYPE = LevelType.SQUARE;
                STANDARD_CANDY_CRUSHED = 0;
                SPECIAL_CANDY_CRUSHED = 0;
                SEVERAL_CRUSHES_BONUS = 0;
                MAX_MOVEMENTS = 3;
                NUM_COLUMNS = 4;
                NUM_ROWS = 4;
                LEVEL_GOAL = 230;

                break;
            }
            case 2: {
                LEVEL_TYPE = LevelType.RECTANGLE;
                STANDARD_CANDY_CRUSHED = 0;
                SPECIAL_CANDY_CRUSHED = 0;
                SEVERAL_CRUSHES_BONUS = 0;
                MAX_MOVEMENTS = 3;
                NUM_COLUMNS = 4;
                NUM_ROWS = 4;
                LEVEL_GOAL = 470;

                break;
            }
            case 3: {
                LEVEL_TYPE = LevelType.CROSS;
                STANDARD_CANDY_CRUSHED = 0;
                SPECIAL_CANDY_CRUSHED = 0;
                SEVERAL_CRUSHES_BONUS = 0;
                MAX_MOVEMENTS = 3;
                NUM_COLUMNS = 4;
                NUM_ROWS = 4;
                LEVEL_GOAL = 470;

                break;
            }
            case 4:
                LEVEL_TYPE = LevelType.SQUARE;
                STANDARD_CANDY_CRUSHED = 0;
                SPECIAL_CANDY_CRUSHED = 0;
                SEVERAL_CRUSHES_BONUS = 0;
                MAX_MOVEMENTS = 5;
                NUM_COLUMNS = 6;
                NUM_ROWS = 6;
                LEVEL_GOAL = 810;

                break;
            case 5:
                LEVEL_TYPE = LevelType.RECTANGLE;
                STANDARD_CANDY_CRUSHED = 0;
                SPECIAL_CANDY_CRUSHED = 0;
                SEVERAL_CRUSHES_BONUS = 0;
                MAX_MOVEMENTS = 5;
                NUM_COLUMNS = 6;
                NUM_ROWS = 6;
                LEVEL_GOAL = 810;

                break;
            case 6:
                LEVEL_TYPE = LevelType.CROSS;
                STANDARD_CANDY_CRUSHED = 0;
                SPECIAL_CANDY_CRUSHED = 0;
                SEVERAL_CRUSHES_BONUS = 0;
                MAX_MOVEMENTS = 5;
                NUM_COLUMNS = 6;
                NUM_ROWS = 6;
                LEVEL_GOAL = 810;

                break;
            case 7:
                LEVEL_TYPE = LevelType.SQUARE;
                STANDARD_CANDY_CRUSHED = 0;
                SPECIAL_CANDY_CRUSHED = 0;
                SEVERAL_CRUSHES_BONUS = 0;
                MAX_MOVEMENTS = 5;
                NUM_COLUMNS = 8;
                NUM_ROWS = 8;
                LEVEL_GOAL = 1210;

                break;
            case 8:
                LEVEL_TYPE = LevelType.RECTANGLE;
                STANDARD_CANDY_CRUSHED = 0;
                SPECIAL_CANDY_CRUSHED = 0;
                SEVERAL_CRUSHES_BONUS = 0;
                MAX_MOVEMENTS = 5;
                NUM_COLUMNS = 8;
                NUM_ROWS = 8;
                LEVEL_GOAL = 1210;

                break;
            case 9:
                LEVEL_TYPE = LevelType.CROSS;
                STANDARD_CANDY_CRUSHED = 0;
                SPECIAL_CANDY_CRUSHED = 0;
                SEVERAL_CRUSHES_BONUS = 0;
                MAX_MOVEMENTS = 5;
                NUM_COLUMNS = 8;
                NUM_ROWS = 8;
                LEVEL_GOAL = 1210;

                break;
            case 10:
                LEVEL_TYPE = LevelType.SQUARE;
                STANDARD_CANDY_CRUSHED = 0;
                SPECIAL_CANDY_CRUSHED = 0;
                SEVERAL_CRUSHES_BONUS = 0;
                MAX_MOVEMENTS = 5;
                NUM_COLUMNS = 10;
                NUM_ROWS = 10;
                LEVEL_GOAL = 1430;

                break;
            case 11:
                LEVEL_TYPE = LevelType.RECTANGLE;
                STANDARD_CANDY_CRUSHED = 0;
                SPECIAL_CANDY_CRUSHED = 0;
                SEVERAL_CRUSHES_BONUS = 0;
                MAX_MOVEMENTS = 5;
                NUM_COLUMNS = 10;
                NUM_ROWS = 10;
                LEVEL_GOAL = 1430;

                break;
            case 12:
                LEVEL_TYPE = LevelType.CROSS;
                STANDARD_CANDY_CRUSHED = 0;
                SPECIAL_CANDY_CRUSHED = 0;
                SEVERAL_CRUSHES_BONUS = 0;
                MAX_MOVEMENTS = 5;
                NUM_COLUMNS = 10;
                NUM_ROWS = 10;
                LEVEL_GOAL = 1430;

                break;
            case 13:
                LEVEL_TYPE = LevelType.SQUARE;
                STANDARD_CANDY_CRUSHED = 0;
                SPECIAL_CANDY_CRUSHED = 0;
                SEVERAL_CRUSHES_BONUS = 0;
                MAX_MOVEMENTS = 5;
                NUM_COLUMNS = 12;
                NUM_ROWS = 12;
                LEVEL_GOAL = 1825;

                break;
            case 14:
                LEVEL_TYPE = LevelType.RECTANGLE;
                STANDARD_CANDY_CRUSHED = 0;
                SPECIAL_CANDY_CRUSHED = 0;
                SEVERAL_CRUSHES_BONUS = 0;
                MAX_MOVEMENTS = 5;
                NUM_COLUMNS = 12;
                NUM_ROWS = 12;
                LEVEL_GOAL = 1825;

                break;
            case 15:
                LEVEL_TYPE = LevelType.CROSS;
                STANDARD_CANDY_CRUSHED = 0;
                SPECIAL_CANDY_CRUSHED = 0;
                SEVERAL_CRUSHES_BONUS = 0;
                MAX_MOVEMENTS = 5;
                NUM_COLUMNS = 12;
                NUM_ROWS = 12;
                LEVEL_GOAL = 1825;

                break;
        }
    }

    public int getDifficulty() {
        return difficulty;
    }

    public LevelType getLevelType() {
        return LEVEL_TYPE;
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

    public int getNumRows() {
        return NUM_ROWS;
    }

    public void setNumRows(int NUMBER_ROWS) {
        this.NUM_ROWS = NUMBER_ROWS;
    }

    public int getLevelGoal() {
        return LEVEL_GOAL;
    }

    public void setLevelGoal(int goal) {
        this.LEVEL_GOAL = goal;
    }

}
