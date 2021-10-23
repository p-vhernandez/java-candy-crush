package components.cards;

import org.json.simple.JSONArray;
import utils.Level;
import utils.Player;
import utils.buttons.LevelButton;
import main.CandyCrush;

import javax.swing.*;

import org.json.simple.JSONObject;
import utils.Utils;
import utils.helpers.CardType;

import java.io.FileWriter;
import java.util.ArrayList;

public class CardLevelChoice extends JPanel {

    private final CandyCrush container;

    private final CardLevelChoiceModel model;
    private final CardLevelChoiceUI view;

    public CardLevelChoice(CandyCrush container) {
        this.container = container;

        this.model = new CardLevelChoiceModel();
        this.view = new CardLevelChoiceUI(this);

        initialize();
    }

    private void initialize() {
        view.initializeUI();
    }

    protected ArrayList<LevelButton> getLevelButtons() {
        return this.model.getLevelButtons();
    }

    protected void resetLevelButtons() {
        this.model.resetLevelButtons();
    }

    private void readJSON() {
        JSONObject jsonObject = Utils.getPlayersJSONObject();
        boolean playerFound = false;

        if (jsonObject != null) {
            JSONArray players = (JSONArray) jsonObject.get("players");

            for (Object o : players) {
                Player player = new Player((JSONObject) o);

                if (player.getUsername().equals(container.getPlayerUsername())) {
                    Utils.player = player;
                    createLevelButtons();

                    playerFound = true;
                    break;
                }
            }

            if (!playerFound) {
                generateNewPlayer(jsonObject);
                reloadContent();
            }
        } else {
            Utils.showError("Players file couldn't be read.");
        }
    }

    protected void createLevelButtons() {
        this.model.resetLevelButtons();

        if (Utils.player != null) {
            for (Object level : Utils.player.getProgress()) {
                JSONObject jsonLevel = (JSONObject) level;

                LevelButton button = new LevelButton(
                        this,
                        (int) (long) jsonLevel.get("level"),
                        (int) (long) jsonLevel.get("index"),
                        (boolean) jsonLevel.get("unlocked")
                );

                model.addLevelButton(button);
            }
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
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Could not create new user. ");
        }
    }

    private void showError(String infoLabel) {
        Utils.showError(infoLabel);
    }

    public void selectLevel(int index) {
        Level level = new Level(index + 1);
        container.setSelectedLevel(level);
    }

    public void reloadContent() {
        removeAll();

        view.setUpLevelLabel();
        readJSON();
        view.setUpButtons();
    }

    public void flipCard() {
        container.flipCard(CardType.LEVELS, CardType.GAME_PLAY);
        container.showLoading();
    }

}
