package io.swagslash.settlersofcatan.network.wifi;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peak.salut.Salut;
import com.peak.salut.SalutDevice;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.Global;
import io.swagslash.settlersofcatan.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class LobbyServiceFragment extends Fragment implements MyLobbyServiceRecyclerViewAdapter.OnLobbyServiceClickListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private MyLobbyServiceRecyclerViewAdapter.OnLobbyServiceClickListener mListener;
    private List<SalutDevice> lobbies = new ArrayList<>();

    private MyLobbyServiceRecyclerViewAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LobbyServiceFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static LobbyServiceFragment newInstance(int columnCount) {
        LobbyServiceFragment fragment = new LobbyServiceFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lobbyservice_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new MyLobbyServiceRecyclerViewAdapter(lobbies, mListener, context);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyLobbyServiceRecyclerViewAdapter.OnLobbyServiceClickListener) {
            mListener = (MyLobbyServiceRecyclerViewAdapter.OnLobbyServiceClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(SalutDevice device) {

    }

    public void setLobbies(List<SalutDevice> lobbies) {
        adapter.setLobbies(lobbies);
    }

    public void addLobby(SalutDevice lobby) {
        adapter.addLobby(lobby);
    }
}
