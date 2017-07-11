package com.example.btw.whatsup;

/**
 * Created by sherl on 10/7/2017.
 */

public class MultiplayerGame {

    private String gameId;
    private String creator;
    private String joiner;
    private int creator_score, joiner_score;
    private int creator_current, joiner_current;
    private String state;
    private int upDigit;

    public MultiplayerGame(){

    }

    public MultiplayerGame(String gameId, String creator, String joiner) {
        this.gameId = gameId;
        this.creator = creator;
        this.joiner = joiner;
      //  this.state = state;
        creator_score = 0;
        joiner_score = 0;
    }

    public String getGameId() {
        return gameId;
    }

    public String getCreator() {
        return creator;
    }

    public String getJoiner() {
        return joiner;
    }

    public int getCreator_score() {
        return creator_score;
    }

    public int getJoiner_score() {
        return joiner_score;
    }

    public String getState(){
        return state;
    }

    public int getUpDigit(){ return upDigit; }

    public int getCreator_current(){ return creator_current; }

    public int getJoiner_current(){ return joiner_current; }
}
