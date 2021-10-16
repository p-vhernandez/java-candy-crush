package components.cards;

import org.json.simple.JSONArray;
import utils.Level;
import utils.buttons.LevelButton;
import main.CandyCrush;

import javax.swing.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.Utils;
import utils.dialogs.ErrorDialog;
import utils.helpers.CardType;

import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CardLevelChoice extends JPanel {

    private final CandyCrush container;
    private final ArrayList<LevelButton> levelButtons;

    private ErrorDialog errorDialog;

    public CardLevelChoice(CandyCrush container) {
        this.container = container;
        this.levelButtons = new ArrayList<>();

        initializeUI();
    }

    private void initializeUI() {
        setPreferredSize(new Dimension(Utils.getWindowWidth(), Utils.getWindowHeight()));
        setBackground(Utils.darkBackground);
        setLayout(new FlowLayout());
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
                    JSONArray progress = (JSONArray) player.get("progress");

                    for (Object level : progress) {
                        JSONObject jsonLevel = (JSONObject) level;

                        LevelButton button = new LevelButton(
                                this,
                                (int) (long) jsonLevel.get("level"),
                                (int) (long) jsonLevel.get("index"),
                                //(boolean) jsonLevel.get("unlocked")
                                true
                        );

                        levelButtons.add(button);
                    }

                    playerFound = true;
                    break;
                }
            }

            if (!playerFound) {
                generateNewPlayer(jsonObject);
                reloadContent();
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            showError("Could not read the player data. ");
        }
    }

    private void generateNewPlayer(JSONObject jsonObject) {
        try {
            JSONObject newPlayer = new JSONObject();
            JSONArray progress = new JSONArray();

            for (int i = 0; i < Utils.getTotalLevels(); i++) {
                JSONObject level = new JSONObject();

                level.put("level", i + 1);
                level.put("index", i);

                if (i == 0) {
                    level.put("unlocked", true);
                } else {
                    level.put("unlocked", false);
                }

                progress.add(level);
            }

            newPlayer.put("username", container.getPlayerUsername());
            newPlayer.put("global-score", 0);
            newPlayer.put("progress", progress);

            ((JSONArray) jsonObject.get("players")).add(newPlayer);
            addPlayerToJSON(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Could not create new user. ");
        }
    }

    private void addPlayerToJSON(JSONObject jsonObject) {
        try {
            FileWriter file = new FileWriter("src/resources/user/progress.json");
            System.out.println(jsonObject);
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Could not create new user. ");
        }
    }

    private void showError(String infoLabel) {
        errorDialog = new ErrorDialog(infoLabel);
        errorDialog.setVisible(true);
    }

    public void selectLevel(int index) {
        Level level = new Level(index + 1);
        container.setSelectedLevel(level);
    }

    public void reloadContent() {
        removeAll();

        // TODO: clear buttons to re-create them
        setUpLevelLabel();
        readJSON();
        setUpButtons();
    }

    public void flipCard() {
        container.flipCard(CardType.LEVELS, CardType.GAME_PLAY);
        container.showLoading();
    }

}
