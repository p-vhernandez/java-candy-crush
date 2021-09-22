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
    private int posX;
    private int posY;

    public BoardTile(TileType tileType, int posX, int posY) {
        this.ui = new BoardTileUI();
        this.ui.installUI(this);
        this.model = new BoardTileModel();
        this.model.addChangeListener(e -> repaint()); // repaint whenever the model updates
        this.tileType = tileType;
        this.posX = posX;
        this.posY = posY;

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

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
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
