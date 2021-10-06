package utils.helpers;

import utils.Utils;

import java.awt.*;

public class Explosion {

    private final int tileOriginX, tileOriginY;
    private final int maxRadius;

    private double x, y;
    private int radius;

    // TODO: combine with actual play
    public Explosion(int tileOriginX, int tileOriginY,
                     int radius, int maxRadius) {
        this.tileOriginX = tileOriginX;
        this.tileOriginY = tileOriginY;
        this.radius = radius;
        this.maxRadius = maxRadius;

        calculateXandY();
    }

    public boolean update() {
        radius++;
        return radius >= maxRadius;
    }

    public void draw(Graphics2D graphics2D) {
        // TODO: set better color
        graphics2D.setColor(Color.red);
        graphics2D.drawOval((int) (x - radius), (int) (y - radius),
                2 * radius, 2 * radius);
    }

    private void calculateXandY() {
        x = tileOriginX + (Utils.getTileSize() / 2);
        y = tileOriginY + (Utils.getTileSize() / 2);
    }

}
