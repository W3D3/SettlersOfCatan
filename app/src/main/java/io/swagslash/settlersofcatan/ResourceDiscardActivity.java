package io.swagslash.settlersofcatan;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResourceDiscardActivity extends AppCompatActivity {
    public int timer = 10;
    public int erz,holz,getreide, wolle,lehm = 0;
    TextView textViewGetreide, textViewLehm, textViewHolz,textViewWolle,textViewErz,textViewTimer,textViewAnweisung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_discard);
        timer = 10;

        //Bei Wedes Resourcen
        //WOOD, GRAIN, WOOL, ORE, BRICK

        Button btnGetreide = (Button) findViewById(R.id.buttonGetreide);
        Button btnLehm =(Button) findViewById(R.id.buttonLehm);
        Button btnHolz = (Button)findViewById(R.id.buttonHolz);
        Button btnErz = (Button)findViewById(R.id.buttonErz);
        Button btnWolle = (Button)findViewById(R.id.buttonWolle);
        Button btnClear = (Button)findViewById(R.id.buttonClear);
        Button btnConfirm = (Button)findViewById(R.id.buttonConfirm);
         textViewGetreide = (TextView)findViewById(R.id.textViewGetreide);
        textViewLehm = (TextView) findViewById(R.id.textViewLehm);
        textViewHolz =(TextView) findViewById(R.id.textViewHolz);
        textViewWolle = (TextView)findViewById(R.id.textViewWolle);
        textViewErz = (TextView)findViewById(R.id.textViewErz);
        textViewTimer =(TextView) findViewById(R.id.textViewtimer);
        textViewAnweisung = (TextView) findViewById(R.id.textViewTitel);

        int countResource = 9;



        countdown10to0();

        btnClear.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                       lehm = 0;erz = 0;holz = 0;getreide = 0;wolle =0;
                       timer = 10;
                       textViewTimer.setText(Integer.toString(timer));
                       textViewErz.setText(Integer.toString(erz));
                       textViewGetreide.setText(Integer.toString(getreide));
                       textViewHolz.setText(Integer.toString(holz));
                       textViewLehm.setText(Integer.toString(lehm));
                       textViewWolle.setText(Integer.toString(wolle));
                    }
                }
        );

        btnErz.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View v) {
                    erz++;
                    textViewErz.setText(Integer.toString(erz));
                }
        });
        btnGetreide.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        getreide++;
                        textViewGetreide.setText(Integer.toString(getreide));
                    }
                });
        btnLehm.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                                               lehm++;
                        textViewLehm.setText(Integer.toString(lehm));
                    }
                });
        btnWolle.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        wolle++;
                        textViewWolle.setText(Integer.toString(wolle));
                    }
                });

        btnHolz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holz++;
                textViewHolz.setText(Integer.toString(holz));
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Resource Discard", "Confirm Resource Discard");
            }
        });
    }

    //Countdown für zufälliges
    public void countdown10to0(){
        new CountDownTimer(10000, 1000){
            public void onTick(long millisUntilFinished) {

                textViewTimer.setText(Long.toString(millisUntilFinished / 1000));
            }

            public void onFinish() {
                textViewTimer.setText("Zeit aus!");
            }
        }.start();
    }


}
