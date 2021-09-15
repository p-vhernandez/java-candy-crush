import Components.BoardPanel;
import Components.TopPanel;

import javax.swing.*;
import java.awt.*;

public class CandyCrush extends JFrame {

    private TopPanel topPanel;
    private BoardPanel boardPanel;

    public CandyCrush() {
        super("Candy Crush");
        setupUI();
    }

    private void setupUI() {
        setAppIcon();
        setPreferredSize(new Dimension(600, 400));

        setUpTopPanel();
        setUpBoardPanel();

        pack();
    }

    private void setAppIcon() {
        Image appIcon = Toolkit.getDefaultToolkit().getImage(getClass()
                .getResource("../resources/img/eye_ball.png"));
        setIconImage(appIcon);
    }

    private void setUpTopPanel() {
        this.topPanel = new TopPanel();
        add(topPanel, BorderLayout.NORTH);
    }

    private void setUpBoardPanel() {
        this.boardPanel = new BoardPanel();
        add(boardPanel);
    }
}
