package components;

import utils.Utils;

import javax.swing.*;
import java.awt.*;

public class LevelButton extends JButton {

    private String label;
    private int index;
    private boolean unlocked;
    private int width = 100;
    private int height = 80;
    private int arc = 5;

    public LevelButton(String label, int index, boolean unlocked) {
        this.label = label;
        this.index = index;
        this.unlocked = unlocked;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    public void paintComponent(Graphics g1d) {
        Graphics2D g = (Graphics2D) g1d;

        g.setPaint(Color.white);
        g.fillRoundRect(0,0, width, height, arc, arc);
        g.setPaint(Color.black);
        g.drawString(label, (width - g.getFontMetrics().stringWidth(label)) / 2, height/2);
    }
}
