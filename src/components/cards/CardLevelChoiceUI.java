package components.cards;

import utils.Utils;
import utils.buttons.LevelButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        controller.setBorder(new EmptyBorder(0, 80, 0, 80));
    }

    protected void setUpLevelLabel() {
        JLabel label = new JLabel("Choose your level");
        int horizontalMargin = 50;
        label.setPreferredSize(new Dimension(Utils.getWindowWidth() - horizontalMargin * 2, 100));
        Utils.setCustomFont(CardLevelChoiceUI.class, label,
                "/resources/font/creepster-rg.ttf", 56f, Font.PLAIN);
        label.setForeground(Color.white);

        controller.add(label);
    }

    protected void setUpButtons() {
        for (LevelButton button : controller.getLevelButtons()) {
            if (!button.isUnlocked()) {
                button.setEnabled(false);
            }

            controller.add(button);
        }
    }

}
