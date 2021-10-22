package utils.buttons;

import components.cards.CardLevelChoice;
import utils.Utils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LevelButton extends JButton {

    private final CardLevelChoice cardLevelChoice;

    private String label;

    private final boolean unlocked;

    private final int index;
    private final int width = 80, height = 80;
    private Color background;

    private final Font font;

    public LevelButton(Object object, int level, int index, boolean unlocked) {
        this.cardLevelChoice = (CardLevelChoice) object;

        this.label = String.valueOf(level);
        this.index = index;
        this.unlocked = unlocked;
        this.background = Utils.halloweenOrange;
        this.font = Utils.generateFont(object, "../../resources/font/shlop-rg.ttf");

        Border emptyBorder = BorderFactory.createEmptyBorder();
        this.setBorder(emptyBorder);

        setEnabled(isUnlocked());
        initializeListeners();
    }

    private void initializeListeners() {
        if (isUnlocked()) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    background = Utils.halloweenOrangeHover;
                    repaint();
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    background = Utils.halloweenOrange;
                    repaint();
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    cardLevelChoice.selectLevel(index);
                    cardLevelChoice.flipCard();
                }
            });
        }
    }

    public String getText() {
        return label;
    }

    public void setText(String label) {
        this.label = label;
    }

    public boolean isUnlocked() {
        return unlocked;
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
        g.setPaint(Utils.darkBackground);
        g.fillRect(0, 0, width, height);

        if (unlocked) {
            g.setPaint(background);
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
