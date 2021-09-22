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
    private final JLabel lblGoalNumber;
    private final JLabel lblScoreNumber;

    public TopPanel() {
        this.scorePanel = new JPanel();
        this.goalPanel = new JPanel();

        this.scoreLayout = new BoxLayout(scorePanel, BoxLayout.PAGE_AXIS);
        this.goalLayout = new BoxLayout(goalPanel, BoxLayout.PAGE_AXIS);

        this.lblMovements = new JLabel();
        this.lblGoalNumber = new JLabel();
        this.lblScoreNumber = new JLabel();

        setUpUI();
    }

    private void setUpUI() {
        setBackground(Color.pink);
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
        lblScoreNumber.setText("0000");

        setCustomFont(lblScore, "../resources/font/caramel-rg.ttf", 22f, Font.BOLD);
        setCustomFont(lblScoreNumber, "../resources/font/creepster-rg.ttf", 28f, Font.PLAIN);

        this.scorePanel.add(lblScore);
        this.scorePanel.add(lblScoreNumber);

        add(scorePanel);
    }

    private void setUpMovements() {
        this.lblMovements.setText("0");
        this.lblMovements.setHorizontalAlignment(SwingConstants.CENTER);
        this.lblMovements.setVerticalAlignment(SwingConstants.CENTER);
        this.lblMovements.setPreferredSize(new Dimension(Utils.getMovementsPanelWidth(),
                Utils.getTopBarComponentsHeight()));

        setCustomFont(lblMovements, "../resources/font/creepster-rg.ttf", 32f, Font.PLAIN);
        add(lblMovements);
    }

    private void setUpGoalLayout() {
        this.goalPanel.setLayout(goalLayout);
        this.goalPanel.setVisible(true);
        this.goalPanel.setPreferredSize(new Dimension(Utils.getRestTopPanelComponentsWidth(),
                Utils.getTopBarComponentsHeight()));

        JLabel lblGoal = new JLabel("Target:");
        lblGoalNumber.setText("6500");

        setCustomFont(lblGoal, "../resources/font/caramel-rg.ttf", 22f, Font.BOLD);
        setCustomFont(lblGoalNumber, "../resources/font/creepster-rg.ttf", 28f, Font.PLAIN);

        this.goalPanel.add(lblGoal);
        this.goalPanel.add(lblGoalNumber);

        add(goalPanel);
    }

    private void setCustomFont(JLabel label, String path,
                               float fontSize, int fontStyle) {
        Font customFom = Utils.generateFont(this, path);
        if (customFom != null) {
            label.setFont(customFom.deriveFont(fontStyle, fontSize));
        }
    }

    public int getLblGoalNumber() {
        return Integer.parseInt(lblGoalNumber.getText());
    }

    public void setLblGoalNumber(int goalNumber) {
        lblGoalNumber.setText(String.valueOf(goalNumber));
    }

    public int getLblScoreNumber() {
        return Integer.parseInt(lblScoreNumber.getText());
    }

    public void setLblScoreNumber(int scoreNumber) {
        lblScoreNumber.setText(String.valueOf(scoreNumber));
    }

}
