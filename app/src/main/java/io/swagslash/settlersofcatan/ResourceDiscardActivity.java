package io.swagslash.settlersofcatan;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class ResourceDiscardActivity extends AppCompatActivity {
    public int timer = 10;
    public int erz;
    public int holz;
    public int getreide;
    public int wolle;
    public int lehm;

    //maxResource hängt von den Handkarten ab. Wenn es funktioniert: Vorher alle auf 0 setzen!!
    int maxHolz = 4;
    int maxWolle = 5;
    int maxErz = 3;
    int maxLehm = 1;
    int maxGetreide = 2;
    int sumResourceInHand, countResourceToDiscard;
    TextView textViewGetreide, textViewLehm, textViewHolz, textViewWolle, textViewErz, textViewTimer, textViewAnweisung, textViewNoch;
    Button btnGetreide, btnLehm, btnHolz, btnErz, btnWolle, btnClear, btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_discard);

        btnGetreide = (Button) findViewById(R.id.buttonGetreide);
        btnLehm = (Button) findViewById(R.id.buttonLehm);
        btnHolz = (Button) findViewById(R.id.buttonHolz);
        btnErz = (Button) findViewById(R.id.buttonErz);
        btnWolle = (Button) findViewById(R.id.buttonWolle);
        btnClear = (Button) findViewById(R.id.buttonClear);
        btnConfirm = (Button) findViewById(R.id.buttonConfirmPlayer);
        textViewGetreide = (TextView) findViewById(R.id.textViewGetreide);
        textViewGetreide.setText("0/" + maxGetreide);
        textViewLehm = (TextView) findViewById(R.id.textViewLehm);
        textViewLehm.setText("0/" + maxLehm);
        textViewHolz = (TextView) findViewById(R.id.textViewHolz);
        textViewHolz.setText("0/" + maxHolz);
        textViewWolle = (TextView) findViewById(R.id.textViewWolle);
        textViewWolle.setText("0/" + maxWolle);
        textViewErz = (TextView) findViewById(R.id.textViewErz);
        textViewErz.setText("0/" + maxErz);
        textViewTimer = (TextView) findViewById(R.id.textViewtimer);
        textViewNoch = (TextView) findViewById(R.id.textViewNochNumber);
        textViewAnweisung = (TextView) findViewById(R.id.textViewTitel);

        /* Inventar durchlaufen, um festzustellen welche Resourcen wie oft vorhanden sind.
        //Player sollte mitgegeben werden
        Player dran = new Player(null,1, Player.Color.RED,"istdran");

        Resource r;
         HashMap.Entry entry;
        Iterator it  = dran.getInventory().getResourceHand().entrySet().iterator();
           while(it.hasNext()){
            entry = (Map.Entry) it.next();
            r = (Resource) entry.getValue();
            if(r.getResourceType() == Resource.ResourceType.WOOD){
                maxHolz++;
            }
            else if (r.getResourceType() == Resource.ResourceType.BRICK){
                maxLehm++;
            }
            else if (r.getResourceType() == Resource.ResourceType.GRAIN){
                maxGetreide++;
            }
            else if (r.getResourceType() == Resource.ResourceType.ORE){
                maxErz++;
            }
            else if (r.getResourceType() == Resource.ResourceType.WOOL){
                maxWolle++;
            }

        }
        */


        //Gesamtanzahk der Handkarten berechnen
        sumResourceInHand = maxGetreide + maxErz + maxHolz + maxLehm + maxWolle;

        //Gesamtanzahl der Abzuwerfenden Karten berechnen
        countResourceToDiscard = sumResourceInHand / 2;
        textViewNoch.setText(Integer.toString(countResourceToDiscard));
        textViewAnweisung.setText("Sie haben " + Integer.toString(sumResourceInHand) + " Ressourcenkarten, werfen sie somit " + Integer.toString((sumResourceInHand / 2)) + " ab!");


        //Start Countdown
        countdown25to0();


        /**
         * Reset Button. Sets the choosen numbers to Default and displays them.
         */
        btnClear.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        lehm = 0;
                        erz = 0;
                        holz = 0;
                        getreide = 0;
                        wolle = 0;
                        countResourceToDiscard = sumResourceInHand / 2;
                        textViewErz.setText(Integer.toString(erz) + "/" + Integer.toString(maxErz));
                        textViewGetreide.setText(Integer.toString(getreide) + "/" + Integer.toString(maxGetreide));
                        textViewHolz.setText(Integer.toString(holz) + "/" + Integer.toString(maxHolz));
                        textViewLehm.setText(Integer.toString(lehm) + "/" + Integer.toString(maxLehm));
                        textViewWolle.setText(Integer.toString(wolle) + "/" + Integer.toString(maxWolle));
                        printCountToDiscard();
                        ;
                    }
                }
        );
        /**
         * Increase Ore Button
         */
        btnErz.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        if (erz < maxErz) {
                            erz++;
                            textViewErz.setText(Integer.toString(erz) + "/" + Integer.toString(maxErz));

                            countResourceToDiscard--;
                            printCountToDiscard();
                        }
                    }
                });
        /**
         * Increase Grain Button
         */
        btnGetreide.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        if (getreide < maxGetreide) {
                            getreide++;
                            textViewGetreide.setText(Integer.toString(getreide) + "/" + Integer.toString(maxGetreide));
                            countResourceToDiscard--;
                            printCountToDiscard();
                        }
                    }
                });
        /**
         * Increase Brick Button
         */
        btnLehm.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        if (lehm < maxLehm) {
                            lehm++;
                            textViewLehm.setText(Integer.toString(lehm) + "/" + Integer.toString(maxLehm));
                            countResourceToDiscard--;
                            printCountToDiscard();
                        }
                    }
                });
        /**
         * Increase Wool Button
         */
        btnWolle.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        if (wolle < maxWolle) {
                            wolle++;
                            textViewWolle.setText(Integer.toString(wolle) + "/" + Integer.toString(maxWolle));
                            countResourceToDiscard--;
                            printCountToDiscard();
                        }
                    }
                });
        /**
         * Increase Wood Button
         */
        btnHolz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holz < maxHolz) {
                    holz++;
                    textViewHolz.setText(Integer.toString(holz) + "/" + Integer.toString(maxHolz));
                    countResourceToDiscard--;
                    printCountToDiscard();

                }
            }
        });

        /**
         * Confirm Button
         */
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDiscard();
                Log.v("Resource Discard", "Confirm Resource Discard");
            }
        });
    }

    public void printCountToDiscard() {
        textViewNoch.setText(Integer.toString(countResourceToDiscard));
    }


    /**
     * Confirm the choosen Discard. Removes discarded Resources from
     * the Resource Hand
     */
    public void confirmDiscard() {

        if (wolle + getreide + lehm + erz + holz != (int) (sumResourceInHand / 2)) {
            Toast toast = Toast.makeText(getApplicationContext(), "Zu viele oder zu wenige Karten ausgewählt!", Toast.LENGTH_SHORT);
            toast.show();
        } else {

        }

        // TODO Remove Cards from Players Hand
    }

    /**
     * Choose Random Cards to discard from Hand after Timeout.
     * Probability depends on how many cards per Resource Type are
     * on the Hand (=Real Probability
     */
    public void randomDiscard() {
        getreide = 0;
        lehm = 0;
        holz = 0;
        erz = 0;
        wolle = 0;
        int numberDiscard = (int) sumResourceInHand / 2;
        //Schranken
        int s1 = maxGetreide;
        int s2 = s1 + maxLehm;
        int s3 = s2 + maxHolz;
        int s4 = s3 + maxWolle;
        Random R = new Random();
        int rand;
        //zufällig numberDiscard erzeugen
        for (int i = 1; i <= numberDiscard; i++) {
            rand = R.nextInt(sumResourceInHand);
            if (rand < s1) {
                //Discard Getreide
                if (getreide < maxGetreide) {
                    getreide++;
                } else {
                    i--;
                }
            } else if (rand < s2) {
                //Discard Lehm
                if (lehm < maxLehm) {
                    lehm++;
                } else {
                    i--;
                }
            } else if (rand < s3) {
                //Discard Holz
                if (holz < maxHolz) {
                    holz++;
                } else {
                    i--;
                }
            } else if (rand < s4) {
                //Discard Wolle
                if (wolle < maxWolle) {
                    wolle++;
                } else {
                    i--;
                }
            } else {
                //Discard Erz
                if (erz < maxErz) {
                    erz++;
                } else {
                    i--;
                }
            }
        }

        textViewGetreide.setText(Integer.toString(getreide) + "/" + Integer.toString(maxGetreide));
        textViewErz.setText(Integer.toString(erz) + "/" + Integer.toString(maxErz));
        textViewLehm.setText(Integer.toString(lehm) + "/" + Integer.toString(maxLehm));
        textViewWolle.setText(Integer.toString(wolle) + "/" + Integer.toString(maxWolle));
        textViewHolz.setText(Integer.toString(holz) + "/" + Integer.toString(maxHolz));
        textViewNoch.setText("0");
        btnClear.setEnabled(false);
        btnWolle.setEnabled(false);
        btnGetreide.setEnabled(false);
        btnErz.setEnabled(false);
        btnHolz.setEnabled(false);
        btnLehm.setEnabled(false);
        btnConfirm.setBackgroundColor(Color.GREEN);


        Log.v("Resource Discard", "Random Discard confirmed" + " Holz " + holz + " Getreide " + getreide
                + " Erz " + erz + " Wolle " + wolle + " Lehm " + getDelegate());

    }


    /**
     * After 25 Seconds, the Coundown finished and the Resource will choose randomly
     */
    public void countdown25to0() {
        new CountDownTimer(25000, 1000) {
            public void onTick(long millisUntilFinished) {
                textViewTimer.setText(Long.toString(millisUntilFinished / 1000));
            }

            public void onFinish() {
                textViewTimer.setText("Zeit vorbei! Zufällige Auswahl!");
                randomDiscard();

            }
        }.start();

    }


}
