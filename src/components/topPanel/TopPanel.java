package components.topPanel;

import components.cards.CardGameplay;
import utils.dialogs.GameOverDialog;
import utils.dialogs.GoalReachedDialog;

import javax.swing.*;

public class TopPanel extends JPanel {

    private final TopPanelModel model;
    private final TopPanelUI view;

    private final CardGameplay container;

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

    public void setLblScoreNumber(int currentScore) {
        this.model.setCurrentScore(currentScore);
        this.view.updateCurrentScore(currentScore);
        checkIfGoalIsMet(currentScore);
    }

    public void setMaxMovements(int movements) {
        this.model.setAvailableMovements(movements);
        this.view.updateMaxMovements(movements);
    }

    public void oneMovementLess() {
        this.model.oneMovementLess();
        this.view.updateMaxMovements(this.model.getAvailableMovements());

        if (this.model.getAvailableMovements() == 0) {
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
            container.updatePlayerProgress(currentScore);
            showGoalReachedDialog();
        }
    }

    private void showGoalReachedDialog() {
        GoalReachedDialog goalReachedDialog = new GoalReachedDialog(container);
        goalReachedDialog.setVisible(true);
    }

}
