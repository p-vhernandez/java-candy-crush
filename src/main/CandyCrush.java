package main;

import components.cards.CardGameplay;
import components.cards.CardLevelChoice;
import components.cards.CardWelcome;
import utils.dialogs.GameOverDialog;
import utils.dialogs.LoadingDialog;
import utils.Level;
import utils.helpers.LevelType;
import utils.Utils;

import javax.swing.*;
import java.awt.*;

public class CandyCrush extends JFrame {

    private LoadingDialog loadingDialog;
    private GameOverDialog gameOverDialog;

    private JPanel cards;

    private CardLayout cardLayout;
    private CardWelcome cardWelcome;
    private CardLevelChoice cardLevelChoice;
    private CardGameplay cardGameplay;

    final static String WELCOMEPANEL = "Welcome panel";
    final static String LEVELPANEL = "Level panel";
    final static String GAMEPANEL = "Gameplay panel";

    private String playerUsername;

    public CandyCrush() {
        super(Utils.getAppName());
        setupUI();
    }

    private void setupUI() {
        setAppIcon();
        setBackground(Utils.darkBackground);
        getContentPane().setBackground(Utils.darkBackground);
        setFrameVisuals();

        // Set up the cards
        cardLayout = new CardLayout();
        cards = new JPanel();
        cards.setLayout(cardLayout);


        cardWelcome = new CardWelcome(this);
        cardLevelChoice = new CardLevelChoice(this);
        cardGameplay = new CardGameplay(new Level(LevelType.SQUARE));

        cards.add(cardWelcome, WELCOMEPANEL);
        cards.add(cardLevelChoice, LEVELPANEL);
        cards.add(cardGameplay, GAMEPANEL);
        cardLayout.show(cards, WELCOMEPANEL);

        add(cards);

        pack();
    }

    private void setAppIcon() {
        Image appIcon = Utils.generateImage(this,
                "../resources/img/EYEBALL.png");
        setIconImage(appIcon);
    }

    private void setFrameVisuals() {
        setPreferredSize(new Dimension(Utils.getWindowWidth(),
                Utils.getWindowHeight()));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public String getPlayerUsername() {
        return this.playerUsername;
    }

    public void setPlayerUsername(String playerUsername) {
        this.playerUsername = playerUsername;
    }

    public void showLoading() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.openLoading();

        closeLoadingDialog();
    }

    private void closeLoadingDialog() {
        Timer timer = new Timer(100, arg0 -> {
            loadingDialog.closeLoading();
            cardGameplay.enableBoardGrid(true);
        });

        timer.setRepeats(false);
        timer.start();
    }

    public void flipCard(boolean toLevelChoice) {
        if (toLevelChoice) {
            cardLevelChoice.reloadContent();
        }

        cardLayout.next(cards);
    }

}
