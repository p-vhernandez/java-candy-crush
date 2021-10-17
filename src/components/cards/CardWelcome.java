package components.cards;

import main.CandyCrush;
import utils.Utils;
import utils.dialogs.ErrorDialog;
import utils.helpers.CardType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class CardWelcome extends JPanel {

    private final CandyCrush container;
    private final CardWelcomeUI view;

    public CardWelcome(CandyCrush container) {
        this.container = container;
        this.view = new CardWelcomeUI(this);

        initialize();
    }

    private void initialize() {
        view.initializeUI();
    }

    protected JLabel generateNewInfoLine(String textToShow) {
        JLabel newLine = new JLabel(textToShow);
        newLine.setForeground(Color.white);
        newLine.setAlignmentX(CENTER_ALIGNMENT);

        Utils.setCustomFont(this, newLine,
                "../../resources/font/creepster-rg.ttf", 60f, Font.PLAIN);

        return newLine;
    }

    protected void startPlaying(String player) {
        container.setPlayerUsername(player);
        container.flipCard(CardType.WELCOME, CardType.LEVELS);
    }

}
