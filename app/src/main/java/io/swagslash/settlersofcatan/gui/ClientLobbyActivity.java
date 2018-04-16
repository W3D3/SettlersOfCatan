package io.swagslash.settlersofcatan.gui;

import android.content.Intent;
import android.os.Message;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bluelinelabs.logansquare.LoganSquare;
import com.peak.salut.Callbacks.SalutCallback;
import com.peak.salut.Callbacks.SalutDataCallback;
import com.peak.salut.Salut;
import com.peak.salut.SalutDataReceiver;
import com.peak.salut.SalutServiceData;

import java.io.IOException;
import java.io.InputStream;

import io.swagslash.settlersofcatan.Global;
import io.swagslash.settlersofcatan.R;

public class ClientLobbyActivity extends AppCompatActivity implements SalutDataCallback{

    private static final String TAG = "CLIENTLOBBY";
    private Salut network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_lobby);

        Global g = (Global)getApplication();
        network = g.getNetwork();
        g.getDataCallBack().setActActivity(this);

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
            io.swagslash.settlersofcatan.network.wifi.Message newMessage = LoganSquare.parse((String) data, io.swagslash.settlersofcatan.network.wifi.Message.class);
            Log.d(TAG, newMessage.description);  //See you on the other side!
            TextView textView = findViewById(R.id.tv_Message);
            textView.setText(newMessage.description);
        }
        catch (IOException ex)
        {
            Log.e(TAG, "Failed to parse network data.");
        }
    }
}
