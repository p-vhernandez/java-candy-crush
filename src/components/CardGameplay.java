package components;

import components.grid.BoardGrid;
import main.CandyCrush;
import utils.Level;
import utils.Utils;
import utils.helpers.LevelType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CardGameplay extends JPanel {

    private static TopPanel topPanel;
    private BoardGrid grid;

    public CardGameplay(Level level) {
        setupUI();
    }

    private void setupUI() {
        setPreferredSize(new Dimension(Utils.getWindowWidth(),
                Utils.getWindowHeight()));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Utils.darkBackground);

        setUpTopPanel();
        setUpBoardPanel();
    }

    private void setUpBoardPanel() {
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new BoxLayout(boardPanel, BoxLayout.Y_AXIS));
        boardPanel.setBorder(new EmptyBorder(40, 0, 0, 0));
        boardPanel.setBackground(Utils.darkBackground);
        setPreferredSize(new Dimension(Utils.getBoardPanelWidth(), Utils.getBoardPanelHeight()));

        add(boardPanel);

        Level selectedLevel = new Level(LevelType.SQUARE);
        grid = new BoardGrid(selectedLevel);
        FlowLayout gridLayout = new FlowLayout();
        gridLayout.setVgap(0);
        gridLayout.setHgap(0);
        grid.setLayout(gridLayout);

        setGoal(selectedLevel.getLevelGoal());
        setMaxMovements(selectedLevel.getMaxMovements());

        boardPanel.add(grid);
    }

    private void setUpTopPanel() {
        topPanel = new TopPanel(this);
        add(topPanel, BorderLayout.NORTH);
    }

    public void addScore(int score) {
        topPanel.setLblScoreNumber(topPanel.getLblScoreNumber() + score);
    }

    public void setGoal(int goal) {
        topPanel.setLblGoalNumber(goal);
    }

    public void setMaxMovements(int maxMovements) {
        topPanel.setMaxMovements(maxMovements);
    }

    public static void oneMovementLess() {
        topPanel.oneMovementLess();
    }

    public static int getMovementsLeft() {
        return topPanel.getMovementsLeft();
    }

    public void enableBoardGrid(boolean enabled) {
        grid.enableBoardGrid(enabled);
    }

    public static void updateScore(int sequence) {
        int currentScore = topPanel.getLblScoreNumber();
        topPanel.setLblScoreNumber(currentScore + sequence * 40);
    }
}
