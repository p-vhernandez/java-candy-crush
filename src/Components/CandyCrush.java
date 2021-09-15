package Components;

import Components.BoardGrid;

import javax.swing.*;
import java.awt.*;

public class CandyCrush extends JFrame {

    public CandyCrush() {
        super("Candy Crush");
        setupUI();
    }

    private void setupUI() {
        try {
            Image appIcon = Toolkit.getDefaultToolkit().getImage(getClass()
                    .getResource("../resources/img/eye_ball.png"));
            setIconImage(appIcon);
            setPreferredSize(new Dimension(600, 400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        BoardPanel panel = new BoardPanel();
        add(panel);
        pack();

    }
}
