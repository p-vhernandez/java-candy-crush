package utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * https://stackoverflow.com/a/19106116/9772035
 */
public class Shadow extends JPanel {

    public Shadow() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBackground(Color.BLACK);
        setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 150);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
        g2d.fillRect(10, 10, getWidth(), getHeight());
        g2d.dispose();
    }

}
