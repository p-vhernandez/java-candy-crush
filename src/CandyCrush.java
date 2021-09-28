import Components.BoardGrid;
import Components.LoadingDialog;
import Components.TopPanel;
import Helpers.LevelType;
import utils.Utils;

import javax.swing.*;
import java.awt.*;

public class CandyCrush extends JFrame {

    private TopPanel topPanel;
    private LoadingDialog loadingDialog;

    public CandyCrush() {
        super(Utils.getAppName());

        showLoading();
        setupUI();
    }

    private void setupUI() {
        setAppIcon();
        setFrameVisuals();
        setUpTopPanel();
        setUpBoardPanel();

        pack();
    }

    private void setAppIcon() {
        Image appIcon = Utils.generateImage(this,
                "resources/img/EYEBALL.png");
        setIconImage(appIcon);
    }

    private void setFrameVisuals() {
        setPreferredSize(new Dimension(Utils.getWindowWidth(),
                Utils.getWindowHeight()));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void setUpTopPanel() {
        topPanel = new TopPanel();
        add(topPanel, BorderLayout.NORTH);
    }

    private void showLoading() {
        loadingDialog = new LoadingDialog();
        loadingDialog.openLoading();

        closeLoadingDialog();
    }

    private void setUpBoardPanel() {
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new BoxLayout(boardPanel, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(600, 400));
        add(boardPanel);

        BoardGrid grid = new BoardGrid(LevelType.SQUARE, 8, 8);
        FlowLayout gridLayout = new FlowLayout();
        gridLayout.setVgap(0);
        gridLayout.setHgap(0);
        grid.setLayout(gridLayout);

        boardPanel.add(grid);
    }

    private void closeLoadingDialog() {
        Timer timer = new Timer(10000, arg0 -> loadingDialog.closeLoading());
        timer.setRepeats(false);
        timer.start();
    }

    public void addScore(int score) {
        topPanel.setLblScoreNumber(topPanel.getLblScoreNumber() + score);
    }

    public void setGoal(int goal) {
        topPanel.setLblGoalNumber(goal);
    }

}
