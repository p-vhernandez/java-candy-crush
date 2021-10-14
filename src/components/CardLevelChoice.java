package components;

import main.CandyCrush;

import javax.swing.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.Utils;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CardLevelChoice extends JPanel {

    private CandyCrush container;
    private ArrayList<LevelButton> levelButtons;
    private int horizontalMargin = 50;

    public CardLevelChoice(CandyCrush container) {
        this.container = container;
        this.levelButtons = new ArrayList<>();
        readJSON();
        initializeUI();
    }

    private void initializeUI() {

        JLabel label = new JLabel("Choose level:");
        label.setPreferredSize(new Dimension(Utils.getWindowWidth() - horizontalMargin * 2, 100));
        Utils.setCustomFont(this, label,
                "../resources/font/creepster-rg.ttf", 60f, Font.PLAIN);
        label.setForeground(Color.white);
        add(label);
        setPreferredSize(new Dimension(Utils.getWindowWidth(), Utils.getWindowHeight()));
        setBackground(Utils.darkBackground);
        setLayout(new FlowLayout());

        for (LevelButton button : levelButtons) {
            add(button);
            //maybe this should be in the button class
            initializeListeners(button);
            if (!button.isUnlocked()) button.setEnabled(false);
        }
    }

    private void readJSON() {

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/resources/progress.json"));
            JSONObject jsonObject = (JSONObject) obj;

            for (int i = 1; i <= jsonObject.size(); i++) {
                String name = "level" + i;
                JSONObject jsonLevel = (JSONObject) jsonObject.get(name);
                LevelButton button = new LevelButton("Level " + (Integer.parseInt((String)jsonLevel.get("index"))+1),
                        Integer.parseInt((String)jsonLevel.get("index")), (boolean)jsonLevel.get("unlocked"));
                levelButtons.add(button);
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeListeners(LevelButton button) {

        //TODO: level choice depending on which button is clicked
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                container.flipCard();
                container.showLoading();
            }
        });
    }
}
