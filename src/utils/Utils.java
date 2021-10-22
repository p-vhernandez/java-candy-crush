package utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.dialogs.ErrorDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Utils {

    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 700;
    private static final int BOARD_PANEL_WIDTH = 600;
    private static final int BOARD_PANEL_HEIGHT = 490;
    private static final int TOP_BAR_HEIGHT = 120;
    private static final int TOP_BAR_COMPONENTS_HEIGHT = 90;
    private static final int MOVEMENTS_PANEL_WIDTH = 100;
    private static final int TILE_SIZE = 40;
    private static final int TOTAL_LEVELS = 8;

    private static final int ICON_SIZE = 35;

    private static final String APP_NAME = "Halloween Candy Crush";
    private static final String WELCOME_BUTTON_LABEL = "Start";

    public static final Color tileBorder = new Color(50, 168, 82);
    public static final Color tileFill = new Color(44, 54, 47);
    public static final Color tileFillSelected = new Color(68, 110, 80);
    public static final Color darkBackground = new Color(44, 54, 47);
    public static final Color halloweenOrange = new Color(252, 127, 3);
    public static final Color halloweenOrangeHover = new Color(135, 71, 7);

    public static Player player;

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

    public static void showError(String errorMsg) {
        ErrorDialog errorDialog = new ErrorDialog(errorMsg);
        errorDialog.setVisible(true);
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

    public static JButton generateDefaultAppButton(Object object, String buttonText) {
        JButton button = new JButton(buttonText);
        button.setBackground(Utils.halloweenOrange);
        button.setForeground(Utils.darkBackground);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.setFont(Objects.requireNonNull(Utils.generateFont(
                        object, "../../resources/font/caramel-rg.ttf")
                ).deriveFont(Font.BOLD, 42f)
        );

        return button;
    }

    public static void adaptDialogSetup(JDialog dialog, int width, int height) {
        dialog.setResizable(false);
        dialog.setModal(false);
        dialog.setUndecorated(true);
        dialog.setSize(width, height);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.setLocationRelativeTo(null);
    }

    public static void adaptDialogPanel(JPanel dialogPanel) {
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        dialogPanel.setBorder(new LineBorder(Utils.halloweenOrange, 5));
        dialogPanel.setBackground(Utils.darkBackground);
    }

    public static JLabel generateDialogInfo(Object object, String infoLabel) {
        JLabel dialogLabel = new JLabel();

        dialogLabel.setText(infoLabel);
        dialogLabel.setForeground(Color.white);
        Utils.setCustomFont(object, dialogLabel, "../../resources/font/deanna.ttf", 32f, Font.PLAIN);
        dialogLabel.setIcon(new ImageIcon(Utils.generateImage(object, "../../resources/img/EYEBALL.png")
                .getScaledInstance(45, 45, Image.SCALE_SMOOTH)));

        dialogLabel.setBorder(new EmptyBorder(20, 0, 20, 0));
        dialogLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        return dialogLabel;
    }

    public static JButton generateDialogDismissButton(Object object, String buttonLabel) {
        JButton dialogButton = new JButton();

        dialogButton.setText(buttonLabel);
        dialogButton.setBackground(Utils.halloweenOrange);
        dialogButton.setForeground(Utils.darkBackground);
        dialogButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        dialogButton.setFont(Objects.requireNonNull(
                        Utils.generateFont(object, "../../resources/font/caramel-rg.ttf"))
                .deriveFont(Font.BOLD, 28f)
        );

        return dialogButton;
    }

    public static JSONObject getPlayersJSONObject() {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;

        try {
            jsonObject = (JSONObject) parser.parse(new FileReader("src/resources/user/progress.json"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

}
