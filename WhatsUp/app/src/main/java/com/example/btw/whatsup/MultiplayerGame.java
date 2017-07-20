package com.example.btw.whatsup;

import java.util.ArrayList;

/**
 * Created by sherl on 10/7/2017.
 */

public class MultiplayerGame {

    private String gameId;
    private String creator;
    private String joiner;
    private int creator_score, joiner_score;
    private int creator_total_score, joiner_total_score;
    private int creator_current, joiner_current;
    private String state;
    private int upDigit;
    private String creator_ready, joiner_ready;
    private ArrayList<Integer> sharedArr = new ArrayList<Integer>();

    public MultiplayerGame(){

    }

    public MultiplayerGame(String gameId, String creator, String joiner) {
        this.gameId = gameId;
        this.creator = creator;
        this.joiner = joiner;
      //  this.state = state;
        creator_score = 0;
        joiner_score = 0;
        creator_total_score = 0;
        joiner_total_score = 0;
        creator_ready = "NO";
        joiner_ready = "NO";
        creator_current = 1;
        joiner_current = 100;
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

    public String getCreator_ready() {
        return creator_ready;
    }

    public String getJoiner_ready() {
        return joiner_ready;
    }

    public int getCreator_total_score() {
        return creator_total_score;
    }

    public int getJoiner_total_score() {
        return joiner_total_score;
    }

    public ArrayList<Integer> getSharedArr() {
        return sharedArr;
    }

}
