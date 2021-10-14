package main;

import components.*;
import utils.dialogs.GameOverDialog;
import utils.dialogs.LoadingDialog;
import utils.Level;
import utils.helpers.LevelType;
import utils.Utils;

import javax.swing.*;
import java.awt.*;

public class CandyCrush extends JFrame {

    private CardWelcome cardWelcome;
    private CardLevelChoice cardLevelChoice;
    private CardGameplay cardGameplay;
    private LoadingDialog loadingDialog;
    private GameOverDialog gameOverDialog;
    private JPanel cards;
    private CardLayout cardLayout;

    final static String WELCOMEPANEL = "Welcome panel";
    final static String LEVELPANEL = "Level panel";
    final static String GAMEPANEL = "Gameplay panel";

    public CandyCrush() {
        super(Utils.getAppName());
        setupUI();
    }

    private void setupUI() {
        setAppIcon();
        setBackground(Utils.darkBackground);
        getContentPane().setBackground(Utils.darkBackground);
        setFrameVisuals();

        //setup the cards
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


    public void showLoading() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.openLoading();

        closeLoadingDialog();
    }


    private void closeLoadingDialog() {
        Timer timer = new Timer(1000, arg0 -> {
            loadingDialog.closeLoading();
            cardGameplay.enableBoardGrid(true);
        });

        timer.setRepeats(false);
        timer.start();
    }

    public void flipCard() {
        cardLayout.next(cards);
    }

}
