package Helpers;

public class Level {

    private final LevelType levelType;

    private int MAX_MOVEMENTS;
    private int STANDARD_CANDY_CRUSHED,
            SPECIAL_CANDY_CRUSHED,
            SEVERAL_CRUSHES_BONUS;

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
            }
            case SQUARE: {
                STANDARD_CANDY_CRUSHED = 40;
                SPECIAL_CANDY_CRUSHED = 80;
                SEVERAL_CRUSHES_BONUS = 120;
                MAX_MOVEMENTS = 26;
            }
            case CROSS: {
                STANDARD_CANDY_CRUSHED = 0;
                SPECIAL_CANDY_CRUSHED = 0;
                SEVERAL_CRUSHES_BONUS = 0;
                MAX_MOVEMENTS = 0;
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

}
