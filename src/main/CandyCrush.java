package main;

import components.cards.CardGameplay;
import components.cards.CardLevelChoice;
import components.cards.CardWelcome;
import utils.dialogs.LoadingDialog;
import utils.Level;
import utils.Utils;
import utils.helpers.CardType;

import javax.swing.*;
import java.awt.*;

public class CandyCrush extends JFrame {

    private LoadingDialog loadingDialog;

    private JPanel cards;

    private CardLayout cardLayout;
    private CardWelcome cardWelcome;
    private CardLevelChoice cardLevelChoice;
    private CardGameplay cardGameplay;

    final static String WELCOMEPANEL = "Welcome panel";
    final static String LEVELPANEL = "Level panel";
    final static String GAMEPANEL = "Gameplay panel";

    private String playerUsername;
    private Level selectedLevel;

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
        cardGameplay = new CardGameplay(this);

        cards.add(cardWelcome, WELCOMEPANEL);
        cards.add(cardLevelChoice, LEVELPANEL);
        cards.add(cardGameplay, GAMEPANEL);
        cardLayout.show(cards, WELCOMEPANEL);

        add(cards);

        pack();
    }

    private void setAppIcon() {
        Image appIcon = Utils.generateImage(CandyCrush.class,
                "/resources/img/EYEBALL.png");
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

    public Level getSelectedLevel() {
        return this.selectedLevel;
    }

    public void setSelectedLevel(Level level) {
        this.selectedLevel = level;
    }

    public void showLoading() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.openLoading();

        closeLoadingDialog();
    }

    private void closeLoadingDialog() {
        Timer timer = new Timer(5000, arg0 -> {
            loadingDialog.closeLoading();
            cardGameplay.enableBoardGrid(true);
        });

        timer.setRepeats(false);
        timer.start();
    }

    public void flipCard(CardType original, CardType cardType) {
        switch (cardType) {
            case WELCOME:
                break;
            case LEVELS:
                cardLevelChoice.reloadContent();

                if (original == CardType.GAME_PLAY) {
                    cardLayout.previous(cards);
                } else {
                    cardLayout.next(cards);
                }

                break;
            case GAME_PLAY:
                cardGameplay.loadGame(getSelectedLevel());
                cardLayout.next(cards);
                break;
        }
    }

}
