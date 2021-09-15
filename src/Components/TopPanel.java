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
        this.scoreLayout = new BoxLayout(scorePanel, BoxLayout.X_AXIS);
        this.livesLayout = new BoxLayout(livesPanel, BoxLayout.X_AXIS);
        this.goalLayout = new BoxLayout(goalPanel, BoxLayout.X_AXIS);

        this.lblMovements = new JLabel();

        setUpUI();
    }

    private void setUpUI() {
        this.view.initializeUI(this);
    }

}
