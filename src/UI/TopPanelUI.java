package UI;

import Components.TopPanel;

import java.awt.*;

public class TopPanelUI {

    private TopPanel topPanel;

    public TopPanelUI() {

    }

    public void initializeUI(TopPanel panel) {
        this.topPanel = panel;
        this.topPanel.setBackground(Color.cyan);
    }

    public Dimension getMaximumSize() { return getPreferredSize(); }
    public Dimension getMinimumSize() { return getPreferredSize(); }
    public Dimension getPreferredSize() { return new Dimension(300,50); }

}
