package components;

import main.CandyCrush;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardWelcome extends JPanel {

    private JButton startButton;
    private JLabel welcomeLabel;
    private CandyCrush container;

    public CardWelcome(CandyCrush container) {
        initializeUI();
        initializeListeners();
        this.container = container;
    }

    private void initializeUI() {

        setPreferredSize(new Dimension(Utils.getWindowWidth(), Utils.getWindowHeight()));
        setBackground(Utils.darkBackground);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        startButton = new JButton(Utils.getWelcomeButtonLabel());
        welcomeLabel = new JLabel(Utils.getAppName());

        welcomeLabel.setForeground(Color.white);
        Utils.setCustomFont(this, welcomeLabel,
                "../resources/font/creepster-rg.ttf", 60f, Font.PLAIN);

        add(Box.createRigidArea(new Dimension(0,100)));
        add(welcomeLabel);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createRigidArea(new Dimension(0,100)));
        add(startButton);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
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
