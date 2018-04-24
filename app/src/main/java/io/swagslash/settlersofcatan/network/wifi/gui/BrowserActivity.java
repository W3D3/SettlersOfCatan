package io.swagslash.settlersofcatan.network.wifi.gui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.peak.salut.Callbacks.SalutCallback;
import com.peak.salut.Salut;
import com.peak.salut.SalutDevice;

import java.util.ArrayList;

import io.swagslash.settlersofcatan.SettlerApp;
import io.swagslash.settlersofcatan.R;
import io.swagslash.settlersofcatan.network.wifi.DiscoveryCallback;
import io.swagslash.settlersofcatan.network.wifi.INetworkManager;
import io.swagslash.settlersofcatan.network.wifi.LobbyServiceFragment;
import io.swagslash.settlersofcatan.network.wifi.MyLobbyServiceRecyclerViewAdapter;

public class  BrowserActivity extends AppCompatActivity implements DiscoveryCallback.IDiscoveryCallback, MyLobbyServiceRecyclerViewAdapter.OnLobbyServiceClickListener, SwipeRefreshLayout.OnRefreshListener{

    public static final String TAG = "LOBBYBROWSER";

    public Salut network;
    private SwipeRefreshLayout swipeRefreshLayout;

    private boolean backAllowed = false;
    private LobbyServiceFragment lobbies;

    public Runnable refreshRunnable;
    private Handler refreshHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        createNetwork();
        setupNetwork();
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        setRefreshing();

        lobbies = (LobbyServiceFragment) getSupportFragmentManager().findFragmentById(R.id.lobbyFrag);
        DiscoveryCallback.activity = this;


    }

    @Override
    protected void onRestart() {
        Fragment frag = getFragmentManager().findFragmentByTag("services");
        if (frag != null) {
            getFragmentManager().beginTransaction().remove(frag).commit();
        }
        super.onRestart();
    }

    @Override
    public void onClick(SalutDevice device) {
        Log.d(TAG,"Try Register");
        network.registerWithHost(device, new SalutCallback() {
            @Override
            public void call() {
                Intent i = new Intent(getApplicationContext(), ClientLobbyActivity.class);
                startActivity(i);
            }
        }, new SalutCallback() {
            @Override
            public void call() {
                Log.d(TAG, "We failed to register.");
            }
        });
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateLobby:
                createLobby();
                break;
            case R.id.btnDiscover:
                startDiscovery();
                break;
                   }
    }

    public void createLobby() {
        stopDiscovery();
        Intent i = new Intent(getApplicationContext(), HostLobbyActivity.class);
        startActivity(i);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(network.isRunningAsHost)
            network.stopNetworkService(false);
        else
            network.unregisterClient(false);
    }

    @Override
    public void onBackPressed() {
        if (!backAllowed) {

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRefresh() {
        setupDiscovery();
        lobbies.setLobbies(new ArrayList<SalutDevice>());
        setRefreshing();
    }

    private void stopDiscovery(){
        if(network.isDiscovering){
            network.stopServiceDiscovery(false);
        }
    }

    private void startDiscovery(){
        network.discoverNetworkServices(new DiscoveryCallback(), true);
    }

    @Override
    public void call() {
        lobbies.setLobbies(network.foundDevices);

        swipeRefreshLayout.setRefreshing(false);
        if (refreshHandler != null) {
            refreshHandler.removeCallbacks(refreshRunnable);
        }
    }

    private void setupDiscovery() {
        network = SettlerApp.getManager().getNetwork();

        stopDiscovery();
        startDiscovery();
    }

    private void setupNetwork(){
        INetworkManager manager = SettlerApp.getManager();
        network = manager.getNetwork();
    }

    private void setRefreshing() {
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),"No Devices found!", Toast.LENGTH_LONG);
                swipeRefreshLayout.setRefreshing(false);
            }
        };

        refreshHandler = new Handler();
        refreshHandler.postDelayed(refreshRunnable, 10000);
    }

    void createNetwork(){
        INetworkManager manager = SettlerApp.getManager();
        Salut network = manager.getNetwork();

        if (network == null) {
            manager.init(this);
        }
    }
}
