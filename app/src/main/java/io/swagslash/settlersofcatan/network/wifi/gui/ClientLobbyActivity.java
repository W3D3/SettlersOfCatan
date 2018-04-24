package io.swagslash.settlersofcatan.network.wifi.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bluelinelabs.logansquare.LoganSquare;
import com.peak.salut.Callbacks.SalutDataCallback;
import com.peak.salut.Salut;
import com.peak.salut.SalutDevice;

import java.io.IOException;
import java.util.List;

import io.swagslash.settlersofcatan.GridActivity;
import io.swagslash.settlersofcatan.SettlerApp;
import io.swagslash.settlersofcatan.R;
import io.swagslash.settlersofcatan.network.wifi.DataCallback;
import io.swagslash.settlersofcatan.network.wifi.INetworkManager;
import io.swagslash.settlersofcatan.pieces.Board;

public class ClientLobbyActivity extends AppCompatActivity implements DataCallback.IDataCallback {

    private static final String TAG = "CLIENTLOBBY";
    private Salut network;

    private boolean backAllowed = false;

    private List<SalutDevice> member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_lobby);
        DataCallback.actActivity = this;

        network = SettlerApp.getManager().getNetwork();
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btnLeave:
                leaveLobby();
                break;
        }
    }

    void leaveLobby(){
        network.unregisterClient(false);
        super.onBackPressed();
    }

    @Override
    public void onDataReceived(Object data) {
        Log.d(TAG, "Received network data.");
        try
        {
//            io.swagslash.settlersofcatan.network.wifi.Message newMessage = LoganSquare.parse((String) data, io.swagslash.settlersofcatan.network.wifi.Message.class);
//            Log.d(TAG, newMessage.description);  //See you on the other side!
//            TextView textView = findViewById(R.id.tv_Message);
//            textView.setText(newMessage.description);

            Board board = LoganSquare.parse((String) data, Board.class);
            SettlerApp.board = board;
            Intent i = new Intent(getApplicationContext(), GridActivity.class);
            startActivity(i);
        }
        catch (IOException ex)
        {
            Log.e(TAG, "Failed to parse network data.");
        }
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
}
