import Components.BoardPanel;
import Components.TopPanel;
import utils.Utils;

import javax.swing.*;
import java.awt.*;

public class CandyCrush extends JFrame {

    private TopPanel topPanel;
    private BoardPanel boardPanel;

    public CandyCrush() {
        super(Utils.getAppName());
        setupUI();
    }

    private void setupUI() {
        setAppIcon();
        setPreferredSize(new Dimension(Utils.getWindowWidth(),
                Utils.getWindowHeight()));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setUpTopPanel();
        setUpBoardPanel();

        pack();
    }

    private void setAppIcon() {
        Image appIcon = Utils.generateImage(this,
                "resources/img/eye_ball.png");
        setIconImage(appIcon);
    }

    private void setUpTopPanel() {
        this.topPanel = new TopPanel();
        add(topPanel, BorderLayout.NORTH);
    }

    private void setUpBoardPanel() {
        this.boardPanel = new BoardPanel();
        add(boardPanel, BorderLayout.CENTER);
    }
}
