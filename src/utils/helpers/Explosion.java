package utils.helpers;

import java.awt.*;

public class Explosion {

    private double x, y;
    private int radius, maxRadius;

    // TODO: combine with actual play
    public Explosion(double x, double y, int radius, int maxRadius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.maxRadius = maxRadius;
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

}
