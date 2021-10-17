package utils.dialogs;

import utils.Utils;

import javax.swing.*;

public class ErrorDialog extends JDialog {

    private final JPanel dialogPanel;

    private final JLabel lblError;
    private final JButton btnDismiss;

    private static final int DIALOG_WIDTH = 450;
    private static final int DIALOG_HEIGHT = 150;

    public ErrorDialog(String infoLabel) {
        this.dialogPanel = new JPanel();

        this.lblError = Utils.generateDialogInfo(this, infoLabel);
        this.btnDismiss = Utils.generateDialogDismissButton(this, "Ok");

        initialize();
    }

    private void initialize() {
        Utils.adaptDialogSetup(this, DIALOG_WIDTH, DIALOG_HEIGHT);
        Utils.adaptDialogPanel(dialogPanel);
        setUpListeners();

        dialogPanel.add(lblError);
        dialogPanel.add(btnDismiss);

        add(dialogPanel);
    }

    private void setUpListeners() {
        btnDismiss.addActionListener(listener -> dispose());
    }

}
