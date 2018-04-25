package io.swagslash.settlersofcatan;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class ResourceDiscardActivity extends AppCompatActivity {
    public int timer = 10;
    public int erz,holz,getreide, wolle,lehm,gesamtAbwurfkarten = 0;
    int maxHolz =4;
    int maxWolle = 5;
    int maxErz = 3;
    int maxLehm = 1;
    int maxGetreide = 2;
    int sumResourceToDiscard, countResourceToDiscard;
    TextView textViewGetreide, textViewLehm, textViewHolz,textViewWolle,textViewErz,textViewTimer,textViewAnweisung,textViewNoch ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_discard);

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
        textViewGetreide.setText("0/" + maxGetreide);
        textViewLehm = (TextView) findViewById(R.id.textViewLehm);
        textViewLehm.setText("0/" + maxLehm);
        textViewHolz =(TextView) findViewById(R.id.textViewHolz);
        textViewHolz.setText("0/" + maxHolz);
        textViewWolle = (TextView)findViewById(R.id.textViewWolle);
        textViewWolle.setText("0/" + maxWolle);
        textViewErz = (TextView)findViewById(R.id.textViewErz);
        textViewErz.setText("0/" + maxErz);
        textViewTimer =(TextView) findViewById(R.id.textViewtimer);
        textViewNoch = (TextView) findViewById(R.id.textViewNochNumber);

        textViewAnweisung = (TextView) findViewById(R.id.textViewTitel);

        sumResourceToDiscard = maxGetreide+maxErz+maxHolz+maxLehm+maxWolle;
        countResourceToDiscard = sumResourceToDiscard/2;
        textViewNoch.setText(Integer.toString(countResourceToDiscard));
        textViewAnweisung.setText("Sie haben "+ sumResourceToDiscard +" Ressourcenkarten, werfen sie somit "+ (sumResourceToDiscard /2) +" ab!");

        //TO-DO_ Holen aus RessourcenInventar!


        //Starte Download
        countdown10to0();

        btnClear.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                       lehm = 0;erz = 0;holz = 0;getreide = 0;wolle =0;gesamtAbwurfkarten=0;
                       countResourceToDiscard = sumResourceToDiscard/2;
                       textViewErz.setText(Integer.toString(erz));
                       textViewGetreide.setText(Integer.toString(getreide));
                       textViewHolz.setText(Integer.toString(holz));
                       textViewLehm.setText(Integer.toString(lehm));
                       textViewWolle.setText(Integer.toString(wolle));
                       countResourceToDiscard = sumResourceToDiscard/2;
                       textViewNoch.setText(Integer.toString(countResourceToDiscard));
                    }
                }
        );

        btnErz.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View v) {if(erz< maxErz){
                    erz++;gesamtAbwurfkarten++;
                    textViewErz.setText(Integer.toString(erz)+"/" +Integer.toString(maxErz));

                    countResourceToDiscard--;
                    textViewNoch.setText(Integer.toString(countResourceToDiscard));
                }}
        });
        btnGetreide.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        if(getreide < maxGetreide){
                        getreide++;gesamtAbwurfkarten++;
                        textViewGetreide.setText(Integer.toString(getreide)+"/" +Integer.toString(maxGetreide));
                            countResourceToDiscard--;
                            textViewNoch.setText(Integer.toString(countResourceToDiscard));
                    }}
                });
        btnLehm.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {if(lehm < maxLehm){
                                               lehm++;gesamtAbwurfkarten++;
                        textViewLehm.setText(Integer.toString(lehm) +"/" +Integer.toString(maxLehm));
                        countResourceToDiscard--;

                        textViewNoch.setText(Integer.toString(countResourceToDiscard));
                    }}
                });
        btnWolle.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        if(wolle < maxWolle){
                        wolle++;gesamtAbwurfkarten++;
                        textViewWolle.setText(Integer.toString(wolle) +"/" +Integer.toString(maxWolle));
                        countResourceToDiscard--;

                            textViewNoch.setText(Integer.toString(countResourceToDiscard));
                    }}
                });

        btnHolz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holz < maxHolz){
                holz++;
                    gesamtAbwurfkarten++;
                textViewHolz.setText(Integer.toString(holz)+"/" +Integer.toString(maxHolz));
                    countResourceToDiscard--;

                    textViewNoch.setText(Integer.toString(countResourceToDiscard));
            }}
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDiscard();
                Log.v("Resource Discard", "Confirm Resource Discard");
            }
        });
    }

    //Bestätigung des Abwurfes und zurück
    public void confirmDiscard(){

    }


    public void randomDiscard(){
        getreide = 0;lehm = 0;holz=0;erz=0;wolle = 0;
        int numberDiscard = sumResourceToDiscard / 2;
        //Reihenfolge 0-maxGetreide,maxHolz-maxHolz
        int s1 = maxGetreide;
        int s2 = s1+maxLehm;
        int s3 = s2 + maxHolz;
        int s4 = s3 + maxWolle;
        Random R = new Random();

        //zufällig numberDiscard erzeugen
        for(int i = 0; i < numberDiscard;i++){
            int rand = R.nextInt(sumResourceToDiscard);
            if(rand <s1){
                //Discard Getreide
                if(getreide<maxGetreide) {
                    getreide++;
                }else{
                 i--;
                }
            }else if(rand < s2){
                //Discard Lehm
                if(lehm<maxLehm){
                lehm++;}
                else{
                    i--;
                }
            }
            else if(rand < s3){
                //Discard Holz
                if(holz<maxHolz){
                    holz++;
                }else{
                    i--;
                }
            }
            else if(rand < s4){
                //Discard Wolle
                if(wolle<maxWolle){wolle++;}
                else{
                    i--;
                }
            }
            else{
                //Discard Erz
                if(erz<maxErz){
                    erz++;
                }else{
                    i--;
                }
            }
        }
        Log.v("Resource Discard", "Random Discard confirmed");
        //Bestätigen des Abwurfes
        confirmDiscard();

    }


    //Countdown bis zum zufälligen Abwurf
    public void countdown10to0(){
        new CountDownTimer(25000, 1000){
            public void onTick(long millisUntilFinished) {
                  textViewTimer.setText(Long.toString(millisUntilFinished / 1000));
            }

            public void onFinish() {
                textViewTimer.setText("Zeit vorbei!");
                randomDiscard();

            }
        }.start();

    }


}
