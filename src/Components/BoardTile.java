package Components;

import javax.swing.*;
import javax.swing.border.Border;

import Helpers.TileType;
import utils.Utils;

import java.awt.*;

public class BoardTile extends JButton {

    private TileType tileType;
    private int tileRow, tileCol;
    private int tileX, tileY;

    public BoardTile(TileType tileType, int tileRow, int tileCol, int tileX, int tileY) {
        this.model.addChangeListener(e -> repaint()); // repaint whenever the model updates
        this.tileType = tileType;

        this.tileRow = tileRow;
        this.tileCol = tileCol;

        this.tileX = tileX;
        this.tileY = tileY;

        Border emptyBorder = BorderFactory.createEmptyBorder();
        this.setBorder(emptyBorder);
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
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

    public int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
        repaint();
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
        repaint();
    }

    //Layout
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }
    public Dimension getPreferredSize() {
        return new Dimension(Utils.getTileSize(), Utils.getTileSize());
    }

    public void paintComponent(Graphics g) {
        //this.ui.paint((Graphics2D) g, this);
    }
}
