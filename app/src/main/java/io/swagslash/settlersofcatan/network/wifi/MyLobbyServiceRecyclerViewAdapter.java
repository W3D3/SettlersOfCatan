package io.swagslash.settlersofcatan.network.wifi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.R;
import io.swagslash.settlersofcatan.network.wifi.LobbyServiceFragment.OnListFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link io.swagslash.settlersofcatan.network.wifi.LobbyService} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyLobbyServiceRecyclerViewAdapter extends RecyclerView.Adapter<MyLobbyServiceRecyclerViewAdapter.ViewHolder> {

    private final List<LobbyService> mValues;
    private final OnLobbyServiceClickListener clickListener;

    public MyLobbyServiceRecyclerViewAdapter(OnLobbyServiceClickListener listener) {
        mValues = new ArrayList<>();
        clickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_lobbyservice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final LobbyService service = mValues.get(position);
        holder.mItem = service;
        holder.mIdView.setText(mValues.get(position).getLobbyName());
        holder.mContentView.setText(mValues.get(position).getOwnerName());
        holder.playerCount.setText(mValues.get(position).getPlayerCount());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickListener != null){
                    clickListener.onClick(service);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public LobbyService mItem;
        public Button button;
        public final TextView playerCount;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.tvLobbyName);
            mContentView = (TextView) view.findViewById(R.id.tvOwnerName);
            button = view.findViewById(R.id.btnConnect);
            playerCount = view.findViewById(R.id.tvPlayerCount);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public void setLobbies(List<LobbyService> lobbies){
        mValues.clear();
        mValues.addAll(lobbies);
        this.notifyDataSetChanged();
    }

    public interface OnLobbyServiceClickListener{
        void onClick(LobbyService service);
    }
}
