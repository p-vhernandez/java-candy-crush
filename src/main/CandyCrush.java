package main;

import components.GameOverDialog;
import components.grid.BoardGrid;
import components.LoadingDialog;
import components.TopPanel;
import utils.Level;
import utils.helpers.LevelType;
import utils.Utils;

import javax.swing.*;
import java.awt.*;

public class CandyCrush extends JFrame {

    private static TopPanel topPanel;
    private LoadingDialog loadingDialog;
    private GameOverDialog gameOverDialog;
    private BoardGrid grid;

    public CandyCrush() {
        super(Utils.getAppName());
        showLoading();
        setupUI();
    }

    private void setupUI() {
        setAppIcon();
        setBackground(Utils.darkBackground);
        getContentPane().setBackground(Utils.darkBackground);
        setFrameVisuals();
        setUpTopPanel();
        setUpBoardPanel();

        pack();
    }

    private void setAppIcon() {
        Image appIcon = Utils.generateImage(this,
                "../resources/img/EYEBALL.png");
        setIconImage(appIcon);
    }

    private void setFrameVisuals() {
        setPreferredSize(new Dimension(Utils.getWindowWidth(),
                Utils.getWindowHeight()));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void setUpTopPanel() {
        topPanel = new TopPanel(this);
        add(topPanel, BorderLayout.NORTH);
    }

    private void showLoading() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.openLoading();

        closeLoadingDialog();
    }

    private void setUpBoardPanel() {
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new BoxLayout(boardPanel, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(600, 400));

        add(boardPanel);

        Level selectedLevel = new Level(LevelType.SQUARE);
        grid = new BoardGrid(selectedLevel);
        FlowLayout gridLayout = new FlowLayout();
        gridLayout.setVgap(0);
        gridLayout.setHgap(0);
        grid.setLayout(gridLayout);

        setGoal(selectedLevel.getLevelGoal());
        setMaxMovemets(selectedLevel.getMaxMovements());

        boardPanel.add(grid);
    }

    private void closeLoadingDialog() {
        Timer timer = new Timer(10000, arg0 -> {
            loadingDialog.closeLoading();
            enableBoardGrid(true);
        });

        timer.setRepeats(false);
        timer.start();
    }

    public void addScore(int score) {
        topPanel.setLblScoreNumber(topPanel.getLblScoreNumber() + score);
    }

    public void setGoal(int goal) {
        topPanel.setLblGoalNumber(goal);
    }

    public void setMaxMovemets(int maxMovemets) {
        topPanel.setMaxMovements(maxMovemets);
    }

    public static void oneMovementLess() {
        topPanel.oneMovementLess();
    }

    public static int getMovementsLeft() {
        return topPanel.getMovementsLeft();
    }

    public void enableBoardGrid(boolean enabled) {
        grid.enableBoardGrid(enabled);
    }

    public static void updateScore(int sequence) {
        int currentScore = topPanel.getLblScoreNumber();
        topPanel.setLblScoreNumber(currentScore + sequence * 40);
    }

}
