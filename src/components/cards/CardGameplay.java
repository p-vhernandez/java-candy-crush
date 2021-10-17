package components.cards;

import components.topPanel.TopPanel;
import components.grid.BoardGrid;
import main.CandyCrush;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.Level;
import utils.Player;
import utils.Utils;
import utils.helpers.CardType;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;

public class CardGameplay extends JPanel {

    private final CandyCrush container;

    private final CardGamePlayModel model;
    private final CardGamePlayUI view;

    private static TopPanel topPanel;

    public CardGameplay(CandyCrush container) {
        this.container = container;

        this.model = new CardGamePlayModel();
        this.view = new CardGamePlayUI(this);

        initialize();
    }

    private void initialize() {
        view.initializeUI();
    }

    public void loadGame(Level level) {
        this.model.setLevel(level);

        removeAll();

        setUpTopPanel();
        view.setUpBoardPanel();
        view.setUpBottomPanel();
    }

    private void setUpTopPanel() {
        topPanel = new TopPanel(this);
        add(topPanel, BorderLayout.NORTH);
    }

    protected void restartLevel() {
        // TODO
    }

    public void flipCard(CardType destination) {
        container.flipCard(CardType.GAME_PLAY, destination);
    }

    public void goToNextLevel() {
        // TODO
    }

    public void updatePlayerProgress(int currentScore) {
        Utils.player.updateProgress(currentScore, getLevel());
        replaceInfoInJSONFile();
    }

    private void replaceInfoInJSONFile() {
        JSONObject jsonObject = Utils.getPlayersJSONObject();
        int userToReplace = -1;

        if (jsonObject != null) {
            JSONArray players = (JSONArray) jsonObject.get("players");

            for (int i = 0; i < players.size(); i++) {
                Player player = new Player((JSONObject) players.get(i));

                if (player.getUsername().equals(Utils.player.getUsername())) {
                    userToReplace = i;
                    break;
                }
            }

            if (userToReplace != -1) {
                players.remove(userToReplace);

                players.add(Utils.player.toJSON());
                jsonObject.replace("players", players);

                writeInJSON(jsonObject);
            }
        } else {
            Utils.showError("Progress could not be saved.");
        }
    }

    private void writeInJSON(JSONObject jsonObject) {
        try {
            FileWriter file = new FileWriter("src/resources/user/progress.json");
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showError("Progress could not be saved.");
        }
    }

    protected void createNewGrid() {
        this.model.createNewGrid();
    }

    protected void setGoal(int goal) {
        topPanel.setScoreGoal(goal);
    }

    protected void setMaxMovements(int maxMovements) {
        topPanel.setMaxMovements(maxMovements);
    }

    protected BoardGrid getBoardGrid() {
        return this.model.getGrid();
    }

    protected Level getLevel() {
        return this.model.getLevel();
    }

    public static void oneMovementLess() {
        topPanel.oneMovementLess();
    }

    public void enableBoardGrid(boolean enabled) {
        this.model.enableGrid(enabled);
    }

    public static void updateScore(int sequence) {
        int currentScore = topPanel.getCurrentScore();
        topPanel.setLblScoreNumber(currentScore + sequence * 40);
    }
}
