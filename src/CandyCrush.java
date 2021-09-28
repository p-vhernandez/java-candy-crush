import Components.BoardGrid;
import Components.BoardTile;
import Components.LoadingDialog;
import Components.TopPanel;
import Helpers.LevelType;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CandyCrush extends JFrame {

    private TopPanel topPanel;
    private LoadingDialog loadingDialog;

    public CandyCrush() {
        super(Utils.getAppName());

        setupUI();
    }

    private void setupUI() {
        setAppIcon();
        setPreferredSize(new Dimension(Utils.getWindowWidth(),
                Utils.getWindowHeight()));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setUpTopPanel();
        setUpBoardPanel();

        pack();
        showLoading();
    }

    private void setAppIcon() {
        Image appIcon = Utils.generateImage(this,
                "resources/img/EYEBALL.png");
        setIconImage(appIcon);
    }

    private void setUpTopPanel() {
        this.topPanel = new TopPanel();
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
        //boardPanel.setBackground(Colors.boardPanel);
        add(boardPanel);

        //board grid
        BoardGrid grid = new BoardGrid(LevelType.SQUARE, 8,8);
        FlowLayout gridLayout = new FlowLayout();
        gridLayout.setVgap(0);
        gridLayout.setHgap(0);
        grid.setLayout(gridLayout);

        // FIXME: tiles take too long to load
        boardPanel.add(grid);
        BoardTile[][] tiles = grid.getTiles();
        for (BoardTile[] tile : tiles) {
            for (BoardTile boardTile : tile) {
                grid.add(boardTile);
            }
        }
    }

    private void closeLoadingDialog() {
        Timer timer = new Timer(10000, arg0 -> loadingDialog.closeLoading());
        timer.setRepeats(false);
        timer.start();
    }
}
