package io.swagslash.settlersofcatan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class RobPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rob_player);

     /*   RadioButton radio1 =  findViewById(R.id.radioButtonPlayer1);
        RadioButton radio2 =  findViewById(R.id.radioButtonPlayer2);
        RadioButton radio3 =  findViewById(R.id.radioButtonPlayer3);
        //TO-DO: Player Namen bekommen
        final Player p1 = null;
        final Player p2 = null;
        final Player p3 = null;
        radio1.setText(p1.getPlayerName());
        radio1.setText(p2.getPlayerName());
        radio1.setText(p3.getPlayerName());

        Button btn;
         btn = findViewById(R.id.buttonConfirmPlayer);
         btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = ((RadioButton) v).isChecked();

                        // Check which radio button was clicked
                        switch(v.getId()) {
                            case R.id.radioButtonPlayer1:
                                if (checked)
                                Robber.rob(p1);
                                    break;
                            case R.id.radioButtonPlayer2:
                                if (checked)
                                    Robber.rob(p2);
                                    break;
                            case R.id.radioButtonPlayer3:
                                if (checked)
                                    Robber.rob(p3);
                                    break;
                        }
                    }
                }
        );
        */
    }




}
