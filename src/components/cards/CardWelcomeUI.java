package components.cards;

import utils.Utils;
import utils.dialogs.ErrorDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.Component.CENTER_ALIGNMENT;

public class CardWelcomeUI {

    private final CardWelcome controller;

    private JTextField textFieldUsername;
    private JButton startButton;

    public CardWelcomeUI(CardWelcome controller) {
        this.controller = controller;
    }

    protected void initializeUI() {
        controller.setPreferredSize(new Dimension(Utils.getWindowWidth(), Utils.getWindowHeight()));
        controller.setBackground(Utils.darkBackground);
        controller.setLayout(new BorderLayout());

        setUpWelcomeText();
        setUpTextField();
        setUpStartButton();
    }

    private void setUpWelcomeText() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Utils.darkBackground);
        infoPanel.setBorder(new EmptyBorder(80, 0, 0, 0));

        infoPanel.add(controller.generateNewInfoLine("Welcome to"));
        infoPanel.add(controller.generateNewInfoLine(Utils.getAppName()));

        controller.add(infoPanel, BorderLayout.NORTH);
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

        controller.add(textFieldPanel, BorderLayout.CENTER);
    }

    private void setUpStartButton() {
        startButton = Utils.generateDefaultAppButton(this,
                Utils.getWelcomeButtonLabel());

        initializeListeners();
        controller.add(startButton, BorderLayout.SOUTH);
    }

    private void initializeListeners() {
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String username = textFieldUsername.getText();

                if (username.equals("")) {
                    ErrorDialog errorDialog = new ErrorDialog("You must enter your username. ");
                    errorDialog.setVisible(true);
                } else {
                    controller.startPlaying(username);
                }
            }
        });
    }

}
