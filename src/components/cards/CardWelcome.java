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

    private JTextField textFieldUsername;
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
        setUpTextField();
        setUpStartButton();
    }

    private void setUpWelcomeText() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Utils.darkBackground);
        infoPanel.setBorder(new EmptyBorder(80, 0, 0, 0));

        infoPanel.add(generateNewInfoLine("Welcome to"));
        infoPanel.add(generateNewInfoLine(Utils.getAppName()));

        add(infoPanel, BorderLayout.NORTH);
    }

    private void setUpTextField() {
        JPanel textFieldPanel = new JPanel();
        textFieldPanel.setBackground(Utils.darkBackground);
        textFieldPanel.setLayout(new BoxLayout(textFieldPanel, BoxLayout.Y_AXIS));
        textFieldPanel.setBorder(new EmptyBorder(40, 20, 40, 20));

        JLabel textFieldLabel = new JLabel();
        textFieldLabel.setAlignmentX(CENTER_ALIGNMENT);
        textFieldLabel.setAlignmentY(CENTER_ALIGNMENT);
        textFieldLabel.setText("Enter your username: ");
        Utils.setCustomFont(this, textFieldLabel, "../../resources/font/caramel-rg.ttf", 48f, Font.PLAIN);
        textFieldLabel.setForeground(Utils.tileBorder);

        textFieldUsername = new JTextField();
        textFieldUsername.setAlignmentX(CENTER_ALIGNMENT);
        textFieldUsername.setAlignmentY(CENTER_ALIGNMENT);
        textFieldUsername.setPreferredSize(new Dimension(500, 50));
        textFieldUsername.setMaximumSize(textFieldUsername.getPreferredSize());

        textFieldPanel.add(textFieldLabel);
        textFieldPanel.add(textFieldUsername);

        add(textFieldPanel, BorderLayout.CENTER);
    }

    private JLabel generateNewInfoLine(String textToShow) {
        JLabel newLine = new JLabel(textToShow);
        newLine.setForeground(Color.white);
        newLine.setAlignmentX(CENTER_ALIGNMENT);

        Utils.setCustomFont(this, newLine,
                "../../resources/font/creepster-rg.ttf", 60f, Font.PLAIN);

        return newLine;
    }

    private void setUpStartButton() {
        startButton = new JButton(Utils.getWelcomeButtonLabel());
        startButton.setBackground(Utils.halloweenOrange);
        startButton.setForeground(Utils.darkBackground);
        startButton.setFocusPainted(false);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton.setFont(Objects.requireNonNull(Utils.generateFont(
                        this, "../../resources/font/caramel-rg.ttf")
                ).deriveFont(Font.BOLD, 42f)
        );

        initializeListeners();
        add(startButton, BorderLayout.SOUTH);
    }

    private void initializeListeners() {
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String username = textFieldUsername.getText();
                container.setPlayerUsername(username);
                container.flipCard(true);
            }
        });
    }

}
