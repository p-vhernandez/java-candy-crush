package utils;

import java.awt.*;
import java.io.InputStream;
import java.util.Objects;

public class Utils {

    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 600;
    private static final int TOP_BAR_HEIGHT = 80;
    private static final int TOP_BAR_COMPONENTS_HEIGHT = 70;
    private static final int MOVEMENTS_PANEL_WIDTH = 100;
    private static final int ANIM_SLIDE_VELOCITY = 1;
    private static final int TILE_SIZE = 30;
    private static final int ICON_SIZE = 25;


    private static final String APP_NAME = "Halloween Candy Crush";

    public static int getWindowWidth() {
        return WINDOW_WIDTH;
    }

    public static int getWindowHeight() {
        return WINDOW_HEIGHT;
    }

    public static int getTopBarHeight() {
        return TOP_BAR_HEIGHT;
    }

    public static int getTopBarComponentsHeight() {
        return TOP_BAR_COMPONENTS_HEIGHT;
    }

    public static int getMovementsPanelWidth() {
        return MOVEMENTS_PANEL_WIDTH;
    }

    public static int getRestTopPanelComponentsWidth() {
        return (WINDOW_WIDTH - 60 - MOVEMENTS_PANEL_WIDTH) / 2;
    }

    public static int getAnimSlideVelocity() { return ANIM_SLIDE_VELOCITY; }

    public static int getTileSize() { return TILE_SIZE; }

    public static int getIconSize() { return ICON_SIZE; }

    public static String getAppName() {
        return APP_NAME;
    }

    public static Image generateImage(Object object, String path) {
        return Toolkit.getDefaultToolkit().getImage(object.getClass()
                .getResource(path));
    }

    public static Font generateFont(Object object, String path) {
        try {
            InputStream is = object.getClass().getResourceAsStream(path);

            if (is != null) {
                return Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(is));
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
