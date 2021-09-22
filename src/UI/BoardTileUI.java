package UI;

import Components.BoardTile;
import utils.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BoardTileUI {

    public void installUI(BoardTile tile) {

    }

    public void paint(Graphics2D g, BoardTile tile) {
        int iconXY = (tile.getTileSize() - tile.getIconSize())/2;
        BufferedImage icon = null;
        try {
            icon = ImageIO.read(getClass().getResourceAsStream("../resources/img/" + tile.getTileType() +".png"));
        } catch (IOException e){
            e.printStackTrace();
        }
        g.setPaint(Utils.tileFill);
        g.fillRoundRect(0,0, tile.getTileSize(), tile.getTileSize(), 5,5);
        g.setPaint(Utils.tileBorder);
        g.drawRoundRect(0,0, tile.getTileSize(), tile.getTileSize(), 5,5);
        g.drawImage(icon, iconXY, iconXY, tile.getIconSize(), tile.getIconSize(), null);
    }
}
