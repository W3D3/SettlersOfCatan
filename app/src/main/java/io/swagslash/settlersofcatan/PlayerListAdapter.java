package io.swagslash.settlersofcatan;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.ViewHolder> {

    private List<Player> players;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        View v;
        ViewHolder(View itemView) {
            super(itemView);
            v = itemView;
        }
    }

    PlayerListAdapter(List<Player> input) {
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
        playerName.setText(players.get(position).getPlayerName());
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
