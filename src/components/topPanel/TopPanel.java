package components.topPanel;

import components.cards.CardGameplay;
import utils.Level;
import utils.dialogs.GameOverDialog;
import utils.dialogs.GoalReachedDialog;

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

        if (!endOfGame) {
            checkIfGoalIsMet(currentScore);
        }
    }

    public void setMaxMovements(int movements) {
        this.model.setAvailableMovements(movements);
        this.view.updateMaxMovements(movements);
    }

    public void oneMovementLess() {
        this.model.oneMovementLess();
        this.view.updateMaxMovements(this.model.getAvailableMovements());

        if (this.model.getAvailableMovements() == 0 && !endOfGame) {
            showGameOverDialog();
            container.enableBoardGrid(false);
        }
    }

    private void showGameOverDialog() {
        GameOverDialog gameOverDialog = new GameOverDialog();
        gameOverDialog.setVisible(true);
    }

    private void checkIfGoalIsMet(int currentScore) {
        if (currentScore >= getScoreGoal()) {
            endOfGame = true;
            container.updatePlayerProgress(currentScore);
            showGoalReachedDialog();
        }
    }

    private void showGoalReachedDialog() {
        GoalReachedDialog goalReachedDialog = new GoalReachedDialog(container);
        goalReachedDialog.setVisible(true);
    }

    public void reloadLevelInfo(Level level) {
        endOfGame = false;
        setCurrentScore(0);
        setScoreGoal(level.getLevelGoal());
        setMaxMovements(level.getMaxMovements());
    }

}
