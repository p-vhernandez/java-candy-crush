package components.cards;

import components.TopPanel;
import components.grid.BoardGrid;
import main.CandyCrush;
import utils.Level;
import utils.helpers.CardType;

import javax.swing.*;
import java.awt.*;

public class CardGameplay extends JPanel {

    private final CandyCrush container;

    private final CardGamePlayModel model;
    private final CardGamePlayUI view;

    private static TopPanel topPanel;

    public CardGameplay(CandyCrush container) {
        this.container = container;

        this.model = new CardGamePlayModel();
        this.view = new CardGamePlayUI(this);

        initialize();
    }

    private void initialize() {
        view.initializeUI();
    }

    public void loadGame(Level level) {
        this.model.setLevel(level);

        removeAll();

        setUpTopPanel();
        view.setUpBoardPanel();
        view.setUpBottomPanel();
    }

    private void setUpTopPanel() {
        topPanel = new TopPanel(this);
        add(topPanel, BorderLayout.NORTH);
    }

    protected void restartLevel() {
        // TODO
    }

    protected void flipCard(CardType origin, CardType destination) {
        container.flipCard(origin, destination);
    }

    protected void createNewGrid() {
        this.model.createNewGrid();
    }

    protected void setGoal(int goal) {
        topPanel.setLblGoalNumber(goal);
    }

    protected void setMaxMovements(int maxMovements) {
        topPanel.setMaxMovements(maxMovements);
    }

    protected BoardGrid getBoardGrid() {
        return this.model.getGrid();
    }

    protected Level getLevel() {
        return this.model.getLevel();
    }

    public static void oneMovementLess() {
        topPanel.oneMovementLess();
    }

    public static int getMovementsLeft() {
        return topPanel.getMovementsLeft();
    }

    public void enableBoardGrid(boolean enabled) {
        this.model.enableGrid(enabled);
    }

    public static void updateScore(int sequence) {
        int currentScore = topPanel.getLblScoreNumber();
        topPanel.setLblScoreNumber(currentScore + sequence * 40);
    }
}
