package Components;

import javax.swing.*;
import javax.swing.border.Border;

import Helpers.TileType;
import Model.BoardTileModel;
import UI.BoardTileUI;

import java.awt.*;

public class BoardTile extends JButton {

    private BoardTileModel model;
    private BoardTileUI ui;
    private TileType tileType;
    private int tileSize = 30;
    private int iconSize = 25;
    private int tileRow;
    private int tileCol;

    public BoardTile(TileType tileType, int tileRow, int tileCol) {
        this.model = new BoardTileModel();
        this.model.addChangeListener(e -> repaint()); // repaint whenever the model updates
        this.ui = new BoardTileUI();
        this.ui.installUI(this);
        this.tileType = tileType;
        this.tileRow = tileRow;
        this.tileCol = tileCol;

        Border emptyBorder = BorderFactory.createEmptyBorder();
        this.setBorder(emptyBorder);
    }

    @Override
    public BoardTileModel getModel() {
        return model;
    }

    public void setModel(BoardTileModel model) {
        this.model = model;
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public int getIconSize() {
        return iconSize;
    }

    public void setIconSize(int iconSize) {
        this.iconSize = iconSize;
    }

    public int getTileRow() {
        return tileRow;
    }

    public void setTileRow(int tileRow) {
        this.tileRow = tileRow;
    }

    public int getTileCol() {
        return tileCol;
    }

    public void setTileCol(int tileCol) {
        this.tileCol = tileCol;
    }

    //Layout
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }
    public Dimension getPreferredSize() {
        return new Dimension(tileSize, tileSize);
    }

    public void paintComponent(Graphics g) {
        this.ui.paint((Graphics2D) g, this);
    }
}
