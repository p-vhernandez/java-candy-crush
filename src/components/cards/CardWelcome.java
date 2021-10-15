package components.cards;

import main.CandyCrush;
import utils.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class CardWelcome extends JPanel {

    private final CandyCrush container;

    private JButton startButton;

    public CardWelcome(CandyCrush container) {
        this.container = container;
        initializeUI();
    }

    private void initializeUI() {

        setPreferredSize(new Dimension(Utils.getWindowWidth(), Utils.getWindowHeight()));
        setBackground(Utils.darkBackground);
        setLayout(new BorderLayout());

        setUpWelcomeText();
        setUpStartButton();
    }

    private void setUpWelcomeText() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Utils.darkBackground);
        infoPanel.setBorder(new EmptyBorder(160, 0, 0, 0));

        infoPanel.add(generateNewInfoLine("Welcome to"));
        infoPanel.add(generateNewInfoLine(Utils.getAppName()));

        add(infoPanel, BorderLayout.CENTER);
    }

    private JLabel generateNewInfoLine(String textToShow) {
        JLabel newLine = new JLabel(textToShow);
        newLine.setForeground(Color.white);
        newLine.setAlignmentX(CENTER_ALIGNMENT);

        Utils.setCustomFont(this, newLine,
                "../resources/font/creepster-rg.ttf", 60f, Font.PLAIN);

        return newLine;
    }

    private void setUpStartButton() {
        startButton = new JButton(Utils.getWelcomeButtonLabel());
        startButton.setBackground(Utils.halloweenOrange);
        startButton.setForeground(Utils.darkBackground);
        startButton.setFocusPainted(false);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton.setFont(Objects.requireNonNull(Utils.generateFont(
                        this, "../resources/font/caramel-rg.ttf")
                ).deriveFont(Font.BOLD, 42f)
        );

        initializeListeners();
        add(startButton, BorderLayout.SOUTH);
    }

    private void initializeListeners() {
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                container.flipCard();
            }
        });
    }

}
