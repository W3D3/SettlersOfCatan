package io.swagslash.settlersofcatan;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.ViewHolder> {

    private String[] players;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        View v;
        ViewHolder(View itemView) {
            super(itemView);
            v = itemView;
        }
    }

    PlayerListAdapter(String[] input){
        players = input;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View container = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_trading_player, parent, false);

        return new ViewHolder(container);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView playerName = holder.v.findViewById(R.id.player_name);
        playerName.setText(players[position]);
    }

    @Override
    public int getItemCount() {
        return players.length;
    }
}
