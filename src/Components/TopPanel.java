package Components;

import Model.TopPanelModel;
import UI.TopPanelUI;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {

    private final TopPanelModel model;
    private final TopPanelUI view;

    private final JPanel scorePanel;
    private final JPanel livesPanel;
    private final JPanel goalPanel;

    private final FlowLayout mainLayout;
    private final BoxLayout scoreLayout;
    private final BoxLayout livesLayout;
    private final BoxLayout goalLayout;
    private final JLabel lblMovements;

    public TopPanel() {
        this.model = new TopPanelModel();
        this.view = new TopPanelUI();

        this.scorePanel = new JPanel();
        this.livesPanel = new JPanel();
        this.goalPanel = new JPanel();

        this.mainLayout = new FlowLayout();
        this.scoreLayout = new BoxLayout(scorePanel, BoxLayout.PAGE_AXIS);
        this.livesLayout = new BoxLayout(livesPanel, BoxLayout.PAGE_AXIS);
        this.goalLayout = new BoxLayout(goalPanel, BoxLayout.PAGE_AXIS);

        this.lblMovements = new JLabel();

        setUpUI();
    }

    private void setUpUI() {
        this.view.initializeUI(this);

        setUpScorePanel();
        setUpMovements();
        setUpLivesPanel();
        setUpGoalLayout();
    }

    private void setUpScorePanel() {
        this.scorePanel.setLayout(scoreLayout);
        this.scorePanel.setVisible(true);
        this.scorePanel.setPreferredSize(new Dimension(100, 50));

        JLabel lblScore = new JLabel("Score:");
        JLabel lblNumber = new JLabel("0000");

        this.scorePanel.add(lblScore);
        this.scorePanel.add(lblNumber);

        add(scorePanel);
    }

    private void setUpMovements() {
        this.lblMovements.setText("0");
        add(lblMovements);
    }

    private void setUpLivesPanel() {

    }

    private void setUpGoalLayout() {

    }

}
