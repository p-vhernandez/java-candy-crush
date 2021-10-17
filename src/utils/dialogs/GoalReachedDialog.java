package utils.dialogs;

import components.cards.CardGameplay;
import utils.Utils;
import utils.helpers.CardType;

import javax.swing.*;
import java.awt.*;

public class GoalReachedDialog extends JDialog {

    private final CardGameplay container;

    private final JPanel dialogPanel;

    private final JLabel lblGameOver;
    private final JButton btnContinue, btnBack;

    private static final int DIALOG_WIDTH = 450;
    private static final int DIALOG_HEIGHT = 150;

    public GoalReachedDialog(CardGameplay container) {
        this.container = container;

        this.dialogPanel = new JPanel();
        this.lblGameOver = Utils.generateDialogInfo(this, "Victory! ");
        this.btnContinue = Utils.generateDialogDismissButton(this, "Go to next level");
        this.btnBack = Utils.generateDialogDismissButton(this, "Back to levels");

        initialize();
    }

    private void initialize() {
        Utils.adaptDialogSetup(this, DIALOG_WIDTH, DIALOG_HEIGHT);
        Utils.adaptDialogPanel(dialogPanel);

        setUpListeners();

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Utils.darkBackground);
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.add(btnBack);
        buttonsPanel.add(btnContinue);

        dialogPanel.add(lblGameOver);
        dialogPanel.add(buttonsPanel);

        add(dialogPanel);
    }

    private void setUpListeners() {
        btnBack.addActionListener(listener -> {
            dispose();
            container.flipCard(CardType.LEVELS);
        });

        btnContinue.addActionListener(listener -> {
            dispose();
            container.goToNextLevel();
        });
    }

}
