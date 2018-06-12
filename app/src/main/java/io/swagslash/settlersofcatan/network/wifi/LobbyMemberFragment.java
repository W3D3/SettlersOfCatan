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

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class LobbyMemberFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private List<NetworkDevice> lobbyMember = new ArrayList<>();

    private MyLobbyMemberRecyclerViewAdapter adapter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LobbyMemberFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static LobbyMemberFragment newInstance(int columnCount) {
        LobbyMemberFragment fragment = new LobbyMemberFragment();
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
        View view = inflater.inflate(R.layout.fragment_lobbymember_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new MyLobbyMemberRecyclerViewAdapter(lobbyMember);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    public void setMember(List<NetworkDevice> member) {
        adapter.setMember(member);
    }
    public void addMember(NetworkDevice member){
        adapter.addMember(member);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
