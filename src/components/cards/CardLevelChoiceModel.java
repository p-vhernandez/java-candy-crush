package components.cards;

import utils.Player;
import utils.buttons.LevelButton;

import java.util.ArrayList;

public class CardLevelChoiceModel {

    private ArrayList<LevelButton> levelButtons;

    private Player player;

    public CardLevelChoiceModel() {
        this.levelButtons = new ArrayList<>();
    }

    protected ArrayList<LevelButton> getLevelButtons() {
        return levelButtons;
    }

    protected void addLevelButton(LevelButton button) {
        this.levelButtons.add(button);
    }

    protected void resetLevelButtons() {
        this.levelButtons = new ArrayList<>();
    }

    public void setLevelButtons(ArrayList<LevelButton> levelButtons) {
        this.levelButtons = levelButtons;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
