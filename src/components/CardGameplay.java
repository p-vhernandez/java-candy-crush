package components;

import components.grid.BoardGrid;
import utils.Level;
import utils.Utils;
import utils.helpers.LevelType;

import javax.swing.*;
import javax.swing.border.Border;
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
        Border blackline = BorderFactory.createLineBorder(Color.WHITE);
        setBorder(blackline);

        setUpTopPanel();
        setUpBoardPanel();
    }

    private void setUpBoardPanel() {
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new BoxLayout(boardPanel, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(600, 400));

        add(boardPanel);

        Level selectedLevel = new Level(LevelType.CROSS);
        grid = new BoardGrid(selectedLevel);

        setGoal(selectedLevel.getLevelGoal());
        setMaxMovements(selectedLevel.getMaxMovements());

        boardPanel.add(grid);
    }

    private void setUpTopPanel() {
        topPanel = new TopPanel(this);
        add(topPanel, BorderLayout.NORTH);
        add(Box.createRigidArea(new Dimension(0, 40)));
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
