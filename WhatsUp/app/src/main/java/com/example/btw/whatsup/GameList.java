package com.example.btw.whatsup;

/**
 * Created by sherl on 10/7/2017.
 */

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class GameList extends ArrayAdapter<MultiplayerGame> {
    private Activity context;
    List<MultiplayerGame> games;

    public GameList(Activity context, List<MultiplayerGame> games) {
        super(context, R.layout.layout_game_list, games);
        this.context = context;
        this.games = games;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_game_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.game_list_name);

        MultiplayerGame game = games.get(position);
        textViewName.setText("Join " + game.getCreator());

        return listViewItem;
    }
}