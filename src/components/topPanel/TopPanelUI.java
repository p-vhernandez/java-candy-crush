package components.topPanel;

import utils.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TopPanelUI {

    private final TopPanel controller;

    private final JPanel scorePanel, goalPanel;
    private final BoxLayout scoreLayout, goalLayout;
    private final JLabel lblMovements, lblGoalNumber, lblScoreNumber;

    public TopPanelUI(TopPanel controller) {
        this.controller = controller;

        this.scorePanel = new JPanel();
        this.goalPanel = new JPanel();

        this.scoreLayout = new BoxLayout(scorePanel, BoxLayout.PAGE_AXIS);
        this.goalLayout = new BoxLayout(goalPanel, BoxLayout.PAGE_AXIS);

        this.lblMovements = new JLabel();
        this.lblGoalNumber = new JLabel();
        this.lblScoreNumber = new JLabel();
    }

    protected void initializeUI() {
        controller.setBackground(Utils.darkBackground);
        controller.setPreferredSize(new Dimension(Utils.getWindowWidth(),
                Utils.getTopBarHeight()));
        controller.setMaximumSize(controller.getPreferredSize());

        setUpScorePanel();
        setUpMovements();
        setUpGoalLayout();
    }

    private void setUpScorePanel() {
        this.scorePanel.setLayout(scoreLayout);
        this.scorePanel.setBackground(Utils.darkBackground);
        this.scorePanel.setVisible(true);
        this.scorePanel.setBorder(new LineBorder(Utils.halloweenOrange, 3));
        this.scorePanel.setPreferredSize(new Dimension(Utils.getRestTopPanelComponentsWidth(),
                Utils.getTopBarComponentsHeight()));

        JLabel lblScore = new JLabel("Score:");
        lblScore.setForeground(Color.white);
        lblScore.setBorder(new EmptyBorder(0, 10, 0, 10));
        lblScoreNumber.setText("0000");
        lblScoreNumber.setForeground(Color.white);
        lblScoreNumber.setBorder(new EmptyBorder(0, 10, 0, 10));

        Utils.setCustomFont(this, lblScore,
                "../../resources/font/caramel-rg.ttf", 28f, Font.BOLD);
        Utils.setCustomFont(this, lblScoreNumber,
                "../../resources/font/creepster-rg.ttf", 32f, Font.PLAIN);

        this.scorePanel.add(lblScore);
        this.scorePanel.add(lblScoreNumber);

        controller.add(scorePanel);
    }

    private void setUpMovements() {
        this.lblMovements.setText("0");
        this.lblMovements.setForeground(Color.white);
        this.lblMovements.setHorizontalAlignment(SwingConstants.CENTER);
        this.lblMovements.setVerticalAlignment(SwingConstants.CENTER);
        this.lblMovements.setBorder(new LineBorder(Utils.halloweenOrange, 3));
        this.lblMovements.setPreferredSize(new Dimension(Utils.getMovementsPanelWidth(),
                Utils.getTopBarComponentsHeight()));

        Utils.setCustomFont(this, lblMovements,
                "../../resources/font/creepster-rg.ttf", 40f, Font.PLAIN);
        controller.add(lblMovements);
    }

    private void setUpGoalLayout() {
        this.goalPanel.setLayout(goalLayout);
        this.goalPanel.setVisible(true);
        this.goalPanel.setBackground(Utils.darkBackground);
        this.goalPanel.setBorder(new LineBorder(Utils.halloweenOrange, 3));
        this.goalPanel.setPreferredSize(new Dimension(Utils.getRestTopPanelComponentsWidth(),
                Utils.getTopBarComponentsHeight()));

        JLabel lblGoal = new JLabel("Target:");
        lblGoal.setForeground(Color.white);
        lblGoal.setBorder(new EmptyBorder(0, 10, 0, 10));
        lblGoalNumber.setText("6500");
        lblGoalNumber.setForeground(Color.white);
        lblGoalNumber.setBorder(new EmptyBorder(0, 10, 0, 10));

        Utils.setCustomFont(this, lblGoal,
                "../../resources/font/caramel-rg.ttf", 28f, Font.BOLD);
        Utils.setCustomFont(this, lblGoalNumber,
                "../../resources/font/creepster-rg.ttf", 32f, Font.PLAIN);

        this.goalPanel.add(lblGoal);
        this.goalPanel.add(lblGoalNumber);

        controller.add(goalPanel);
    }

    protected void updateScoreGoal(int scoreGoal) {
        this.lblGoalNumber.setText(String.valueOf(scoreGoal));
    }

    protected void updateCurrentScore(int currentScore) {
        this.lblScoreNumber.setText(String.valueOf(currentScore));
    }

    protected void updateMaxMovements(int movements) {
        this.lblMovements.setText(String.valueOf(movements));
    }

}
