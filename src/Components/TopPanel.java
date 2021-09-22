package Components;

import utils.Utils;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {

    private final JPanel scorePanel;
    private final JPanel goalPanel;

    private final BoxLayout scoreLayout;
    private final BoxLayout goalLayout;
    private final JLabel lblMovements;

    public TopPanel() {
        this.scorePanel = new JPanel();
        this.goalPanel = new JPanel();

        this.scoreLayout = new BoxLayout(scorePanel, BoxLayout.PAGE_AXIS);
        this.goalLayout = new BoxLayout(goalPanel, BoxLayout.PAGE_AXIS);

        this.lblMovements = new JLabel();

        setUpUI();
    }

    private void setUpUI() {
        setBackground(Color.cyan);
        setPreferredSize(new Dimension(Utils.getWindowWidth(),
                Utils.getTopBarHeight()));

        setUpScorePanel();
        setUpMovements();
        setUpGoalLayout();
    }

    private void setUpScorePanel() {
        this.scorePanel.setLayout(scoreLayout);
        this.scorePanel.setVisible(true);
        this.scorePanel.setPreferredSize(new Dimension(Utils.getRestTopPanelComponentsWidth(),
                Utils.getTopBarComponentsHeight()));

        JLabel lblScore = new JLabel("Score:");
        JLabel lblNumber = new JLabel("0000");

        this.scorePanel.add(lblScore);
        this.scorePanel.add(lblNumber);

        add(scorePanel);
    }

    private void setUpMovements() {
        this.lblMovements.setText("0");
        this.lblMovements.setHorizontalAlignment(SwingConstants.CENTER);
        this.lblMovements.setVerticalAlignment(SwingConstants.CENTER);
        this.lblMovements.setPreferredSize(new Dimension(Utils.getMovementsPanelWidth(),
                Utils.getTopBarComponentsHeight()));

        add(lblMovements);
    }

    private void setUpGoalLayout() {
        this.goalPanel.setLayout(goalLayout);
        this.goalPanel.setVisible(true);
        this.goalPanel.setPreferredSize(new Dimension(Utils.getRestTopPanelComponentsWidth(),
                Utils.getTopBarComponentsHeight()));

        JLabel lblGoal = new JLabel("Target:");
        JLabel lblGoalNumber = new JLabel("6500");

        this.goalPanel.add(lblGoal);
        this.goalPanel.add(lblGoalNumber);

        add(goalPanel);
    }

}
