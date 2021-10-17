package utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Player {

    private final String username;
    private final int globalScore;
    private final JSONArray progress;

    public Player(JSONObject player) {
        this.username = (String) player.get("username");
        this.globalScore = (int) (long) player.get("global-score");
        this.progress = (JSONArray) player.get("progress");
    }

    public String getUsername() {
        return username;
    }

    public int getGlobalScore() {
        return globalScore;
    }

    public JSONArray getProgress() {
        return progress;
    }

}
