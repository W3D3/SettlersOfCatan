package io.swagslash.settlersofcatan.network.wifi.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import io.swagslash.settlersofcatan.R;
import io.swagslash.settlersofcatan.SettlerApp;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btnStart:
                startGame();
                break;
        }
    }

    private void startGame() {
        TextView tvPlayer = findViewById(R.id.enterName);
        if (tvPlayer.getText().toString().isEmpty()) {
            tvPlayer.setError(getResources().getString(R.string.error_noname));
            return;
        }
        SettlerApp.playerName = tvPlayer.getText().toString();
        Intent i = new Intent(getApplicationContext(), BrowserActivity.class);
        startActivity(i);
    }

}
