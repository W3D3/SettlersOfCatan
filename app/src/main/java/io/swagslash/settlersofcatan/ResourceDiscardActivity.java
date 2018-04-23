package io.swagslash.settlersofcatan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResourceDiscardActivity extends AppCompatActivity {
    public int timer = 10;
     public int erz,holz,getreide, wolle,lehm = 0;
    TextView textViewGetreide, textViewLehm, textViewHolz,textViewWolle,textViewErz,textViewTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_discard);
        timer = 10;


        Button btnGetreide = findViewById(R.id.buttonGetreide);
        Button btnLehm = findViewById(R.id.buttonLehm);
        Button btnHolz = findViewById(R.id.buttonHolz);
        Button btnErz = findViewById(R.id.buttonErz);
        Button btnWolle = findViewById(R.id.buttonWolle);
        Button btnClear = findViewById(R.id.buttonClear);
        Button btnConfirm = findViewById(R.id.buttonConfirm);
         textViewGetreide = findViewById(R.id.textViewGetreide);
        textViewLehm = findViewById(R.id.textViewLehm);
         textViewHolz = findViewById(R.id.textViewHolz);
         textViewWolle = findViewById(R.id.textViewWolle);
        textViewErz = findViewById(R.id.textViewErz);
        textViewTimer = findViewById(R.id.textViewtimer);

        btnClear.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                       lehm = 0;erz = 0;holz = 0;getreide = 0;wolle =0;
                       timer = 0;
                       textViewTimer.setText(timer);
                       textViewErz.setText(erz);
                       textViewGetreide.setText(getreide);
                       textViewHolz.setText(holz);
                       textViewLehm.setText(lehm);
                       textViewWolle.setText(wolle);
                    }
                }
        );

        btnErz.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View v) {
                    erz++;
                    textViewErz.setText(erz);
                }
        });
        btnGetreide.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        getreide++;
                        textViewGetreide.setText(getreide);
                    }
                });
        btnLehm.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        lehm++;
                        textViewLehm.setText(lehm);
                    }
                });
        btnWolle.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        wolle++;
                        textViewWolle.setText(wolle);
                    }
                });

        btnHolz.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        holz++;
                        textViewHolz.setText(holz);
                    }
                });

    }




}
