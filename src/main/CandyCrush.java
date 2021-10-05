package main;

import components.CardGameplay;
import components.GameOverDialog;
import components.grid.BoardGrid;
import components.LoadingDialog;
import components.TopPanel;
import utils.Level;
import utils.helpers.LevelType;
import utils.Utils;

import javax.swing.*;
import java.awt.*;

public class CandyCrush extends JFrame {

    private JPanel cardWelcome;
    private JPanel cardLevelChoice;
    private JPanel cardGameplay;

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
        cardGameplay = new CardGameplay(new Level(LevelType.SQUARE));
        add(cardGameplay, GAMEPANEL);

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

}
