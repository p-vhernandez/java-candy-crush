package utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Player {

    private final String username;
    private final JSONArray progress;

    private int globalScore;

    public Player(JSONObject player) {
        this.username = (String) player.get("username");
        this.globalScore = (int) (long) player.get("global-score");
        this.progress = (JSONArray) player.get("progress");
    }

    // Methods
    public String getUsername() {
        return username;
    }

    public void setGlobalScore(int globalScore) {
        this.globalScore += globalScore;
    }

    public int getGlobalScore() {
        return globalScore;
    }

    public JSONArray getProgress() {
        return progress;
    }

    public void updateProgress(int globalScore, Level playedLevel) {
        setGlobalScore(globalScore);

        for (Object level : progress) {
            JSONObject jsonLevel = (JSONObject) level;

            if (((int) (long) jsonLevel.get("level"))
                    == (playedLevel.getDifficulty() + 1)) {
                jsonLevel.replace("unlocked", true);
                break;
            }
        }
    }

    public JSONObject toJSON() {
        JSONObject playerJSON = new JSONObject();

        playerJSON.put("username", this.username);
        playerJSON.put("global-score", this.globalScore);
        playerJSON.put("progress", progress);

        return playerJSON;
    }

}
