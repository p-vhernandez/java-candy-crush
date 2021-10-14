package components;

import utils.Utils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class LevelButton extends JButton {

    private String label;

    private boolean unlocked;

    private int index;
    private final int width = 80, height = 80, arc = 200;

    private final Font font;

    public LevelButton(Object object, String label, int index, boolean unlocked) {
        this.label = label;
        this.index = index;
        this.unlocked = unlocked;
        this.font = Utils.generateFont(object, "../resources/font/shlop-rg.ttf");

        Border emptyBorder = BorderFactory.createEmptyBorder();
        this.setBorder(emptyBorder);
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

        if (unlocked) {
            g.setPaint(Utils.halloweenOrange);
            g.fillOval(0, 0, width, height);
            g.setPaint(Utils.darkBackground);
        } else {
            g.setPaint(Color.lightGray);
            g.fillOval(0, 0, width, height);
            g.setPaint(Color.white);
        }

        g.setFont(font.deriveFont(48f));
        g.drawString(label, (width - g.getFontMetrics().stringWidth(label)) / 2, height - 25);
    }
}
