package Components;

import javax.swing.*;
import java.awt.*;
import Helpers.Colors;
import Helpers.LevelType;
import Helpers.TileType;

public class CandyCrush extends JFrame {


    public CandyCrush() {
        super("Candy Crush");
        setupUI();
    }

    private void setupUI() {
        try {
            Image appIcon = Toolkit.getDefaultToolkit().getImage(getClass()
                    .getResource("../resources/img/EYEBALL.png"));
            setIconImage(appIcon);
            setPreferredSize(new Dimension(600, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setupBoardPanel();
        pack();

    }

    private void setupBoardPanel() {
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

        //tiles
        boardPanel.add(grid);
        BoardTile[][] tiles = grid.getTiles();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                grid.add(tiles[i][j]);
            }
        }
    }
}
