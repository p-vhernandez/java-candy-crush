package components.topPanel;

import components.cards.CardGameplay;
import utils.Level;

import javax.swing.*;

public class TopPanel extends JPanel {

    private final TopPanelModel model;
    private final TopPanelUI view;

    private final CardGameplay container;

    private boolean endOfGame = false;

    public TopPanel(CardGameplay cardGameplay) {
        this.model = new TopPanelModel();
        this.view = new TopPanelUI(this);

        this.container = cardGameplay;

        initialize();
    }

    private void initialize() {
        this.view.initializeUI();
    }

    public int getScoreGoal() {
        return this.model.getScoreGoal();
    }

    public void setScoreGoal(int scoreGoal) {
        this.model.setScoreGoal(scoreGoal);
        this.view.updateScoreGoal(scoreGoal);
    }

    public int getCurrentScore() {
        return this.model.getCurrentScore();
    }

    public void setCurrentScore(int currentScore) {
        this.model.setCurrentScore(currentScore);
        this.view.updateCurrentScore(currentScore);
    }

    public void setMaxMovements(int movements) {
        this.model.setAvailableMovements(movements);
        this.view.updateMaxMovements(movements);
    }

    public void oneMovementLess() {
        this.model.oneMovementLess();
        this.view.updateMaxMovements(this.model.getAvailableMovements());
    }

    public boolean checkPlayerWin() {
        if (!endOfGame) {
            return checkIfGoalIsMet(getCurrentScore());
        } else {
            return false;
        }
    }

    public boolean checkPlayerLose() {
        if (this.model.getAvailableMovements() == 0 && !endOfGame) {
            container.enableBoardGrid(false);
            return true;
        } else {
            return false;
        }
    }

    private boolean checkIfGoalIsMet(int currentScore) {
        if (currentScore >= getScoreGoal()) {
            endOfGame = true;
            container.updatePlayerProgress(currentScore);
            return true;
        } else {
            return false;
        }
    }

    public void reloadLevelInfo(Level level) {
        endOfGame = false;
        setCurrentScore(0);
        setScoreGoal(level.getLevelGoal());
        setMaxMovements(level.getMaxMovements());
    }

}
