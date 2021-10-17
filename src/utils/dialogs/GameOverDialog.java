package utils.dialogs;

import utils.Utils;

import javax.swing.*;
import java.awt.*;

public class GameOverDialog extends JDialog {

    private final JPanel dialogPanel;

    private final JLabel lblGameOver;
    private final JButton btnDismiss;

    private static final int DIALOG_WIDTH = 300;
    private static final int DIALOG_HEIGHT = 150;

    public GameOverDialog() {
        this.dialogPanel = new JPanel();

        lblGameOver = Utils.generateDialogInfo(this, "Game over. ");
        btnDismiss = Utils.generateDialogDismissButton(this, "Accept defeat");

        initialize();
    }

    private void initialize() {
        Utils.adaptDialogSetup(this, DIALOG_WIDTH, DIALOG_HEIGHT);
        Utils.adaptDialogPanel(dialogPanel);
        setUpListeners();

        dialogPanel.add(lblGameOver);
        dialogPanel.add(btnDismiss);

        add(dialogPanel);
    }

    private void setUpListeners() {
        btnDismiss.addActionListener(listener -> dispose());
    }

    public void paint(Graphics graphics) {
        super.paint(graphics);
    }

}
