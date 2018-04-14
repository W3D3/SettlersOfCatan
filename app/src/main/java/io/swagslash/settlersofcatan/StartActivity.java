package io.swagslash.settlersofcatan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
        Global g = (Global)getApplication();
        TextView tvPlayer = (TextView)findViewById(R.id.enterName);
        g.setPlayerName(tvPlayer.getText().toString());
        Intent i = new Intent(getApplicationContext(), BrowserActivity.class);
        startActivity(i);
    }

}
