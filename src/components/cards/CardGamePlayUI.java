package components.cards;

import utils.Utils;
import utils.helpers.CardType;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CardGamePlayUI {

    private final CardGameplay controller;

    private JButton btnBack;
    private JButton btnRestart;

    public CardGamePlayUI(CardGameplay controller) {
        this.controller = controller;
    }

    protected void initializeUI() {
        controller.setPreferredSize(new Dimension(Utils.getWindowWidth(),
                Utils.getWindowHeight()));
        controller.setLayout(new BoxLayout(controller, BoxLayout.Y_AXIS));
        controller.setBackground(Utils.darkBackground);

        Border blackLine = BorderFactory.createLineBorder(Color.WHITE);
        controller.setBorder(blackLine);
    }

    protected void setUpBoardPanel() {
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new BoxLayout(boardPanel, BoxLayout.Y_AXIS));
        boardPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        boardPanel.setBackground(Utils.darkBackground);
        boardPanel.setPreferredSize(new Dimension(Utils.getBoardPanelWidth(), Utils.getBoardPanelHeight()));
        boardPanel.setMinimumSize(boardPanel.getPreferredSize());

        controller.add(boardPanel, BorderLayout.CENTER);
        controller.createNewGrid();

        controller.setGoal(controller.getLevel().getLevelGoal());
        controller.setMaxMovements(controller.getLevel().getMaxMovements());

        boardPanel.add(controller.getBoardGrid());
    }

    protected void setUpBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        bottomPanel.setBackground(Utils.darkBackground);
        bottomPanel.setMaximumSize(new Dimension(Utils.getWindowWidth(), 60));

        btnBack = Utils.generateDefaultAppButton(CardGamePlayUI.class, "Back to levels");
        btnBack.setPreferredSize(new Dimension(Utils.getWindowWidth() - 30, 50));

        btnRestart = Utils.generateDefaultAppButton(CardGamePlayUI.class, "Restart level");
        btnRestart.setPreferredSize(new Dimension((Utils.getWindowWidth() / 2 - 30), 50));

        initializeListeners();

        bottomPanel.add(btnBack);
        //bottomPanel.add(btnRestart);
        controller.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void initializeListeners() {
        btnBack.addActionListener(listener -> controller.flipCard(CardType.LEVELS));
        btnRestart.addActionListener(listener -> controller.restartLevel());
    }

}
