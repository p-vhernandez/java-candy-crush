package components.cards;

import components.TopPanel;
import components.grid.BoardGrid;
import main.CandyCrush;
import utils.Level;
import utils.Utils;
import utils.helpers.CardType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardGameplay extends JPanel {

    private final CandyCrush container;

    private static TopPanel topPanel;
    private BoardGrid grid;

    private Level level;

    private JButton btnBack;

    public CardGameplay(CandyCrush container) {
        this.container = container;

        setupUI();
    }

    private void setupUI() {
        setPreferredSize(new Dimension(Utils.getWindowWidth(),
                Utils.getWindowHeight()));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Utils.darkBackground);
        Border blackline = BorderFactory.createLineBorder(Color.WHITE);
        setBorder(blackline);
    }

    public void loadGame(Level level) {
        this.level = level;

        removeAll();

        setUpTopPanel();
        setUpBoardPanel();
        setUpBottonPanel();
    }

    private void setUpBoardPanel() {
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new BoxLayout(boardPanel, BoxLayout.Y_AXIS));
        boardPanel.setBorder(new EmptyBorder(40, 0, 0, 0));
        boardPanel.setBackground(Utils.darkBackground);
        setPreferredSize(new Dimension(Utils.getBoardPanelWidth(), Utils.getBoardPanelHeight()));

        add(boardPanel, BorderLayout.CENTER);
        grid = new BoardGrid(level);

        setGoal(level.getLevelGoal());
        setMaxMovements(level.getMaxMovements());

        boardPanel.add(grid);
    }

    private void setUpTopPanel() {
        topPanel = new TopPanel(this);
        add(topPanel, BorderLayout.NORTH);
    }

    private void setUpBottonPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setPreferredSize(new Dimension(Utils.getWindowWidth(), 150));
        bottomPanel.setMaximumSize(bottomPanel.getMaximumSize());

        btnBack = Utils.generateDefaultAppButton(this, "Back to levels");
        btnBack.setPreferredSize(new Dimension(Utils.getWindowWidth() / 2, 50));

        initializeListeners();
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void initializeListeners() {
        btnBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                container.flipCard(CardType.GAME_PLAY, CardType.LEVELS);
            }
        });
    }

    public void addScore(int score) {
        topPanel.setLblScoreNumber(topPanel.getLblScoreNumber() + score);
    }

    public void setGoal(int goal) {
        topPanel.setLblGoalNumber(goal);
    }

    public void setMaxMovements(int maxMovements) {
        topPanel.setMaxMovements(maxMovements);
    }

    public static void oneMovementLess() {
        topPanel.oneMovementLess();
    }

    public static int getMovementsLeft() {
        return topPanel.getMovementsLeft();
    }

    public void enableBoardGrid(boolean enabled) {
        grid.enableBoardGrid(enabled);
    }

    public static void updateScore(int sequence) {
        int currentScore = topPanel.getLblScoreNumber();
        topPanel.setLblScoreNumber(currentScore + sequence * 40);
    }
}
