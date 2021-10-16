package components.cards;

import utils.Utils;
import utils.buttons.LevelButton;
import utils.dialogs.ErrorDialog;

import javax.swing.*;
import java.awt.*;

public class CardLevelChoiceUI {

    private final CardLevelChoice controller;

    public CardLevelChoiceUI(CardLevelChoice controller) {
        this.controller = controller;
    }

    protected void initializeUI() {
        controller.setPreferredSize(new Dimension(Utils.getWindowWidth(), Utils.getWindowHeight()));
        controller.setBackground(Utils.darkBackground);
        controller.setLayout(new FlowLayout());
    }

    protected void setUpLevelLabel() {
        JLabel label = new JLabel("Choose your level");
        int horizontalMargin = 50;
        label.setPreferredSize(new Dimension(Utils.getWindowWidth() - horizontalMargin * 2, 100));
        Utils.setCustomFont(this, label,
                "../../resources/font/creepster-rg.ttf", 56f, Font.PLAIN);
        label.setForeground(Color.white);

        controller.add(label);
    }

    protected void setUpButtons() {
        controller.resetLevelButtons();
        controller.createLevelButtons();

        for (LevelButton button : controller.getLevelButtons()) {
            if (!button.isUnlocked()) {
                button.setEnabled(false);
            }

            controller.add(button);
        }
    }

    protected void showError(String infoLabel) {
        ErrorDialog errorDialog = new ErrorDialog(infoLabel);
        errorDialog.setVisible(true);
    }

}
