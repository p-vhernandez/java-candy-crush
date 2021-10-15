package components.cards;

import utils.buttons.LevelButton;
import main.CandyCrush;

import javax.swing.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.Utils;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CardLevelChoice extends JPanel {

    private final CandyCrush container;
    private final ArrayList<LevelButton> levelButtons;
    private final int horizontalMargin = 50;

    public CardLevelChoice(CandyCrush container) {
        this.container = container;
        this.levelButtons = new ArrayList<>();

        readJSON();
        initializeUI();
    }

    private void initializeUI() {
        setPreferredSize(new Dimension(Utils.getWindowWidth(), Utils.getWindowHeight()));
        setBackground(Utils.darkBackground);
        setLayout(new FlowLayout());

        setUpLevelLabel();

        for (LevelButton button : levelButtons) {
            if (!button.isUnlocked()) {
                button.setEnabled(false);
            }

            add(button);
        }
    }

    private void setUpLevelLabel() {
        JLabel label = new JLabel("Choose your level");
        label.setPreferredSize(new Dimension(Utils.getWindowWidth() - horizontalMargin * 2, 100));
        Utils.setCustomFont(this, label,
                "../resources/font/creepster-rg.ttf", 56f, Font.PLAIN);
        label.setForeground(Color.white);

        add(label);
    }

    private void readJSON() {
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader("src/resources/user/progress.json"));
            JSONObject jsonObject = (JSONObject) obj;

            for (int i = 1; i <= jsonObject.size(); i++) {
                String name = "level" + i;
                JSONObject jsonLevel = (JSONObject) jsonObject.get(name);

                LevelButton button = new LevelButton(
                        this,
                        String.valueOf(Integer.parseInt((String) jsonLevel.get("index")) + 1),
                        Integer.parseInt((String) jsonLevel.get("index")),
                        (boolean) jsonLevel.get("unlocked")
                );

                levelButtons.add(button);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public void flipCard() {
        container.flipCard();
        container.showLoading();
    }

}
