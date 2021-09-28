package Helpers;

import java.awt.*;

public class Crush {

    private double x, y;
    private int radius, maxRadius;

    public Crush(double x, double y, int radius, int maxRadius) {
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
        graphics2D.setColor(Color.red);
        graphics2D.drawOval((int) (x - radius), (int) (y - radius),
                2 * radius, 2 * radius);
    }

}
