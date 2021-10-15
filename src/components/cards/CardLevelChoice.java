package components.cards;

import org.json.simple.JSONArray;
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
        setUpButtons();
    }

    private void setUpLevelLabel() {
        JLabel label = new JLabel("Choose your level");
        int horizontalMargin = 50;
        label.setPreferredSize(new Dimension(Utils.getWindowWidth() - horizontalMargin * 2, 100));
        Utils.setCustomFont(this, label,
                "../../resources/font/creepster-rg.ttf", 56f, Font.PLAIN);
        label.setForeground(Color.white);

        add(label);
    }

    private void setUpButtons() {
        for (LevelButton button : levelButtons) {
            if (!button.isUnlocked()) {
                button.setEnabled(false);
            }

            add(button);
        }
    }

    private void readJSON() {
        JSONParser parser = new JSONParser();
        boolean playerFound = false;

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/resources/user/progress.json"));
            JSONArray players = (JSONArray) jsonObject.get("players");

            for (Object o : players) {
                JSONObject player = (JSONObject) o;
                String username = (String) player.get("username");

                if (username.equals(container.getPlayerUsername())) {
                    JSONObject progress = (JSONObject) player.get("progress");

                    for (int j = 1; j <= progress.size(); j++) {
                        String name = "level" + j;
                        JSONObject jsonLevel = (JSONObject) progress.get(name);

                        LevelButton button = new LevelButton(
                                this,
                                String.valueOf(Integer.parseInt((String) jsonLevel.get("index")) + 1),
                                Integer.parseInt((String) jsonLevel.get("index")),
                                (boolean) jsonLevel.get("unlocked")
                        );

                        levelButtons.add(button);
                    }

                    playerFound = true;
                    break;
                }
            }

            if (!playerFound) {
                // TODO: create new player
                System.out.println("new player!!!");
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadContent() {
        readJSON();
        setUpButtons();
    }

    public void flipCard() {
        container.flipCard(false);
        container.showLoading();
    }

}
