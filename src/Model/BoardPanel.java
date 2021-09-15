package Model;

import javax.swing.*;

public class BoardPanel extends JPanel {
    private BoardPanelModel model;
    private BoardPanelUI ui;

    public BoardPanel() {
        this.model = new BoardPanelModel();
        this.ui = new BoardPanelUI();
    }
}
