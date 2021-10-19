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
    // TODO: Higher up the level goals
    private void setUpLevelCharacteristics() {
        switch (difficulty) {
            case 1: {
                LEVEL_TYPE = LevelType.SQUARE;
                STANDARD_CANDY_CRUSHED = 0;
                SPECIAL_CANDY_CRUSHED = 0;
                SEVERAL_CRUSHES_BONUS = 0;
                MAX_MOVEMENTS = 6;
                NUM_COLUMNS = 5;
                NUM_ROWS = 5;
                LEVEL_GOAL = 831;

                break;
            }
            case 2: {
                LEVEL_TYPE = LevelType.RECTANGLE;
                STANDARD_CANDY_CRUSHED = 0;
                SPECIAL_CANDY_CRUSHED = 0;
                SEVERAL_CRUSHES_BONUS = 0;
                MAX_MOVEMENTS = 6;
                NUM_COLUMNS = 8;
                NUM_ROWS = 5;
                LEVEL_GOAL = 942;

                break;
            }
            case 3:
                LEVEL_TYPE = LevelType.SQUARE;
                STANDARD_CANDY_CRUSHED = 0;
                SPECIAL_CANDY_CRUSHED = 0;
                SEVERAL_CRUSHES_BONUS = 0;
                MAX_MOVEMENTS = 8;
                NUM_COLUMNS = 7;
                NUM_ROWS = 7;
                LEVEL_GOAL = 1345;

                break;
            case 4:
                LEVEL_TYPE = LevelType.RECTANGLE;
                STANDARD_CANDY_CRUSHED = 0;
                SPECIAL_CANDY_CRUSHED = 0;
                SEVERAL_CRUSHES_BONUS = 0;
                MAX_MOVEMENTS = 8;
                NUM_COLUMNS = 10;
                NUM_ROWS = 7;
                LEVEL_GOAL = 1450;

                break;
            case 5:
                LEVEL_TYPE = LevelType.SQUARE;
                STANDARD_CANDY_CRUSHED = 0;
                SPECIAL_CANDY_CRUSHED = 0;
                SEVERAL_CRUSHES_BONUS = 0;
                MAX_MOVEMENTS = 10;
                NUM_COLUMNS = 9;
                NUM_ROWS = 9;
                LEVEL_GOAL = 2105;

                break;
            case 6:
                LEVEL_TYPE = LevelType.RECTANGLE;
                STANDARD_CANDY_CRUSHED = 0;
                SPECIAL_CANDY_CRUSHED = 0;
                SEVERAL_CRUSHES_BONUS = 0;
                MAX_MOVEMENTS = 10;
                NUM_COLUMNS = 12;
                NUM_ROWS = 9;
                LEVEL_GOAL = 2156;

                break;
            case 7:
                LEVEL_TYPE = LevelType.SQUARE;
                STANDARD_CANDY_CRUSHED = 0;
                SPECIAL_CANDY_CRUSHED = 0;
                SEVERAL_CRUSHES_BONUS = 0;
                MAX_MOVEMENTS = 12;
                NUM_COLUMNS = 11;
                NUM_ROWS = 11;
                LEVEL_GOAL = 2560;

                break;
            case 8:
                LEVEL_TYPE = LevelType.RECTANGLE;
                STANDARD_CANDY_CRUSHED = 0;
                SPECIAL_CANDY_CRUSHED = 0;
                SEVERAL_CRUSHES_BONUS = 0;
                MAX_MOVEMENTS = 12;
                NUM_COLUMNS = 14;
                NUM_ROWS = 11;
                LEVEL_GOAL = 2780;

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

    public int getStandardCandyCrushed() {
        return STANDARD_CANDY_CRUSHED;
    }

    public int getSpecialCandyCrushed() {
        return SPECIAL_CANDY_CRUSHED;
    }

    public int getSeveralCrushesBonus() {
        return SEVERAL_CRUSHES_BONUS;
    }

    public int getNumColumns() {
        return NUM_COLUMNS;
    }

    public int getNumRows() {
        return NUM_ROWS;
    }

    public int getLevelGoal() {
        return LEVEL_GOAL;
    }

}
