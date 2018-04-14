package io.swagslash.settlersofcatan.network.wifi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.peak.salut.SalutDevice;

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

    private final List<SalutDevice> mValues;
    private final OnLobbyServiceClickListener clickListener;
    private final Context context;

    public MyLobbyServiceRecyclerViewAdapter(List<SalutDevice> items, OnLobbyServiceClickListener listener, Context context) {
        mValues = items;
        clickListener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_lobbyservice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final SalutDevice device = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).deviceName);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) {
                    clickListener.onClick(device);
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
        public Button button;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.tvLobbyName);
            button = view.findViewById(R.id.btnConnect);
        }

        @Override
        public String toString() {
            return super.toString() + " '"  + "'";
        }
    }

    public void setLobbies(List<SalutDevice> lobbies) {
        mValues.clear();
        mValues.addAll(lobbies);
        this.notifyDataSetChanged();
    }

    public interface OnLobbyServiceClickListener {
        void onClick(SalutDevice device);
    }

    public void addLobby(SalutDevice lobby) {
        mValues.add(lobby);
        this.notifyDataSetChanged();
    }
}
