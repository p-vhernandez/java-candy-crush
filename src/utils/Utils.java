package utils;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.Objects;

public class Utils {

    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 800;
    private static final int BOARD_PANEL_WIDTH = 600;
    private static final int BOARD_PANEL_HEIGHT = 400;
    private static final int TOP_BAR_HEIGHT = 120;
    private static final int TOP_BAR_COMPONENTS_HEIGHT = 90;
    private static final int MOVEMENTS_PANEL_WIDTH = 100;
    private static final int TILE_SIZE = 40;
    private static final int TOTAL_LEVELS = 15;

    public static int getWindowWidth() {
        return WINDOW_WIDTH;
    }

    private static final int ICON_SIZE = 35;
    private static final String APP_NAME = "Halloween Candy Crush";

    private static final String WELCOME_BUTTON_LABEL = "Start";
    public static Color tileBorder = new Color(50, 168, 82);
    public static Color tileFill = new Color(44, 54, 47);
    public static Color darkBackground = new Color(44, 54, 47);

    public static Color halloweenOrange = new Color(252, 127, 3);

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

    public static int getTileSize() {
        return TILE_SIZE;
    }

    public static int getIconSize() {
        return ICON_SIZE;
    }

    public static String getAppName() {
        return APP_NAME;
    }

    public static String getWelcomeButtonLabel() {
        return WELCOME_BUTTON_LABEL;
    }

    public static int getBoardPanelWidth() {
        return BOARD_PANEL_WIDTH;
    }

    public static int getBoardPanelHeight() {
        return BOARD_PANEL_HEIGHT;
    }

    public static int getTotalLevels() {
        return TOTAL_LEVELS;
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

    public static void setCustomFont(Object object, JLabel label, String path, float fontSize, int fontStyle) {
        Font customFont = Utils.generateFont(object, path);
        if (customFont != null) {
            label.setFont(customFont.deriveFont(fontStyle, fontSize));
        }
    }

}
