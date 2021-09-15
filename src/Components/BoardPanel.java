package Components;

import Model.BoardPanelModel;
import UI.BoardPanelUI;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    private BoardPanelModel model;
    private BoardPanelUI ui;

    public BoardPanel() {
        this.model = new BoardPanelModel();
        this.ui = new BoardPanelUI();
        this.ui.intitializeUI(this);
    }

    //Layout
    public Dimension getMaximumSize() { return getPreferredSize(); }
    public Dimension getMinimumSize() { return getPreferredSize(); }
    public Dimension getPreferredSize() { return new Dimension(300,200); }
}
