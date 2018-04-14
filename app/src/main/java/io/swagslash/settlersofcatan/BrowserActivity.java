package io.swagslash.settlersofcatan;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bluelinelabs.logansquare.LoganSquare;
import com.peak.salut.Callbacks.SalutCallback;
import com.peak.salut.Callbacks.SalutDataCallback;
import com.peak.salut.Callbacks.SalutDeviceCallback;
import com.peak.salut.Salut;
import com.peak.salut.SalutDataReceiver;
import com.peak.salut.SalutDevice;
import com.peak.salut.SalutServiceData;

import java.io.IOException;
import java.io.InputStream;

import io.swagslash.settlersofcatan.network.wifi.LobbyService;
import io.swagslash.settlersofcatan.network.wifi.LobbyServiceFragment;
import io.swagslash.settlersofcatan.network.wifi.Message;
import io.swagslash.settlersofcatan.network.wifi.MyLobbyServiceRecyclerViewAdapter;

public class BrowserActivity extends AppCompatActivity implements SalutDataCallback, LobbyServiceFragment.OnListFragmentInteractionListener, MyLobbyServiceRecyclerViewAdapter.OnLobbyServiceClickListener{

    public static final String TAG = "LobbyBrowser";

    public SalutDataReceiver dataReceiver;
    public SalutServiceData serviceData;
    public Salut network;
    SalutDataCallback callback;

    private String playerName;

    private LobbyServiceFragment lobbies;

    private TextView statusTxtView;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);



        lobbies = (LobbyServiceFragment) getSupportFragmentManager().findFragmentById(R.id.lobbyFrag);

        /*Create a data receiver object that will bind the callback
        with some instantiated object from our app. */
        dataReceiver = new SalutDataReceiver(this, this);

        Global g = (Global)getApplication();

        /*Populate the details for our awesome service. */
        serviceData = new SalutServiceData("SettlerOfCartan", 10000,
                g.getPlayerName());

        /*Create an instance of the Salut class, with all of the necessary data from before.
         * We'll also provide a callback just in case a device doesn't support WiFi Direct, which
         * Salut will tell us about before we start trying to use methods.*/
        network = new Salut(dataReceiver, serviceData, new SalutCallback() {
            @Override
            public void call() {
                // wiFiFailureDiag.show();
                // OR
                Log.e(TAG, "Sorry, but this device does not support WiFi Direct.");
            }
        });

        g.setNetwork(network);



    }

    private void setupNetwork()
    {
        if(!network.isRunningAsHost)
        {
            network.stopNetworkService(false);
            network.startNetworkService(new SalutDeviceCallback() {
                @Override
                public void call(SalutDevice salutDevice) {
                    Log.d(TAG,"Shit found me");
                    Toast.makeText(getApplicationContext(), "Device: " + salutDevice.instanceName + " connected.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            network.stopNetworkService(false);
        }
    }

    @Override
    protected void onRestart() {
        Fragment frag = getFragmentManager().findFragmentByTag("services");
        if (frag != null) {
            getFragmentManager().beginTransaction().remove(frag).commit();
        }
        super.onRestart();
    }

    private void discoverServices()
    {
        if(!network.isRunningAsHost && !network.isDiscovering)
        {
            network.discoverNetworkServices(new SalutCallback() {
                @Override
                public void call() {
                    lobbies.setLobbies(network.foundDevices);
                }
            }, true);
        }
        else
        {
            network.stopServiceDiscovery(true);
        }
    }


    @Override
    public void onListFragmentInteraction(LobbyService item) {

    }

    @Override
    public void onDataReceived(Object data) {
        Log.d(TAG, "Received network data.");
//        try
//        {
//            Message newMessage = LoganSquare.parse((InputStream) data, Message.class);
//            Log.d(TAG, newMessage.description);  //See you on the other side!
//            //Do other stuff with data.
//        }
//        catch (IOException ex)
//        {
//            Log.e(TAG, "Failed to parse network data.");
//        }

    }

    @Override
    public void onClick(SalutDevice device) {
        Log.d(TAG,"Try Register");
        network.registerWithHost(device, new SalutCallback() {
            @Override
            public void call() {
                Log.d(TAG, "We're now registered.");
            }
        }, new SalutCallback() {
            @Override
            public void call() {
                Log.d(TAG, "We failed to register.");
            }
        });
    }

    public interface MessageTarget {
        public Handler getHandler();
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateLobby:
                setupNetwork();
                break;
            case R.id.btnDiscover:
                discoverServices();
                break;
            case R.id.btnSend:
                sendMessage();
                break;



        }
    }

    public void sendMessage(){
        Message myMessage = new Message();
        myMessage.description = "See you on the other side!";

        network.sendToAllDevices(myMessage, new SalutCallback() {
            @Override
            public void call() {
                Log.e(TAG, "Oh no! The data failed to send.");
            }
        });
    }

    public void createLobby() {
        Intent i = new Intent(getApplicationContext(), LobbyActivity.class);
        startActivity(i);
    }
}
