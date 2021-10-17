package components.topPanel;

public class TopPanelModel {

    private int availableMovements,
            scoreGoal,
            currentScore;

    // Methods
    protected int getScoreGoal() {
        return scoreGoal;
    }

    protected void setScoreGoal(int scoreGoal) {
        this.scoreGoal = scoreGoal;
    }

    protected int getCurrentScore() {
        return currentScore;
    }

    protected void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    protected int getAvailableMovements() {
        return availableMovements;
    }

    protected void setAvailableMovements(int maxMovements) {
        this.availableMovements = maxMovements;
    }

    protected void oneMovementLess() {
        this.availableMovements--;
    }

}
