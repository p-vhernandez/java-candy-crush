package Model;

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

            BoardGrid grid = new BoardGrid(8);
            add(grid);

            pack();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
